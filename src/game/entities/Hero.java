package game.entities;

import framework.Logger;
import framework.connector.Connector;
import framework.connector.payloads.ActionInflictionPayload;
import framework.connector.payloads.CastReplacementPayload;
import framework.connector.payloads.DmgChangesPayload;
import framework.connector.payloads.EffectAddedPayload;
import framework.connector.payloads.EffectFailurePayload;
import framework.connector.payloads.EndOfTurnPayload;
import framework.connector.payloads.GetStatPayload;
import framework.connector.payloads.HealChangesPayload;
import framework.connector.payloads.StartOfTurnPayload;
import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import framework.states.Arena;
import game.objects.Equipment;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.Stat;
import utils.MyMaths;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Hero extends GUIElement {

    public Animator anim;
    public Arena arena;
    public Hero Hero;

    public static final String RESOURCE_MANA = "MANA";
    public static final String RESOURCE_FAITH = "FAITH";
    public static final String RESOURCE_LIFE = "LIFE";

    private int id;
    private String name;
    private Skill[] skills;
    private List<Equipment> equipments = new ArrayList<>();
    private String portraitName;

    private Map<Stat, Integer> stats = new HashMap<>();
    private List<Effect> effects = new ArrayList<>();
    private Stat secondaryResource;

    public int position;
    public boolean enemy;


    public Hero(String name, Map<Stat, Integer> stats, Skill[] skills) {
        this.name = name;
        this.stats = stats;
        this.skills = skills;
    }

//StatMagic
    public int getStat(Stat stat, Skill skill) {
        if (stat == null) {
            return 0;
        }
        int baseStat = this.stats.get(stat);

        int statChange = getStatChange(stat, skill);
        return baseStat + statChange;
    }

    private int getStatChange(Stat stat, Skill skill) {

        GetStatPayload payload = new GetStatPayload()
                .setStat(stat)
                .setHero(this)
                .setSkill(skill);
        Connector.fireTopic(Connector.GET_STAT, payload);

        return payload.value;
    }

    public void changeStatTo(Stat stat, int value) {
        this.stats.put(stat, value);
    }

    public void addToStat(Stat stat, int value) {
        this.stats.put(stat, Math.max(this.stats.get(stat) + value, 0));
    }

    public int getSkillAccuracy(Skill skill) {
        if (skill.getAccuracy() == -1 || Skill.MAX_ACC_TARGET_TYPES.contains(skill.getTargetType())) {
            return 100;
        }
        return skill.getAccuracy();
    }
    public double getResourcePercentage(Stat resource) {
        if (resource == null) {
            return 0.0;
        }
        switch (resource) {
            case LIFE:
                return ((double)this.stats.get(Stat.CURRENT_LIFE)) / this.stats.get(Stat.LIFE);
            case MANA, FAITH:
                return ((double)this.stats.get(Stat.CURRENT_MANA)) / this.stats.get(Stat.MANA);
        }
        return 0.0;
    }
    public int getCurrentLifePercentage() {
        return this.stats.get(Stat.CURRENT_LIFE) * 100 / this.stats.get(Stat.LIFE);
    }
    public int getMissingLifePercentage() {
        return (100 - getCurrentLifePercentage());
    }

//EffectMagic
    public <T extends Effect> int getPermanentEffectStacks(Class<T> clazz) {
        int amount = 0;
        for (Effect currentEffect : effects) {
            if (currentEffect.getClass().equals(clazz)) {
                amount+=currentEffect.stacks;
            }
        }
        return amount;
    }

//TurnMagic
    public void prepareCast() {
        for (Skill skill : this.skills) {
            if (skill != null) {
                skill._cast = skill.getCast();
                CastReplacementPayload payload = new CastReplacementPayload()
                        .setCast(skill._cast);
                Connector.fireTopic(Connector.CAST_REPLACEMENT, payload);
                Connector.fireTopic(Connector.CAST_CHANGE, payload);
            }
        }
    }
    public void startOfTurn() {
        StartOfTurnPayload startOfTurnPayload = new StartOfTurnPayload();
        Connector.fireTopic(this.id + Connector.START_OF_TURN, startOfTurnPayload);

        setActions();
    }
    public void endOfTurn() {
        EndOfTurnPayload endOfTurnPayload = new EndOfTurnPayload();
        Connector.fireTopic(this.id + Connector.END_OF_TURN, endOfTurnPayload);
        effectTurn();
        heal(this, getStat(Stat.LIFE_REGAIN, null), null);
    }
    private void setActions() {
        ActionInflictionPayload actionInflictionPayload = new ActionInflictionPayload();
        Connector.fireTopic(Connector.ACTION_INFLICTION, actionInflictionPayload);
        this.addToStat(Stat.CURRENT_ACTION, actionInflictionPayload.getInfliction());
    }



//EffectMagic
    private void effectTurn() {
        for (Effect effect : effects) {
            effect.turn();
        }
        cleanUpEffect();
    }
    private void cleanUpEffect() {
        this.effects.removeIf(e -> e.turns == 0);
        this.effects.removeIf(e -> e.stackable && e.stacks<=0);
        this.effects.removeIf(e -> e.type== Effect.ChangeEffectType.STAT_CHANGE && e.intensity==0);
    }
    public void addAllEffects(List<Effect> effects, Hero caster) {
        for (Effect effect : effects) {
            this.addEffect(effect, caster);
        }
    }
    public void addEffect(Effect effect, Hero caster) {

        if (getEffectFailure(effect, caster)) {
            return;
        }

        boolean added = false;

        EffectAddedPayload effectAddedPayload = new EffectAddedPayload()
                .setEffect(effect)
                .setCaster(caster);

        if (effect.stackable) {
            for (Effect currentEffect : effects) {
                if (currentEffect.getClass().equals(effect.getClass())) {
                    currentEffect.stacks++;
                    added = true;
                    Connector.fireTopic(Connector.EFFECT_ADDED, effectAddedPayload);
                }
            }
            if (!added) {
                Effect newEffect = effect.getNew();
                newEffect.origin = caster;
                newEffect.Hero = this;
                this.effects.add(newEffect);
                Connector.fireTopic(Connector.EFFECT_ADDED, effectAddedPayload);
            }
        }else if (effect.type.equals(Effect.ChangeEffectType.STAT_CHANGE)) {
            for (Effect currentEffect : effects) {
                if (currentEffect.type.equals(Effect.ChangeEffectType.STAT_CHANGE)
                        && currentEffect.stat == effect.stat) {
                    currentEffect.intensity += effect.intensity;
                    added = true;
                    Connector.fireTopic(Connector.EFFECT_ADDED, effectAddedPayload);
                }
            }
            if (!added) {
                Effect newEffect = new Effect(effect);
                newEffect.Hero = this;
                newEffect.origin = caster;
                this.effects.add(newEffect);
                Connector.fireTopic(Connector.EFFECT_ADDED, effectAddedPayload);
            }
        }else {
            removePermanentEffect(effect.getClass());
            Effect newEffect = effect.getNew();
            newEffect.origin = caster;
            newEffect.Hero = this;
            this.effects.add(newEffect);
            Connector.fireTopic(Connector.EFFECT_ADDED, effectAddedPayload);
        }
    }
    private boolean getEffectFailure(Effect effect, Hero caster) {
        EffectFailurePayload payload = new EffectFailurePayload()
                .setEffect(effect)
                .setCaster(caster);
        Connector.fireTopic(Connector.EFFECT_FAILURE, payload);
        return payload.isFailure();
    }

    public <T extends Effect> int hasPermanentEffect(Class<T> clazz) {
        int amount = 0;
        for (Effect currentEffect : effects) {
            if (currentEffect.getClass().equals(clazz)) {
                amount+=currentEffect.stacks;
            }
        }
        return amount;
    }
    public <T extends Effect> void decreaseEffectStack(Class<T> clazz) {
        if (this.hasPermanentEffect(clazz) > 0) {
            this.getPermanentEffect(clazz).stacks--;
            this.cleanUpEffect();
        }
    }
    public <T extends Effect> void removePermanentEffect(Class<T> clazz) {
        Logger.logLn(this.name + ".removePermanentEffect:" + clazz.getName());
        this.effects.removeIf(effect -> effect.getClass().equals(clazz));
    }
    public <T extends Effect> Effect getPermanentEffect(Class<T> clazz) {
        for (Effect currentEffect : effects) {
            if (currentEffect.getClass().equals(clazz)) {
                return currentEffect;
            }
        }
        return null;
    }

//SkillMagic
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
                s.getCdCurrent()<=0 && !s.isPassive() &&
                this.arena.canPerformCheck(s);
    }
    public void payForSkill(Skill s) {
        addToStat(Stat.CURRENT_LIFE, -1*s.getLifeCost(this));
        addToStat(Stat.CURRENT_ACTION, -1*s.getActionCost());
        Logger.logLn("Paid life:"+s.getLifeCost(this));
        Logger.logLn("Paid action:"+s.getActionCost());
    }

    public void cleanse() {
        this.effects = new ArrayList<>();
    }

    public int simulateHealInPercentages(Hero caster, int heal, Skill skill) {
        HealChangesPayload healChangesPayload = new HealChangesPayload()
                .setCaster(caster)
                .setTarget(this)
                .setSkill(skill)
                .setHeal(heal);
        Connector.fireTopic(Connector.HEAL_CHANGES, healChangesPayload);
        int pureHeal = healChangesPayload.getHeal();
        int actualHeal = Math.min(pureHeal, this.stats.get(Stat.LIFE) - this.stats.get(Stat.CURRENT_LIFE));
        return actualHeal * 100 / this.stats.get(Stat.LIFE);
    }
    public void heal(Hero caster, int heal, Skill skill) {
        HealChangesPayload healChangesPayload = new HealChangesPayload()
                .setCaster(caster)
                .setTarget(this)
                .setSkill(skill)
                .setHeal(heal);
        Connector.fireTopic(Connector.HEAL_CHANGES, healChangesPayload);
        int resultHeal = healChangesPayload.getHeal();
        addToStat(Stat.CURRENT_LIFE, resultHeal);
        if (this.stats.get(Stat.CURRENT_LIFE) > this.stats.get(Stat.LIFE))
            changeStatTo(Stat.CURRENT_LIFE, this.stats.get(Stat.LIFE));
    }

    public int damage(Hero caster, int damage, Stat dmgType, int lethality, Skill skill) {

        int def = getStat(getDefenseStatFor(dmgType), skill);
        int result = MyMaths.getDamage(damage, def, lethality);
        DmgChangesPayload dmgChangesPayload = new DmgChangesPayload()
                .setCaster(caster)
                .setTarget(this)
                .setSkill(skill)
                .setDmg(result)
                .setDmgtype(dmgType)
                .setSimulate(false);
        Connector.fireTopic(Connector.DMG_CHANGES, dmgChangesPayload);
        System.out.println("dmg:"+result);
        Logger.logLn("1play dmg animation of " + this.name + "/"+this.id);
        playAnimation("damagedL", "damagedR", true);
        addToStat(Stat.CURRENT_LIFE, -1*result);
        return result;
    }
    private Stat getDefenseStatFor(Stat dmgType) {
        if (Objects.requireNonNull(dmgType) == Stat.NORMAL) {
            return Stat.FORCE;
        }
        return Stat.ENDURANCE;
    }
    public int simulateDamageInPercentages(Hero caster, int damage, Stat dmgType, int lethality, Skill skill) {
        int def = getStat(getDefenseStatFor(dmgType), skill);
        int result = MyMaths.getDamage(damage, def, lethality);
        DmgChangesPayload dmgChangesPayload = new DmgChangesPayload()
                .setCaster(caster)
                .setTarget(this)
                .setSkill(skill)
                .setDmg(result)
                .setDmgtype(dmgType)
                .setSimulate(true);
        Connector.fireTopic(Connector.DMG_CHANGES, dmgChangesPayload);
        return result * 100 / this.stats.get(Stat.LIFE);
    }

    public int effectDamage(int damage) {
        Logger.logLn("2play dmg animation of " + this.name + " at pos " + this.Hero.position);
        this.anim.playAnimation(this.Hero.enemy ? "damagedL" : "damagedR", true);
        addToStat(Stat.CURRENT_LIFE, -1*damage);
        return damage;
    }


