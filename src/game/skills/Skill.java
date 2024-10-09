package game.skills;

import framework.Logger;
import framework.graphics.text.TextEditor;
import framework.resources.SpriteLibrary;
import game.entities.Entity;
import game.entities.Multiplier;
import game.objects.Equipment;
import utils.MyMaths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Skill {

    private static int counter;
    public int id;
    public final Entity entity;
    public Equipment equipment;
    public String name;
    public String translation;
    public String translationShort;
    public String description;
    public String animationR;
    public String animationL;
    public String icon;
    public int[] iconPixels;

    public Skill _cast;
    public Skill ogSkill;

    protected int distance = 0;
    protected TargetType targetType = TargetType.SINGLE;
    protected int targetRadius = 0;

    protected Stat damageType = null;
    protected boolean passive = false;
    protected boolean weaponSkill = false;
    protected boolean autoAttack = false;
    protected boolean isMove = false;
    protected List<Effect> movedWithEffects = new ArrayList<>();
    protected List<Effect> effects = new ArrayList<>();
    protected List<Effect> casterEffects = new ArrayList<>();

    protected List<Multiplier> dmgMultipliers = new ArrayList<>();
    protected List<Multiplier> healMultipliers = new ArrayList<>();

    protected List<Entity> targets = new ArrayList<>();

    protected int manaCost = 0;
    protected int lifeCost = 0;
    protected int faithCost = 0;
    protected int actionCost = 0;
    protected int overheatCost = 0;
    protected int accuracy = 100;
    public int dmg = 0;
    public int heal = 0;
    protected int powerPercentage = 0;
    protected int summonId = 0;
    protected int enhancementId = 0;
    protected int cdMax = 0;
    protected int cdCurrent = 0;
    protected int blockedTurns = 0;
    protected boolean canMiss = true;
    protected int countAsHits = 1;



    protected Stat primaryMultiplier;


//AI
    public List<AiSkillTag> tags = new ArrayList<>();
    public int getAIRating(Entity target, int beatDownMeter){return 0;}
    public enum AiSkillTag {
        DMG,
        HEAL,
        BUFF,
        CC,
        RESTOCK,
        SETUP,
        PEEL,
        REGROUP
    }

    public Skill(Entity entity) {
        this.id = ++counter;
        this.entity = entity;
        this.iconPixels = SpriteLibrary.sprites.get(this.getClass().getName());
    }

    public Skill(Skill s) {
        this.id = s.id;
        this._cast = null;
        this.entity = s.entity;
        this.name = s.name;
        this.translation = s.translation;
        this.description = s.description;
        this.animationR = s.animationR;
        this.animationL = s.animationL;
        this.distance = s.distance;
        this.targetType = s.targetType;
        this.targetRadius = s.targetRadius;
        this.damageType = s.damageType;
        this.passive = s.passive;
        this.weaponSkill = s.weaponSkill;
        this.isMove = s.isMove;
        this.movedWithEffects = s.movedWithEffects;
        this.effects = s.effects;
        this.casterEffects = s.casterEffects;
        this.dmgMultipliers = s.dmgMultipliers;
        this.targets = s.targets;
        this.manaCost = s.manaCost;
        this.faithCost = s.faithCost;
        this.lifeCost = s.lifeCost;
        this.overheatCost = s.overheatCost;
        this.actionCost = s.actionCost;
        this.accuracy = s.accuracy;
        this.dmg = s.dmg;
        this.heal = s.heal;
        this.powerPercentage = s.powerPercentage;
        this.summonId = s.summonId;
        this.enhancementId = s.enhancementId;
        this.cdMax = s.cdMax;
        this.cdCurrent = s.cdCurrent;
        this.blockedTurns = s.blockedTurns;
        this.canMiss = s.canMiss;
        this.autoAttack = s.autoAttack;
        this.ogSkill = s;
        this.postInit();
    }
    public void copyFrom(Skill s) {
        this.id = s.id;
        this._cast = null;
        this.name = s.name;
        this.translation = s.translation;
        this.description = s.description;
        this.animationR = s.animationR;
        this.animationL = s.animationL;
        this.distance = s.distance;
        this.targetType = s.targetType;
        this.targetRadius = s.targetRadius;
        this.damageType = s.damageType;
        this.passive = s.passive;
        this.weaponSkill = s.weaponSkill;
        this.isMove = s.isMove;
        this.movedWithEffects = s.movedWithEffects;
        this.effects = s.effects;
        this.casterEffects = s.casterEffects;
        this.dmgMultipliers = s.dmgMultipliers;
        this.targets = s.targets;
        this.manaCost = s.manaCost;
        this.faithCost = s.faithCost;
        this.lifeCost = s.lifeCost;
        this.overheatCost = s.overheatCost;
        this.actionCost = s.actionCost;
        this.accuracy = s.accuracy;
        this.dmg = s.dmg;
        this.heal = s.heal;
        this.powerPercentage = s.powerPercentage;
        this.summonId = s.summonId;
        this.enhancementId = s.enhancementId;
        this.cdMax = s.cdMax;
        this.cdCurrent = s.cdCurrent;
        this.blockedTurns = s.blockedTurns;
        this.canMiss = s.canMiss;
        this.autoAttack = s.autoAttack;
        this.ogSkill = s;
    }
    public abstract Skill getCast();

    //SKILL LOGIC
    public void postInit() {}
    public void update() {}
    public void startOfMatch() {}
    public void startOfTurn() {
        this.blockedTurns = Math.max(0,this.blockedTurns-1);
        if (this.blockedTurns<=0) {
            this.cdCurrent--;
        }
    }
    public void endOfTurn() {}
    private void performEffect() {}
    public void changeEffects(Skill s) {}
    public void baseDamageChanges(){}
    public void replacementEffect(Skill s){}
    public int getDamageChanges(Entity caster, Entity target, Skill damagingSkill, int result, Stat damageType, boolean simulated) {return result;}
    public int getHealChanges(Entity caster, Entity target, Skill damagingSkill, int result) {return result;}
    public void dmgTrigger(Entity target, Skill cast, int doneDamage) {}
    public void critTrigger(Entity target, Skill cast) {}
    public void effectAddedTrigger(Effect effect, Entity target) {}
    public boolean effectFailure(Effect effect, Entity target) {return false;}
    public TargetMode getTargetMode(Skill targetingSkill) {
        return TargetMode.NORMAL;
    }
    public Boolean performSuccessCheck(Skill cast) {
        return true;
    }
    public boolean canPerformCheck(Skill cast) {
        return true;
    }
    public int getStat(Stat stat){return 0;}
    public int getTargetedStat(Stat stat, Skill targeted) {return 0;}
    public int getCastingStat(Stat stat, Skill cast) {return 0;}
    public int getAccuracyFor(Skill cast, int accuracy) {return accuracy;}
    private void skillMissedTrigger() {}
    private void skillCounteredTrigger() {}
    public int getCurrentActionAmountChange(){return 0;}

    public void perform() {
        this.entity.playAnimation(animationL,animationR);
        this.entity.payForSkill(this);
        this.ogSkill.setCdCurrent(this.getCdMax());
        Logger.logLn("Cd now " + this.ogSkill.getCdCurrent());
        this.performEffect();
    }
    public void resetCast() {
        this._cast = null;
    }

    public void resolve() {
        for (Entity target : targets) {
            if (this.targetType.equals(TargetType.SINGLE_ALLY)
                    || this.targetType.equals(TargetType.SINGLE_ALLY_IN_FRONT)) {

                target.isTargeted(this);
                this.individualResolve(target);
            } else {
                Logger.logLn("Resolve " + this.name + " for " + target.name);
                int dist = Math.abs(entity.position - target.position);
                Logger.logLn("Dist:"+dist);
                int evasion = target.getTargetedStat(this, Stat.EVASION);
                Logger.logLn("evasion:"+evasion);
                int hitChance = entity.getAccuracyFor(this, dist);
                Logger.logLn("Hitchance:"+hitChance);
                if (this.canMiss && !MyMaths.success(hitChance - evasion)) {
                    Logger.logLn("Missed");
                    this.skillMissedTrigger();
                    continue;
                }
                Logger.logLn("Hit!");
                target.isTargeted(this);
                this.individualResolve(target);
            }
        }
        this.resetCast();
    }
    protected void individualResolve(Entity target) {
        Logger.logLn("Base DMG:" + this.dmg);
        this.baseDamageChanges();
        Logger.logLn("After base dmg changes:" + this.dmg);
        int dmg = this.getDamage();
        Logger.logLn("After multipliers:" + dmg);
        Stat dt = this.getDamageType();
        Logger.logLn("DT:" + dt);
        int lethality = this.entity.getCastingStat(this, Stat.LETHALITY);
        Logger.logLn("Lethality:"+lethality);
        for (int i = 0; i < getCountsAsHits(); i++) {
            Logger.logLn("Hit no:" + i+1);
            if (this.isWeaponSkill()) {
                int critChance = this.entity.getCastingStat(this, Stat.CRIT_CHANCE);
                Logger.logLn("Crit Chance:"+critChance);
                if (MyMaths.success(critChance)) {
                    Logger.logLn("Crit!");
                    this.dmg*=2;
                    this.entity.arena.critTrigger(target, this);
                }
            }
            if (dmg>0) {
                int doneDamage = target.damage(this.entity, dmg, dt, lethality, this);
                Logger.logLn("done damage:"+doneDamage);
                this.entity.arena.dmgTrigger(target,this, doneDamage);
            }
            int heal = this.getHeal();
            Logger.logLn("Heal:" + heal);
            if (heal > 0) {
                target.heal(this.entity, heal, this);
            }
            this.applySkillEffects(target);
        }
    }
    public void applySkillEffects(Entity target) {
        this.entity.addAllEffects(this.getCasterEffects(), this.entity);
        target.addAllEffects(this.getEffects(), this.entity);
    }
    protected int getHealMultiBonus() {
        return this.getMultiplierBonus(this.healMultipliers);
    }
    public String getHealMultiplierString(Entity e) {
        return getMultiString(e, this.healMultipliers);
    }
    protected int getDmgMultiBonus() {
        return this.getMultiplierBonus(this.dmgMultipliers);
    }
    public String getDmgMultiplierString(Entity e) {
        return getMultiString(e, this.dmgMultipliers);
    }
    protected int getMultiplierBonus(List<Multiplier> multipliers) {
        if(multipliers ==null) {
            return 0;
        }
        int result = 0;
        for(Multiplier m : multipliers) {
            result +=(int)( m.percentage * this.entity.getCastingStat(this, m.prof));
        }
        return result;
    }
    public String getMultiString(Entity e, List<Multiplier> multipliers) {
        if (multipliers == null) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        for (Multiplier m : multipliers) {
            result.append("(")
                    .append(m.percentage)
                    .append("[").append(m.prof.getIconKey()).append("]")
                    .append("=")
                    .append((int)(m.percentage * e.getStat(m.prof)))
                    .append(")");
        }
        return result.toString();
    }
    public int[] setupTargetMatrix() {
        int[] baseTargets = new int[]{0,1,2,3,4,5,6,7};
        int position = this.entity.position;

        if (!this.entity.enemy && this.targetType.equals(TargetType.SINGLE_ALLY)) {
            baseTargets = new int[]{0,1,2,3};
        } else if (this.entity.enemy && this.targetType.equals(TargetType.SINGLE_ALLY)) {
            baseTargets = new int[]{4,5,6,7};
        } else if (!this.entity.enemy && this.targetType.equals(TargetType.SINGLE_ALLY_IN_FRONT)) {
            baseTargets = Arrays.stream(baseTargets).filter(i->i<4 && i>position).toArray();
        } else if (this.entity.enemy && this.targetType.equals(TargetType.SINGLE_ALLY_IN_FRONT)) {
            baseTargets = Arrays.stream(baseTargets).filter(i->i>3 && i<position).toArray();
        }
        int range = this.getDistance();
        for (int i = 0; i < baseTargets.length; i++) {
            int dist = Math.abs(position-baseTargets[i]);
            if (dist>range) {
                baseTargets[i]=-1;
            }
            if (this.targetType.equals(TargetType.SINGLE)) {
                Entity possibleTarget = this.entity.arena.getAtPosition(baseTargets[i]);
                if (possibleTarget!=null) {
                    TargetMode targetMode = possibleTarget.getTargetModeForSkill(this);
                    if (targetMode!=null) {
                        if (targetMode.equals(TargetMode.MUST_NOT)) {
                            baseTargets[i]=-1;
                        } else if (targetMode.equals(TargetMode.MUST)) {
                            for (int j = 0; j < baseTargets.length; j++) {
                                if (baseTargets[j] != baseTargets[i]) {
                                    baseTargets[j] = -1;
                                }
                            }
                        }
                    }
                }
            }
            if (this.targetType.equals(TargetType.LINE)) {
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
    public String getDescriptionFor(Entity p) {return "skill description";}

    public int getDistance() {
        int bonus = this.entity.getDistBonusFor(this);
        return distance + bonus;
    }
    public int getLifeCost(Entity caster) {
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

    public Stat getDamageType() {
        return damageType;
    }

    public void setDamageType(Stat damageType) {
        this.damageType = damageType;
    }

    public boolean isPassive() {
        return passive;
    }

    public void setPassive(boolean passive) {
        this.passive = passive;
    }

    public boolean isWeaponSkill() {
        return weaponSkill;
    }

    public void setWeaponSkill(boolean weaponSkill) {
        this.weaponSkill = weaponSkill;
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

    public List<Entity> getTargets() {
        return targets;
    }

    public void setTargets(List<Entity> targets) {
        this.targets = targets;
    }

    public int getManaCost() {
        return manaCost;
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

    public int getDamage() {
        int baseDamage = this.dmg;
        int multiplierBonus = getDmgMultiBonus();
        Logger.logLn("multubonus:" + multiplierBonus);
        return baseDamage+multiplierBonus;
    }

    public int getHeal() {
        int baseHeal = this.heal;
        int multiplierBonus = getHealMultiBonus();
        return baseHeal + multiplierBonus;
    }

    public void setPower(int power) {
        this.dmg = power;
    }

    public int getPowerPercentage() {
        return powerPercentage;
    }

    public void setPowerPercentage(int powerPercentage) {
        this.powerPercentage = powerPercentage;
    }

    public int getSummonId() {
        return summonId;
    }

    public void setSummonId(int summonId) {
        this.summonId = summonId;
    }

    public int getEnhancementId() {
        return enhancementId;
    }

    public void setEnhancementId(int enhancementId) {
        this.enhancementId = enhancementId;
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

    public void setCdCurrent(int cdCurrent) {
        this.cdCurrent = cdCurrent;
    }

    public int getBlockedTurns() {
        return blockedTurns;
    }

    public void setBlockedTurns(int blockedTurns) {
        this.blockedTurns = blockedTurns;
    }

    public boolean isAutoAttack() {
        return autoAttack;
    }

    public void setAutoAttack(boolean autoAttack) {
        this.autoAttack = autoAttack;
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
    public Stat getPrimaryMultiplier() {
        return primaryMultiplier;
    }
    public String getIcon() {
        return "aa_blaster";
    }
    public String getButtonName() {
        if (this.translationShort == null) {
            return this.translation;
        }
        return this.translationShort;
    }

    public String getCostString() {
        if (this.isPassive()) {
            return "Passive";
        }
        StringBuilder costString = new StringBuilder();
        if (this.manaCost != 0) {
            costString.append(this.manaCost);
            costString.append("["+ TextEditor.MANA+"]");
        }
        if (this.faithCost != 0) {
            costString.append(this.faithCost);
            costString.append("["+ TextEditor.FAITH+"]");
        }
        if (this.lifeCost != 0) {
            costString.append(this.lifeCost);
            costString.append("["+ TextEditor.LIFE+"]");
        }
        if (this.actionCost != 0) {
            costString.append(("["+TextEditor.ACTION_KEY+"]").repeat(Math.max(0, this.actionCost)));
        }
        if (this.manaCost != 0) {
            costString.append(("["+TextEditor.TURN_KEY+"]").repeat(Math.max(0, this.cdMax)));
        }
        if (this.manaCost != 0) {
            costString.append(("["+TextEditor.OVERHEAT_INACTIVE_KEY+"]").repeat(Math.max(0, this.overheatCost)));
        }
        return costString.toString();
    }
    public void setPrimaryMultiplier(Stat primaryMultiplier) {
        this.primaryMultiplier = primaryMultiplier;
    }

    @Override
    public String toString() {
        return "\nSkill{" +
                "id=" + id +
                ", entity=" + entity.name +
                ", equipment=" + (equipment!=null) +
                ", name='" + name + '\'' +
                ", distance=" + distance +
                ", targetType=" + targetType +
                ", targetRadius=" + targetRadius +
                ", damageType=" + damageType +
                ", passive=" + passive +
                ", weaponSkill=" + weaponSkill +
                ", autoAttack=" + autoAttack +
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
                ", powerPercentage=" + powerPercentage +
                ", cdMax=" + cdMax +
                ", cdCurrent=" + cdCurrent +
                ", blockedTurns=" + blockedTurns +
                ", canMiss=" + canMiss +
                ", countAsHits=" + countAsHits +
                ", primaryMultiplier=" + primaryMultiplier +
                ", tags=" + tags +
                '}';
    }
}
