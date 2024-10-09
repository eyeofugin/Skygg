package game.entities;

import framework.Logger;
import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import framework.states.Arena;
import game.objects.Equipment;
import game.objects.equipments.basic.BlasterPistol;
import game.objects.equipments.basic.Fists;
import game.skills.*;
import game.skills.changeeffects.effects.Cover;
import game.skills.changeeffects.effects.Resilient;
import game.skills.changeeffects.statusinflictions.Disenchanted;
import game.skills.itemskills.AutoAttack;
import utils.MyMaths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class Entity extends GUIElement {

    public Animator anim;
    public Arena arena;

    public int position = -1;

    public int id;
    public String name;
    public String appearanceName;
    public boolean enemy;
    public Skill[] skills;
    public List<Equipment> equipments = new ArrayList<>();

    public int[] portrait = new int[64*64];

    //Stats
    protected Map<Stat, Integer> stats = new HashMap<>();
    public String resource;

    public List<Effect> currentEffects = new ArrayList<>();

    //AI
    public int effectivePosition;
    public ROLE role = ROLE.SUPPORT;
    public enum ROLE {
        SUPPORT,
//        DPS,
//        BRAWL,
//        TANK
    }
    public static final String HEALTH = "health";
    public static final String MANA = "mana";
    public static final String FAITH = "faith";

    public Entity() {

    }
    public Entity(Arena arena) {
        this.arena = arena;
    }

    public Entity(Arena arena, boolean enemy, int position) {
        this.arena = arena;
        this.enemy = enemy;
        this.position = position;
    }

    public Entity(int id, String name, Map<Stat, Integer> stats, Skill[] skills) {
        this.id = id;
        this.name = name;
        this.skills = skills;
        this.stats = stats;
    }

    public static Entity getStndEntity() {
        Entity entity = new Entity() {
            @Override
            public void update(int frame) {}};
        BlasterPistol bp = new BlasterPistol();
        bp.inventoryPosition = 0;
        entity.equipments.add(0, bp);

        entity.skills = new Skill[]{
                new AutoAttack(entity, bp)};
        entity.stats.put(Stat.MAX_LIFE, 40);
        entity.stats.put(Stat.CURRENT_LIFE, 15);
        entity.stats.put(Stat.LIFE_REGAIN, 2);
        entity.stats.put(Stat.TURN_START_ACTION, 1);
        entity.stats.put(Stat.CURRENT_ACTION, 1);
        entity.stats.put(Stat.STRENGTH, 10);
        entity.stats.put(Stat.PRECISION, 10);
        entity.stats.put(Stat.WILLPOWER, 10);
        entity.stats.put(Stat.VITALITY, 10);
        entity.stats.put(Stat.REFLEXES, 10);
        entity.stats.put(Stat.HARMONY, 10);
        entity.stats.put(Stat.SPEED, 10);
        entity.stats.put(Stat.ACCURACY, 100);
        entity.stats.put(Stat.EVASION, 0);
        entity.stats.put(Stat.LETHALITY, 0);
        entity.stats.put(Stat.CRIT_CHANCE, 10);

        return entity;
    }


//MANAGEMENT
    //Stats
    public void changeStatTo(Stat stat, int value) {
        this.stats.put(stat, value);
    }
    public void addToStat(Stat stat, int value) {
        this.stats.put(stat, this.stats.get(stat) + value);
    }
    public int getBaseStat(Stat stat) {
        return this.stats.get(stat);
    }
    public int getStat(Stat stat) {
        if (stat == null) {
            return 0;
        }
        try {
            int s = this.stats.get(stat);
            if (!(this.hasPermanentEffect(Disenchanted.class)>0)) {
                s += getEquipmentStat(stat);
            }
            s += getSkillStat(stat);
            //multiplier lastly
            s *= getEffectStat(stat);
            return s;
        }catch(Exception e) {
            Logger.logLn("tried to get stat " + stat.name() + " of " + this.name);
            e.printStackTrace();
        }
        return 0;
    }
    private int getEquipmentStat(Stat stat) {
        int p = 0;
        for (Equipment e : this.equipments) {
            if (e!=null) {
                p += e.getStat(stat);
            }
        }
        return p;
    }
    private double getEffectStat(Stat stat) {
        int p = 0;
        for (Effect e : this.currentEffects) {
            if (e != null
                    && e.type.equals(Effect.ChangeEffectType.STAT_CHANGE)) {
                if (e.stat.equals(stat)) {
                    Logger.logLn("Add intensity" + e.intensity);
                    p += e.intensity;
                }
            }
        }
        return Math.max(0,1+ p*0.25);
    }
    private int getSkillStat(Stat stat) {
        int p = 0;
        for (Skill s : this.skills) {
            if (s!=null) {
                p += s.getStat(stat);
            }
        }
        return p;
    }

    public int getTargetedStat(Skill targetingSkill, Stat stat) {
        if (stat == null) {
            return 0;
        }
        switch (stat) {
            case MELEE -> stat=Stat.VITALITY;
            case RANGED -> stat=Stat.REFLEXES;
            case FORCE -> stat=Stat.HARMONY;
        }
        int baseStat = getStat(stat);
        try {
            baseStat += getEquipmentTargetedStat(stat, targetingSkill);
            baseStat += getSkillTargetedStat(stat, targetingSkill);
            baseStat += getEffectTargetedStat(stat, targetingSkill);
            return baseStat;
        }catch(Exception e) {
            Logger.logLn("tried to get stat " + stat.name() + " of " + this.name);
        }
        return 0;
    }
    private int getEquipmentTargetedStat(Stat stat, Skill targeted) {
        int p = 0;
        for (Equipment e : this.equipments) {
            p += e.getTargetedStat(stat, targeted);
        }
        return p;
    }
    private int getSkillTargetedStat(Stat stat, Skill targeted) {
        int p = 0;
        for (Skill s : this.skills) {
            if (s != null) {
                p += s.getTargetedStat(stat, targeted);
            }
        }
        return p;
    }
    private int getEffectTargetedStat(Stat stat, Skill targeted) {
        int p = 0;
        for (Effect e : this.currentEffects) {
            if (e != null) {
                p += e.getTargetedStat(stat, targeted);
            }
        }
        return p;
    }
    public int getCastingStat(Skill cast, Stat stat) {
        if (stat == null) {
            return 0;
        }
        int baseStat = getStat(stat);
        try {
            baseStat += getEquipmentCastingStat(stat, cast);
            baseStat += getSkillCastingStat(stat, cast);
            baseStat += getEffectCastingStat(stat, cast);
            return baseStat;
        }catch(Exception e) {
            Logger.logLn("tried to get stat " + stat.name() + " of " + this.name);
        }
        return 0;
    }
    private int getEquipmentCastingStat(Stat stat, Skill cast) {
        int p = 0;
        for (Equipment e : this.equipments) {
            p += e.getCastingStat(stat, cast);
        }
        return p;
    }
    private int getSkillCastingStat(Stat stat, Skill cast) {
        int p = 0;
        for (Skill s : this.skills) {
            if (s!= null) {
                p += s.getCastingStat(stat, cast);
            }
        }
        return p;
    }
    private int getEffectCastingStat(Stat stat, Skill cast) {
        int bonus = 0;
        for (Effect e : this.currentEffects) {
            if (e.type == Effect.ChangeEffectType.STAT_CHANGE && e.stat == stat) {
                bonus += e.intensity;
            } else {
                bonus += e.getCastingStat(stat, cast);
            }
        }
        return bonus;
    }

    public int getDistBonusFor(Skill s) {
        int bonus = 0;
        //bonus += getEquipmentDistBonusFor(s);
        //bonus += getEffectDistBonusFor(s);
        //bonus += getSkillDistBonusFor(s);
        return bonus;
    }
    public int getAccuracyFor(Skill s, int dist) {
        if (s.getAccuracy() == -1 || s.getTargetType().equals(TargetType.SELF)) {
            return 100;
        }

        int baseAccuracy = s.getAccuracy();
        double distMultiplier = (double) (16 - 2 * dist) / 10;
        try {
            baseAccuracy = getCastingStat(s, Stat.ACCURACY);
//            baseAccuracy = getEquipmentAccuracy(s, baseAccuracy);
//            baseAccuracy = getEffectAccuracy(s, baseAccuracy);
//            baseAccuracy = getSkillAccuracy(s, baseAccuracy);
        } catch(Exception e) {
            Logger.logLn("tried to get accuracy of " + s.name);
        }
        return (int) (baseAccuracy * distMultiplier);
    }
    private int getEquipmentAccuracy(Skill cast, int accuracy) {
        for (Equipment e : this.equipments) {
            accuracy = e.getAccuracyFor(cast, accuracy);
        }
        return accuracy;
    }
    private int getEffectAccuracy(Skill cast, int accuracy) {
        for (Effect e : this.currentEffects) {
            accuracy = e.getAccuracyFor(cast, accuracy);
        }
        return accuracy;
    }
    private int getSkillAccuracy(Skill cast, int accuracy) {
        for (Skill e : this.skills) {
            accuracy = e.getAccuracyFor(cast, accuracy);
        }
        return accuracy;
    }


    //TURNLOGIC

    public void prepareUpdate() {
        this.removePermanentEffect(Cover.class);
    }
    public void update() {
        this.updateSkills();
        this.updateEquipments();
    }
    public void updateSkills() {
        for (Skill s : this.skills) {
            if (s!= null) {
                s.update();
            }
        }
    }
    public void updateEquipments() {
        for (Equipment e : this.equipments) {
            e.update();
        }
    }
    public void prepareCast() {
        for (Skill s : this.skills) {
            if (s!= null) {
                s._cast = s.getCast();
                this.arena.replacementEffects(s._cast);
                this.arena.changeEffects(s._cast);
            }
        }
    }

    public void startOfMatch() {
        this.startOfMatchSkills();
    }
    public void startOfMatchSkills() {
        for (Skill s : this.skills) {
            if (s!= null) {
                s.startOfMatch();
                s.startOfTurn();
                s.update();
            }
        }
    }
    public void startOfTurn() {
        Logger.logLn(this.name + " statOfTurn");
        this.startOfTurnSkills();
        this.startOfTurnEquipments();
        int actionInfliction = actionAmountChange();
        Logger.logLn(actionInfliction + " actioninfliction");
        int resultingActions = Math.max(0,this.getStat(Stat.CURRENT_ACTION) - actionInfliction);
        changeStatTo(Stat.CURRENT_ACTION, resultingActions);
        updateEffects();
    }
    public void startOfTurnSkills() {
        for (Skill s : this.skills) {
            if (s!= null) {
                s.startOfTurn();
                s.update();
            }
        }
    }
    public void startOfTurnEquipments() {
        for (Equipment e : this.equipments) {
            e.startOfTurn();
            e.update();
        }
    }
    private int actionAmountChange() {
        int infl = 0;
        infl+=getEffectActionAmountChange();
        infl+=getEquipmentActionAmountChange();
        infl+=getSkillActionAmountChange();
        return infl;
    }
    private int getEffectActionAmountChange() {
        int result = 0;
        for (Effect e: this.currentEffects) {
            result += e.getActionChanges();
        }
        return result;
    }
    private int getEquipmentActionAmountChange() {
        int result = 0;
        for (Equipment e: this.equipments) {
            result += e.getCurrentActionAmountChange();
        }
        return result;
    }
    private int getSkillActionAmountChange() {
        int result = 0;
        for (Skill s: this.skills) {
            if (s!= null) {
                result += s.getCurrentActionAmountChange();
            }
        }
        return result;
    }

    public void endOfTurn() {
        this.endOfTurnSkills();
        for (Effect effect : this.currentEffects) {
            effect.turn();

        }
        this.currentEffects.removeIf(e -> e.turns == 0);
        changeStatTo(Stat.CURRENT_ACTION,
                Math.min(this.stats.get(Stat.CURRENT_LIFE) + this.stats.get(Stat.LIFE_REGAIN), this.stats.get(Stat.MAX_LIFE)));
        changeStatTo(Stat.CURRENT_ACTION, this.stats.get(Stat.TURN_START_ACTION));
    }
    public void endOfTurnSkills() {
        for (Skill s : this.skills) {
            if (s!= null) {
                s.endOfTurn();
                s.update();
            }
        }
    }
    //EFFECTLOGIC
    private void updateEffects() {
        this.currentEffects.removeIf(e -> e.turns == 0);
        this.currentEffects.removeIf(e -> e.stackable && e.stacks<=0);
        this.currentEffects.removeIf(e -> e.type== Effect.ChangeEffectType.STAT_CHANGE && e.intensity==0);
    }
    public void addAllEffects(List<Effect> effects, Entity caster) {
        for (Effect e : effects) {
            this.addEffect(e, caster);
        }
    }

    public void addEffect(Effect e, Entity caster) {
        Logger.logLn("Add effect " + e.name + "|"+e.type +"|"+e.stat+"|"+e.intensity +" to " + this.name + " " + this.position);

        if (isAddEffectFailure(e, caster)) {
            return;
        }
        boolean added = false;
        if (e.stackable) {
            for (Effect currentEffect : currentEffects) {
                if (currentEffect.getClass().equals(e.getClass())) {
                    currentEffect.stacks++;
                    added = true;
                    this.effectAdded(e);
                }
            }
            if (!added) {
                Effect newEffect = e.getNew();
                newEffect.origin = caster;
                newEffect.entity = this;
                this.currentEffects.add(newEffect);
                this.effectAdded(e);
            }
        }else if (e.type.equals(Effect.ChangeEffectType.STAT_CHANGE)) {
            for (Effect currentEffect : currentEffects) {
                if (currentEffect.type.equals(Effect.ChangeEffectType.STAT_CHANGE)
                        && currentEffect.stat == e.stat) {
                    currentEffect.intensity += e.intensity;
                    added = true;
                    this.effectAdded(e);
                }
            }
            if (!added) {
                Effect newEffect = new Effect(e);
                newEffect.entity = this;
                newEffect.origin = caster;
                this.currentEffects.add(newEffect);
                this.effectAdded(e);
            }
        }else {
            removePermanentEffect(e.getClass());
            Effect newEffect = e.getNew();
            newEffect.origin = caster;
            newEffect.entity = this;
            this.currentEffects.add(newEffect);
            this.effectAdded(e);
        }
    }
    private boolean isAddEffectFailure(Effect e, Entity entity) {
        boolean effectFailure = !MyMaths.success(e.successChance);
        if (effectFailure) {
            return true;
        }
        boolean resilient = (e.type.equals(Effect.ChangeEffectType.STATUS_INFLICTION)
                &&hasPermanentEffect(Resilient.class)>0);
        if (resilient) { return true;}
        return effectFailureCheck(e);
    }
    private boolean effectFailureCheck(Effect e) {
        boolean effectFailure = false;
        for (Skill s : Arrays.stream(this.arena.getAllLivingEntities())
                .flatMap(entity->Arrays.stream(entity.skills))
                .filter(Objects::nonNull).toList()) {
            effectFailure = effectFailure || s.effectFailure(e, this);
        }
        return effectFailure;
    }
    private void effectAdded(Effect e) {
        this.effectAddedSkillTrigger(e, this);
        this.effectAddedEquipmentTrigger(e, this);
    }
    private void effectAddedSkillTrigger(Effect e, Entity target) {
        for (Skill s : Arrays.stream(this.arena.getAllLivingEntities())
                .flatMap(entity->Arrays.stream(entity.skills))
                .filter(Objects::nonNull).toList()) {
            s.effectAddedTrigger(e, target);
        }
    }
    private void effectAddedEquipmentTrigger(Effect e, Entity target) {
        for (Equipment equipment : Arrays.stream(this.arena.getAllLivingEntities())
                .flatMap(entity->entity.equipments.stream()).toList()) {
            equipment.effectAddedTrigger(e, target);
        }
    }

    public <T extends Effect> int hasPermanentEffect(Class<T> clazz) {
        int amount = 0;
        for (Effect currentEffect : currentEffects) {
            if (currentEffect.getClass().equals(clazz)) {
                amount+=currentEffect.stacks;
            }
        }
        return amount;
    }

    public boolean hasStatBuff() {
        for (Effect currentEffect : currentEffects) {
            if (currentEffect.type.equals(Effect.ChangeEffectType.STAT_CHANGE)
                    && currentEffect.intensity>0) {
                return true;
            }
        }
        return false;
    }
    public <T extends Effect> void decreaseEffectStack(Class<T> clazz) {
        if (this.hasPermanentEffect(clazz) > 0) {
            this.getPermanentEffect(clazz).stacks--;
            this.updateEffects();
        }
    }
    public <T extends Effect> void removePermanentEffect(Class<T> clazz) {
        Logger.logLn(this.name + ".removePermanentEffect:" + clazz.getName());
        this.currentEffects.removeIf(effect -> effect.getClass().equals(clazz));
    }
    public <T extends Effect> Effect getPermanentEffect(Class<T> clazz) {
        for (Effect currentEffect : currentEffects) {
            if (currentEffect.getClass().equals(clazz)) {
                return currentEffect;
            }
        }
        return null;
    }

    //CASTING SKILLS
    public <T extends Skill> boolean hasSkill(Class<T> clazz) {
        for (Skill s : this.skills) {
            if (s!= null && s.getClass().equals(clazz)) {
                return true;
            }
        }
        return false;
    }
    public boolean canPerform(Skill s) {
        return this.stats.get(Stat.CURRENT_LIFE) >= s.getLifeCost() &&
                this.stats.get(Stat.CURRENT_ACTION) >= s.getActionCost() &&
                this.getPrimary().getCurrentOverheat()+s.getOverheatCost()<=this.getPrimary().getMaxOverheat() &&
                s.getCdCurrent()<=0 &&
                !(s.getOverheatCost()>0 && this.getPrimary().isOverheated()) && !s.isPassive() &&
                this.arena.canPerformCheck(s);
    }
    public void payForSkill(Skill s) {
        addToStat(Stat.CURRENT_LIFE, -1*s.getLifeCost(this));
        addToStat(Stat.CURRENT_ACTION, -1*s.getActionCost());
        this.getPrimary().changeOverheat(s.getOverheatCost());
        Logger.logLn("Paid life:"+s.getLifeCost(this));
        Logger.logLn("Paid action:"+s.getActionCost());
        Logger.logLn("Paid overheat:"+s.getOverheatCost());
    }

    public void heal(Entity caster, int heal, Skill skill) {
        int resultHeal = this.getHealChanges(caster, this, skill, heal);
        addToStat(Stat.CURRENT_LIFE, resultHeal);
        if (this.stats.get(Stat.CURRENT_LIFE) > this.stats.get(Stat.MAX_LIFE))
            changeStatTo(Stat.CURRENT_LIFE, this.stats.get(Stat.MAX_LIFE));
    }

    public void cleanse() {
        this.currentEffects = new ArrayList<>();
    }

    public int simulateHealInPercentages(Entity caster, int heal, Skill skill) {
        int pureHeal = this.getHealChanges(caster, this, skill, heal);
        int actualHeal = Math.min(pureHeal, this.getStat(Stat.MAX_LIFE) - this.getStat(Stat.CURRENT_LIFE));
        return actualHeal * 100 / this.getStat(Stat.MAX_LIFE);
    }

    public int damage(Entity caster, int damage, Stat dmgType, int lethality, Skill skill) {
        int def = this.getTargetedStat(skill, dmgType);
        int result = MyMaths.getDamage(damage, def, lethality);
        result = this.getDamageChanges(caster, this, skill, result, dmgType, false);
        System.out.println("dmg:"+result);
        Logger.logLn("1play dmg animation of " + this.name + " at pos " + this.position);
        playAnimation("damagedL", "damagedR", true);
        addToStat(Stat.CURRENT_LIFE, -1*result);
        return result;
    }
    public int simulateDamageInPercentages(Entity caster, int damage, Stat dmgType, int lethality, Skill skill) {
        int def = this.getTargetedStat(skill, dmgType);
        int result = MyMaths.getDamage(damage, def, lethality);
        result = this.getDamageChanges(caster, this, skill, result, dmgType, true);
        return result * 100 / this.getStat(Stat.MAX_LIFE);
    }
    private int getHealChanges(Entity caster, Entity target, Skill damagingSkill, int result) {
        //Assoziativgestz <-> Distributivgesetz daher nur * änderungen plz future self
        result = this.getEquipmentHealChanges(caster, target, damagingSkill, result);
        result = this.getEffectHealChanges(caster, target, damagingSkill, result);
        result = this.getSkillHealChanges(caster, target, damagingSkill, result);
        return result;
    }
    public int getEquipmentHealChanges(Entity caster, Entity target, Skill damagingSkill, int result) {
        for (Equipment e : Arrays.stream(this.arena.getAllLivingEntities())
                .flatMap(entity->entity.equipments.stream()).toList()) {
            result = e.getHealChanges(caster, target, damagingSkill, result);
        }
        return result;
    }
    public int getSkillHealChanges(Entity caster, Entity target, Skill damagingSkill, int result) {
        for (Skill s : Arrays.stream(this.arena.getAllLivingEntities())
                .flatMap(entity->Arrays.stream(entity.skills))
                .filter(Objects::nonNull).toList()) {
            result = s.getHealChanges(caster, target, damagingSkill, result);
        }
        return result;
    }
    public int getEffectHealChanges(Entity caster, Entity target, Skill damagingSkill, int result) {
        for (Effect e : Arrays.stream(this.arena.getAllLivingEntities())
                .flatMap(entity -> entity.currentEffects.stream()).toList()) {
            if (e != null) {
                result = e.getHealChanges(caster, target, damagingSkill, result);
            }
        }
        return result;
    }


    private int getDamageChanges(Entity caster, Entity target, Skill damagingSkill, int result, Stat dmgType, boolean simulated) {
        //Assoziativgestz <-> Distributivgesetz daher nur * änderungen plz future self
        result = this.getEquipmentDamageChanges(caster, target, damagingSkill, result, dmgType, simulated);
        result = this.getEffectDamageChanges(caster, target, damagingSkill, result, dmgType, simulated);
        result = this.getSkillDamageChanges(caster, target, damagingSkill, result, dmgType, simulated);
        return result;
    }
    public int getEquipmentDamageChanges(Entity caster, Entity target, Skill damagingSkill, int result, Stat damageType, boolean simulated) {
        for (Equipment e : Arrays.stream(this.arena.getAllLivingEntities())
                .flatMap(entity->entity.equipments.stream()).toList()) {
            result = e.getDamageChanges(caster, target, damagingSkill, result, damageType, simulated);
        }
        return result;
    }
    public int getSkillDamageChanges(Entity caster, Entity target, Skill damagingSkill, int result, Stat damageType, boolean simulated) {
        for (Skill s : Arrays.stream(this.arena.getAllLivingEntities())
                .flatMap(entity->Arrays.stream(entity.skills))
                .filter(Objects::nonNull).toList()) {
            result = s.getDamageChanges(caster, target, damagingSkill, result, damageType, simulated);
        }
        return result;
    }
    public int getEffectDamageChanges(Entity caster, Entity target, Skill damagingSkill, int result, Stat damageType, boolean simulated) {
        for (Effect e : Arrays.stream(this.arena.getAllLivingEntities())
                .flatMap(entity -> entity.currentEffects.stream()).toList()) {
            if (e != null) {
                result = e.getDamageChanges(caster, target, damagingSkill, result, damageType, simulated);
            }
        }
        return result;
    }

    public void isTargeted(Skill s) {

    }
    public TargetMode getTargetModeForSkill(Skill targetingSkill) {
        TargetMode targetMode = null;
        for (Skill ownSkill : this.skills) {
            TargetMode tmTemp = ownSkill.getTargetMode(targetingSkill);
            targetMode = tmTemp.equals(TargetMode.NORMAL) ? targetMode : tmTemp;
        }
        return targetMode;
    }
    public int effectDamage(int damage) {
        Logger.logLn("2play dmg animation of " + this.name + " at pos " + this.position);
        this.anim.playAnimation(this.enemy ? "damagedL" : "damagedR", true);
        addToStat(Stat.CURRENT_LIFE, -1*damage);
        return damage;
    }


    @Override
    public int[] render() {
        return new int[0];
    }
    public String getResourceString(String resource) {
        if (resource == null) {
            return "";
        }
        switch(resource) {
            case HEALTH:
                return getHealthString();
            case MANA:
                return getManaString();
            case FAITH:
                return getFaithString();
        }
        return "";
    }

    private String getFaithString() {
        return "faith";
    }

    private String getManaString() {
        return "mana";
    }
    public static Color getResourceColor(String resource) {
        if (resource == null) {
            return Color.BLACK;
        }
        switch(resource) {
            case HEALTH:
                return Color.GREEN;
            case MANA:
                return Color.BLUE;
            case FAITH:
                return Color.DARKYELLOW;
        }
        return null;
    }

    public String getHealthString() {
        return this.stats.get(Stat.CURRENT_LIFE) + "(+" + this.stats.get(Stat.LIFE_REGAIN) + ")/" + this.stats.get(Stat.MAX_LIFE);
    }
    public void playAnimation(String animL, String animR, boolean waitfor) {
        if (this.anim != null) {
            Logger.logLn("play skill animation of " + this.name + " at pos " + this.position);
            this.anim.playAnimation(this.enemy ? animL : animR, waitfor);
        }
    }
    public void playAnimation(String animL, String animR) {
        this.playAnimation(animL, animR, true);
    }
    public List<Entity> getAllies() {
        List<Entity> result = new ArrayList<>();
        for (Entity e : this.arena.getAllLivingEntities()) {
            if (e.enemy==this.enemy && e!=this) {
                result.add(e);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "\n position=" + position +
                ",\n id=" + id +
                ",\n name='" + name + '\'' +
                ",\n appearanceName='" + appearanceName + '\'' +
                ",\n enemy=" + enemy +
                ",\n skills=" + Arrays.toString(skills) +
                ",\n equipments=" + equipments +
                ",\n stats=" + stats +
                ",\n currentEffects=" + currentEffects +
                ",\n effectivePosition=" + effectivePosition +
                ",\n role=" + role +
                '}';
    }

    //AutoAttack
    public Equipment getPrimary() {
        for (Equipment e: this.equipments) {
            if (e.inventoryPosition == 0) {
                return e;
            }
        }
        return new Fists();
    }
    public double getResourcePercentage(String resource) {
        if (resource == null) {
            return 0.0;
        }
        switch (resource) {
            case HEALTH:
                return ((double)this.getStat(Stat.CURRENT_LIFE)) / this.getStat(Stat.MAX_LIFE);
            case MANA, FAITH:
                return ((double)this.getStat(Stat.CURRENT_MANA)) / this.getStat(Stat.MAX_MANA);
        }
        return 0.0;
    }
    public int getCurrentLifePercentage() {
        return this.getStat(Stat.CURRENT_LIFE) * 100 / this.getStat(Stat.MAX_LIFE);
    }
    public int getMissingLifePercentage() {
        return (100 - getCurrentLifePercentage());
    }
}