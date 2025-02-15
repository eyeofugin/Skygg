package game.skills;

import framework.Logger;
import framework.Property;
import framework.connector.Connector;
import framework.connector.payloads.BaseHealChangesPayload;
import framework.connector.payloads.CriticalTriggerPayload;
import framework.connector.payloads.DmgChangesPayload;
import framework.connector.payloads.DmgTriggerPayload;
import framework.connector.payloads.OnPerformPayload;
import framework.graphics.text.Color;
import framework.graphics.text.TextEditor;
import framework.resources.SpriteLibrary;
import framework.states.Arena;
import game.entities.Hero;
import game.entities.Multiplier;
import game.objects.Equipment;
import game.skills.changeeffects.effects.Scoped;
import game.skills.changeeffects.effects.Threatening;
import game.skills.changeeffects.statusinflictions.Rooted;
import utils.MyMaths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Skill {

    private static int counter;
    public int id;
    public String name;
    public Hero hero;
    public Equipment equipment;
    public String description;
    protected int[] iconPixels;
    protected String iconPath;
    protected String animationName = "action_w";

    protected int distance = 0;
    protected TargetType targetType = TargetType.SINGLE;
    protected int targetRadius = 0;

    protected DamageType damageType = null;
    protected DamageMode damageMode = null;
    protected boolean passive = false;
    protected boolean isMove = false;
    protected List<Effect> movedWithEffects = new ArrayList<>();
    protected List<Effect> effects = new ArrayList<>();
    protected List<Effect> casterEffects = new ArrayList<>();

    protected List<Resource> targetResources = new ArrayList<>();

    protected List<Multiplier> dmgMultipliers = new ArrayList<>();
    protected List<Multiplier> healMultipliers = new ArrayList<>();

    protected List<Hero> targets = new ArrayList<>();

    protected int manaCost = 0;
    protected int lifeCost = 0;
    protected int faithCost = 0;
    protected int actionCost = 1;
    protected int overheatCost = 0;
    protected int accuracy = 100;
    protected int critChance = 0;
    public int dmg = 0;
    public int heal = 0;
    public int shield = 0;
    protected int cdMax = 0;
    protected int cdCurrent = 0;
    protected boolean justCast = false;
    protected int blockedTurns = 0;
    protected boolean canMiss = true;
    protected int countAsHits = 1;
    protected boolean primary = false;
    protected boolean ultimate = false;
    protected boolean comboEnabled = false;
    protected boolean faithGain = false;
    public boolean allowAllyForSingle = false;

    public static List<TargetType> MAX_ACC_TARGET_TYPES = List.of(
            TargetType.SELF,
            TargetType.ALL_ALLY,
            TargetType.SINGLE_ALLY,
            TargetType.SINGLE_ALLY_IN_FRONT,
            TargetType.SINGLE_ALLY_BEHIND
    );

//AI
    public List<SkillTag> tags = new ArrayList<>();
    public int getAIRating(Hero target){return 0;}
    public int getAIArenaRating(Arena arena) {return 0;}

    protected int getRollRating(Hero target) {
        if (this.hero.hasPermanentEffect(Rooted.class) > 0 ||
                this.hero.hasPermanentEffect(Scoped.class) > 0 ||
                target.hasPermanentEffect(Rooted.class) > 0 ||
                target.hasPermanentEffect(Scoped.class) > 0) {
            return -5;
        }
        int lastEffectivePosition = this.hero.getLastEffectivePosition();
        int targetLifeAdvantage = target.getStat(Stat.CURRENT_LIFE) - this.hero.getStat(Stat.CURRENT_LIFE);

        if (target.getPosition() > this.hero.getPosition()) { //ROll Back
            if (this.hero.getPosition() == lastEffectivePosition) {
                return -10;
            }
            return targetLifeAdvantage / 2;

        } else {
            if (target.getPosition() == lastEffectivePosition) { // ROLL FORWARD
                return 10;
            }
            return targetLifeAdvantage / 2 * -1;
        }
    }

    public boolean performCheck(Hero hero) {
        return true;
    }

    public enum SkillTag {
        DMG,
        HEAL,
        BUFF,
        CC,
        RESTOCK,
        SETUP,
        PEEL,
        REGROUP,
        MOVE
    }
    public Skill(Hero hero) {
        this.id = ++counter;
        this.hero = hero;
    }
    public void setToInitial() {
        Logger.logLn("Set to initial");
        this.targetRadius = 0;
        this.passive = false;
        this.isMove = false;
        this.movedWithEffects = new ArrayList<>();
        this.effects = new ArrayList<>();
        this.casterEffects = new ArrayList<>();
        this.dmgMultipliers = new ArrayList<>();
        this.targets = new ArrayList<>();
        this.manaCost = 0;
        this.lifeCost = 0;
        this.overheatCost = 0;
        this.actionCost = 1;
        this.accuracy = 100;
        this.critChance = 0;
        this.dmg = 0;
        this.heal = 0;
        this.cdMax = 0;
        this.shield = 0;
        this.comboEnabled = false;
        this.canMiss = true;
        this.countAsHits = 1;
        if (SpriteLibrary.hasSprite(this.getName())) {
            this.iconPixels = SpriteLibrary.getSprite(this.getName());
        } else {
            String basePath = this.hero != null? this.hero.basePath : "icons/skills";
            this.iconPixels = SpriteLibrary.sprite(Property.SKILL_ICON_SIZE,Property.SKILL_ICON_SIZE,Property.SKILL_ICON_SIZE,Property.SKILL_ICON_SIZE,
                    basePath + this.iconPath, 0);
            SpriteLibrary.addSprite(this.getName(), this.iconPixels);
        }
    }

    public void turn() {
        if (justCast) {
            justCast = false;
        } else if (cdCurrent > 0) {
            cdCurrent--;
        }
        setToInitial();
    }
    public abstract String getDescriptionFor(Hero hero);
    public void addSubscriptions() {

    }

    //SKILL LOGIC
    public void baseDamageChanges(Hero target, Hero caster){
        if (this.dmg > 0 || !this.dmgMultipliers.isEmpty()) {
            DmgChangesPayload dmgChangesPayload = new DmgChangesPayload()
                    .setDmg(this.dmg)
                    .setSkill(this)
                    .setTarget(target)
                    .setCaster(caster);
            Connector.fireTopic(Connector.BASE_DMG_CHANGES, dmgChangesPayload);
            this.dmg = dmgChangesPayload.dmg;
        }
    }
    public void baseHealChanges(Hero target, Hero caster) {
        if (this.heal > 0 || !this.healMultipliers.isEmpty()) {
            BaseHealChangesPayload baseHealChangesPayload = new BaseHealChangesPayload()
                    .setHeal(this.heal)
                    .setSkill(this)
                    .setTarget(target)
                    .setCaster(caster);
            Connector.fireTopic(Connector.BASE_HEAL_CHANGES, baseHealChangesPayload);
            this.heal = baseHealChangesPayload.heal;
        }
    }
    public void perform() {
        OnPerformPayload pl = new OnPerformPayload()
                .setSkill(this);
        Connector.fireTopic(Connector.ON_PERFORM, pl);
        this.hero.playAnimation(this.animationName);
        this.hero.payForSkill(this);
        this.setCdCurrent(this.getCdMax());
        this.justCast = true;
        Logger.logLn("Cd now " + this.getCdCurrent());
    }
    public void clearEffects() {
        this.effects = new ArrayList<>();
        this.casterEffects = new ArrayList<>();
        this.movedWithEffects = new ArrayList<>();
    }

    public void resolve() {
        if (this.targetType.equals(TargetType.ARENA)) {
            this.applySkillEffects(this.hero);
        } else {
            oncePerActivationEffect();
            for (Hero arenaTarget : targets) {
                if (this.targetType.equals(TargetType.SINGLE_ALLY)
                        || this.targetType.equals(TargetType.SINGLE_ALLY_IN_FRONT)
                        || this.targetType.equals(TargetType.SINGLE_ALLY_BEHIND)
                        || this.targetType.equals(TargetType.ALL_ALLY)) {
                    this.individualResolve(arenaTarget);
                } else {
                    Logger.logLn("Resolve " + this.getName() + " for " + arenaTarget.getName());
                    int evasion = arenaTarget.getStat( Stat.EVASION);
                    Logger.logLn("evasion:"+evasion);
                    int acc = hero.getStat(Stat.ACCURACY);
                    int hitChance = this.accuracy * acc / 100;
                    Logger.logLn("Hitchance:"+hitChance);
                    if (this.canMiss && !MyMaths.success(hitChance - evasion)) {
                        Logger.logLn("Missed");
                        continue;
                    }
                    Logger.logLn("Hit!");
                    this.individualResolve(arenaTarget);
                }
            }
        }
    }
    protected void oncePerActivationEffect() {}
    protected void individualResolve(Hero target) {
        Logger.logLn("Base DMG:" + this.dmg);
        this.baseDamageChanges(target, this.hero);
        this.baseHealChanges(target, this.hero);
        Logger.logLn("After base dmg changes:" + this.dmg);
        int dmg = getDmgWithMulti(target);
        Logger.logLn("After multipliers:" + dmg);
        DamageType dt = this.getDamageType();
        DamageMode dm = this.getDamageMode();
        Logger.logLn("DT:" + dt);
        int lethality = this.hero.getStat(Stat.LETHALITY) + this.getLethality();
        for (int i = 0; i < getCountsAsHits(); i++) {
            int dmgPerHit = dmg;
            Logger.logLn("Hit no:" + i+1);
            if (this.damageMode != null && this.damageMode.equals(DamageMode.PHYSICAL)) {
                int critChance = this.hero.getStat(Stat.CRIT_CHANCE);
                critChance += this.critChance;
                Logger.logLn("Crit Chance:"+critChance);
                if (MyMaths.success(critChance)) {
                    Logger.logLn("Crit!");
                    dmgPerHit*=2;
                    this.fireCritTrigger(target, this);
                }
            }
            if (dmgPerHit>0) {
                int doneDamage = target.damage(this.hero, dmgPerHit, dm, dt, lethality, this);
                Logger.logLn("done damage:"+doneDamage);
                this.fireDmgTrigger(target,this, doneDamage, dm, dt);
            }
            int heal = this.getHealWithMulti(target);
            Logger.logLn("Heal:" + heal);
            if (heal > 0) {
                target.heal(this.hero, heal, this, false);
            }
            this.applySkillEffects(target);
        }
    }
    public void fireCritTrigger(Hero target, Skill cast) {
        CriticalTriggerPayload criticalTriggerPayload = new CriticalTriggerPayload()
                .setTarget(target)
                .setCast(cast);
        Connector.fireTopic(Connector.CRITICAL_TRIGGER, criticalTriggerPayload);
    }

    public void fireDmgTrigger(Hero target, Skill cast, int damageDone, DamageMode dm, DamageType dt) {
        DmgTriggerPayload dmgTriggerPayload = new DmgTriggerPayload()
                .setTarget(target)
                .setCast(cast)
                .setDmgDone(damageDone)
                .setDamageMode(dm)
                .setDamageType(dt);
        Connector.fireTopic(Connector.DMG_TRIGGER, dmgTriggerPayload);
    }
    public void applySkillEffects(Hero target) {
        this.hero.addAllEffects(this.getCasterEffects(), this.hero);
        target.addAllEffects(this.getEffects(), this.hero);
        target.addResources(this.targetResources, this.hero);
    }
    protected int getHealMultiBonus() {
        return this.getMultiplierBonus(this.healMultipliers);
    }
    protected int getDmgMultiBonus() {
        return this.getMultiplierBonus(this.dmgMultipliers);
    }
    protected int getMultiplierBonus(List<Multiplier> multipliers) {
        if(multipliers ==null) {
            return 0;
        }
        int result = 0;
        for(Multiplier m : multipliers) {
            result +=(int)( m.percentage * this.hero.getStat(m.prof));
        }
        return result;
    }
    public int[] setupTargetMatrix() {
        int[] baseTargets = new int[]{0,1,2,3,4,5,6,7};
        int position = this.hero.getPosition();

        if (!this.hero.isTeam2() && this.targetType.equals(TargetType.SINGLE_ALLY)) {
            baseTargets = new int[]{0,1,2,3};
        } else if (this.hero.isTeam2() && this.targetType.equals(TargetType.SINGLE_ALLY)) {
            baseTargets = new int[]{4,5,6,7};
        } else if (!this.hero.isTeam2() && this.targetType.equals(TargetType.SINGLE_ALLY_IN_FRONT)) {
            baseTargets = Arrays.stream(baseTargets).filter(i->i<4 && i>position).toArray();
        } else if (this.hero.isTeam2() && this.targetType.equals(TargetType.SINGLE_ALLY_IN_FRONT)) {
            baseTargets = Arrays.stream(baseTargets).filter(i->i>3 && i<position).toArray();
        } else if (this.targetType.equals(TargetType.ENEMY_LINE)) {
            baseTargets = this.hero.isTeam2()?new int[]{0,1,2,3}:new int[]{4,5,6,7};
        } else if (this.targetType.equals(TargetType.SINGLE_ALLY_BEHIND)) {
            if (this.hero.isTeam2()) {
                baseTargets = Arrays.stream(baseTargets).filter(i->i>4 && i>position).toArray();
            } else {
                baseTargets = Arrays.stream(baseTargets).filter(i->i<3 && i<position).toArray();
            }
        }
        int range = this.distance;
        for (int i = 0; i < baseTargets.length; i++) {
            int dist = Math.abs(position-baseTargets[i]);
            if (dist>range) {
                baseTargets[i]=-1;
            }
            Hero possibleTarget = this.hero.arena.getAtPosition(baseTargets[i]);
            if (possibleTarget == null) {
                baseTargets[i] = -1;
            } else if (this.targetType.equals(TargetType.SINGLE) && possibleTarget.hasPermanentEffect(Threatening.class) > 0) {
                for (int j = 0; j < baseTargets.length; j++) {
                    if (baseTargets[j] != baseTargets[i]) {
                        baseTargets[j] = -1;
                    }
                }
            } else if (this.targetType.equals(TargetType.LINE)) {
                if (position == baseTargets[i]) {
                    baseTargets[i] = -1;
                }
            }
        }
        List<Integer> resultList = new ArrayList<>();
        for (int baseTarget : baseTargets) {
            if (baseTarget != -1) {
                resultList.add(baseTarget);
            }
        }
        return resultList.stream().mapToInt(i->i).toArray();
    }

    public int getLifeCost(Hero caster) {
        return lifeCost;
    }
    protected List<Effect> of(Effect[] effects){
        List<Effect> result = new ArrayList<>();
        Collections.addAll(result, effects);
        return result;
    }
    protected List<Multiplier> of(Multiplier[] multiplier){
        List<Multiplier> result = new ArrayList<>();
        Collections.addAll(result, multiplier);
        return result;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
    }

    public int getTargetRadius() {
        return targetRadius;
    }

    public void setTargetRadius(int targetRadius) {
        this.targetRadius = targetRadius;
    }

    public DamageType getDamageType() {
        return damageType;
    }

    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
    }

    public DamageMode getDamageMode() {
        return damageMode;
    }

    public void setDamageMode(DamageMode damageMode) {
        this.damageMode = damageMode;
    }

    public int getLethality() {
        return 0;
    }

    public boolean isPassive() {
        return passive;
    }

    public void setPassive(boolean passive) {
        this.passive = passive;
    }

    public boolean isMove() {
        return isMove;
    }

    public void setMove(boolean move) {
        isMove = move;
    }

    public List<Effect> getMovedWithEffects() {
        return movedWithEffects;
    }

    public void setMovedWithEffects(List<Effect> movedWithEffects) {
        this.movedWithEffects = movedWithEffects;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public void setEffects(List<Effect> effects) {
        this.effects = effects;
    }

    public List<Effect> getCasterEffects() {
        return casterEffects;
    }

    public void setCasterEffects(List<Effect> casterEffects) {
        this.casterEffects = casterEffects;
    }

    public List<Multiplier> getDmgMultipliers() {
        return dmgMultipliers;
    }

    public void setDmgMultipliers(List<Multiplier> dmgMultipliers) {
        this.dmgMultipliers = dmgMultipliers;
    }

    public List<Multiplier> getHealMultipliers() {
        return healMultipliers;
    }

    public void setHealMultipliers(List<Multiplier> healMultipliers) {
        this.healMultipliers = healMultipliers;
    }


    public int getManaCost() {
        return 0;
    }


    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }
    public int getFaithCost() {
        return faithCost;
    }
    public void setFaithCost(int faithCost) {
        this.faithCost = faithCost;
    }
    public int getLifeCost() {
        return lifeCost;
    }

    public void setLifeCost(int lifeCost) {
        this.lifeCost = lifeCost;
    }

    public int getActionCost() {
        return actionCost;
    }

    public void setActionCost(int actionCost) {
        this.actionCost = actionCost;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getDmg(Hero target) {
        return dmg;
    }
    public int getDmgWithMulti(Hero target) {
        return getDmg(target) + getDmgMultiBonus();
    }

    public int getHealWithMulti(Hero target) {
        int baseHeal = this.getHeal(target);
        int multiplierBonus = getHealMultiBonus();
        return baseHeal + multiplierBonus;
    }
    public int getHeal(Hero target) {return this.heal;}

    public void setPower(int power) {
        this.dmg = power;
    }

    public int getCdMax() {
        return cdMax;
    }

    public void setCdMax(int cdMax) {
        this.cdMax = cdMax;
    }

    public int getCdCurrent() {
        return cdCurrent;
    }

    public void setTargets(List<Hero> targets) {
        this.targets = targets;
    }

    public List<Hero> getTargets() {
        return targets;
    }

    public void setCdCurrent(int cdCurrent) {
        this.cdCurrent = cdCurrent;
    }

    public int getBlockedTurns() {
        return blockedTurns;
    }

    public void setBlockedTurns(int blockedTurns) {
        this.blockedTurns = blockedTurns;
    }

    public int getOverheatCost() {
        return overheatCost;
    }

    public void setOverheatCost(int overheatCost) {
        this.overheatCost = overheatCost;
    }

    public int getCountsAsHits() {
        return this.countAsHits;
    }
    public void setCountsAsHits(int countsAsHits) {
        this.countAsHits = countsAsHits;
    }
    public boolean isPrimary() {
        return this.primary;
    }
    public String getIcon() {
        return "aa_blaster";
    }

    public int[] getIconPixels() {
        return iconPixels;
    }

    public List<Resource> getTargetResources() {
        return targetResources;
    }

    public void setTargetResources(List<Resource> targetResources) {
        this.targetResources = targetResources;
    }

    public String getName() {
        return this.name;
    }

    public int getShield() {
        return shield;
    }

    public boolean isComboEnabled() {
        return comboEnabled;
    }

    public boolean isFaithGain() {
        return faithGain;
    }

    public String getTargetString() {
        StringBuilder builder = new StringBuilder();
        if (this.isPassive()) {
            return "";
        }
        builder.append("Target:");
        switch (this.targetType) {
            case SINGLE -> {
                builder.append("Any");
            }
            case SINGLE_ALLY -> {
                builder.append("Ally");
            }
            case SINGLE_ALLY_IN_FRONT -> {
                builder.append("Ally front");
            }
            case SINGLE_ALLY_BEHIND -> {
                builder.append("Ally behind");
            }
            case SELF -> {
                builder.append("Self");
            }
            case ONE_RDM -> {
                builder.append("Any Random");
            }
            case TWO_RDM -> {
                builder.append("Two Random");
            }
            case THREE_RDM -> {
                builder.append("Three Random");
            }
            case ALL -> {
                builder.append("All");
            }
            case ALL_ENEMY -> {
                builder.append("All enemies");
            }
            case ALL_ALLY -> {
                builder.append("All allies");
            }
            case LINE -> {
                builder.append("Line");
            }
            case FIRST_TWO_ENEMIES -> {
                builder.append("First two enemies");
            }
            case FIRST_ENEMY -> {
                builder.append("First enemy");
            }
            case ENEMY_LINE -> {
                builder.append("Enemy line");
            }
            case ARENA -> {
                builder.append("Arena");
            }
        }
        return builder.toString();
    }
    public String getDmgOrHealString() {
        StringBuilder builder = new StringBuilder();
        if (this.dmg != 0 || !this.dmgMultipliers.isEmpty()) {
            builder.append("DMG: ");
            if (this.dmg != 0) {
                builder.append(this.dmg);
            }
            if (!this.dmgMultipliers.isEmpty()) {
                for (Multiplier mult : this.dmgMultipliers) {
                    builder.append(" + ");
                    String profColor = mult.prof.getColorKey();
                    builder.append(profColor);
                    builder.append((int)(mult.percentage*100))
                            .append("% ")
                            .append(mult.prof.getIconString())
                            .append("{000} ");
                }
            }
        } else if (this.heal != 0 || !this.healMultipliers.isEmpty()) {
            builder.append("HEAL: ");
            if (this.heal != 0) {
                builder.append(this.heal);
            }
            if (!this.healMultipliers.isEmpty()) {
                for (Multiplier mult : this.healMultipliers) {
                    builder.append(" + ");
                    String profColor = mult.prof.getColorKey();
                    builder.append(profColor);
                    builder.append((int)(mult.percentage*100))
                            .append("% ")
                            .append(mult.prof.getIconString())
                            .append("{000} ");
                }
            }
        }
        return builder.toString();
    }
    public String getCostString() {
        if (this.isPassive()) {
            return "Passive";
        }
        StringBuilder costString = new StringBuilder();
        if (this.manaCost != 0 || this.faithCost != 0 || this.lifeCost != 0) {
            costString.append("Cost:");
        }
        if (this.manaCost != 0) {
            costString.append(Color.BLUE.getCodeString()).append(this.manaCost).append("{000}");
            costString.append(Stat.MANA.getIconString());
        }
        if (this.faithCost != 0) {
            costString.append(Color.DARKYELLOW.getCodeString()).append(this.faithCost).append("{000}");
            costString.append(Stat.FAITH.getIconString());
        }
        if (this.lifeCost != 0) {
            costString.append(Color.RED.getCodeString()).append(this.lifeCost).append("{000}");
            costString.append(Stat.LIFE.getIconString());
        }
//        if (this.actionCost != 0) {
//            costString.append("Action:");
//            costString.append(("["+TextEditor.ACTION_KEY+"]").repeat(Math.max(0, this.actionCost))).append(" ");
//        }
        if (this.cdMax != 0 || this.cdCurrent!=0) {
            costString.append("CD:");
            for (int i = 1; i <= this.cdMax || i <= this.cdCurrent; i++) {
                if (i <= this.cdCurrent) {
                    costString.append("[").append(TextEditor.TURN_CD_KEY).append("]");
                } else {

                    costString.append("[").append(TextEditor.TURN_KEY).append("]");
                }
            }
            costString.append(" ");
        }
        return costString.toString();
    }
    @Override
    public String toString() {
        return "\nSkill{" +
                "id=" + id +
                ", Hero=" + hero.getName() +
                ", name='" + getName() + '\'' +
                ", distance=" + distance +
                ", targetType=" + targetType +
                ", targetRadius=" + targetRadius +
                ", damageType=" + damageType +
                ", passive=" + passive +
                ", isMove=" + isMove +
                ", movedWithEffects=" + movedWithEffects +
                ", effects=" + effects +
                ", casterEffects=" + casterEffects +
                ", dmgMultipliers=" + dmgMultipliers +
                ", healMultipliers=" + healMultipliers +
                ", manaCost=" + manaCost +
                ", lifeCost=" + lifeCost +
                ", actionCost=" + actionCost +
                ", overheatCost=" + overheatCost +
                ", accuracy=" + accuracy +
                ", dmg=" + dmg +
                ", heal=" + heal +
                ", cdMax=" + cdMax +
                ", cdCurrent=" + cdCurrent +
                ", blockedTurns=" + blockedTurns +
                ", canMiss=" + canMiss +
                ", countAsHits=" + countAsHits +
                ", tags=" + tags +
                '}';
    }
}