//GUI
    public void playAnimation(String animL, String animR, boolean waitfor) {
        if (this.anim != null) {
            Logger.logLn("play skill animation of " + this.name);
            this.anim.playAnimation(this.Hero.enemy ? animL : animR, waitfor);
        }
    }
    public String getResourceString(Stat resource) {
        if (resource == null) {
            return "";
        }
        return switch (resource) {
            case LIFE -> getHealthString();
            case MANA -> getManaString();
            case FAITH -> getFaithString();
            default -> "";
        };
    }

    private String getFaithString() {
        return this.stats.get(Stat.CURRENT_FAITH) + "/" + this.stats.get(Stat.FAITH);
    }

    private String getManaString() {
        return this.stats.get(Stat.CURRENT_MANA) + "(+" + this.stats.get(Stat.MANA_REGAIN) + ")/" + this.stats.get(Stat.MANA);
    }

    public String getHealthString() {
        return this.stats.get(Stat.CURRENT_LIFE) + "(+" + this.stats.get(Stat.LIFE_REGAIN) + ")/" + this.stats.get(Stat.LIFE);
    }

    public static Color getResourceColor(Stat stat) {
        if (stat == null) {
            return Color.BLACK;
        }
        return switch (stat) {
            case LIFE -> Color.GREEN;
            case MANA -> Color.BLUE;
            case FAITH -> Color.DARKYELLOW;
            default -> null;
        };
    }


//GETTERS SETTERS

    public int getId() {
        return id;
    }

    public Hero setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Hero setName(String name) {
        this.name = name;
        return this;
    }

    public Skill[] getSkills() {
        return skills;
    }

    public Hero setSkills(Skill[] skills) {
        this.skills = skills;
        return this;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public Hero setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
        return this;
    }

    public String getPortraitName() {
        return portraitName;
    }

    public Hero setPortraitName(String portraitName) {
        this.portraitName = portraitName;
        return this;
    }

    public Map<Stat, Integer> getStats() {
        return stats;
    }

    public Hero setStats(Map<Stat, Integer> stats) {
        this.stats = stats;
        return this;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public Hero setEffects(List<Effect> effects) {
        this.effects = effects;
        return this;
    }

    public Stat getSecondaryResource() {
        return secondaryResource;
    }

    public Hero setSecondaryResource(Stat secondaryResource) {
        this.secondaryResource = secondaryResource;
        return this;
    }
}
