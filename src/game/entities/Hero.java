package game.entities;

import framework.connector.Connector;
import framework.connector.payloads.ActionInflictionPayload;
import framework.connector.payloads.CastReplacementPayload;
import framework.connector.payloads.EndOfTurnPayload;
import framework.connector.payloads.GetStatPayload;
import framework.connector.payloads.StartOfTurnPayload;
import game.objects.Equipment;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.Stat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hero {

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
    private String secondaryResource;

    public Hero(String name, Map<Stat, Integer> stats, Skill[] skills) {
        this.name = name;
        this.stats = stats;
        this.skills = skills;
    }

//StatMagic
    public int getStat(Stat stat) {
        if (stat == null) {
            return 0;
        }
        int baseStat = this.stats.get(stat);

        GetStatPayload payload = new GetStatPayload()
                .setValue(baseStat)
                .setStat(stat)
                .setHero(this);
        Connector.fireTopic(Connector.GET_STAT, payload);
        return baseStat + payload.intVal;
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
        heal(this, getStat(Stat.LIFE_REGAIN), null);
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

        if (effect.stackable) {
            for (Effect currentEffect : effects) {
                if (currentEffect.getClass().equals(effect.getClass())) {
                    currentEffect.stacks++;
                    added = true;
                    this.effectAdded(effect);
                }
            }
            if (!added) {
                Effect newEffect = effect.getNew();
                newEffect.origin = caster;
                newEffect.entity = this;
                this.effects.add(newEffect);
                this.effectAdded(effect);
            }
        }else if (effect.type.equals(Effect.ChangeEffectType.STAT_CHANGE)) {
            for (Effect currentEffect : effects) {
                if (currentEffect.type.equals(Effect.ChangeEffectType.STAT_CHANGE)
                        && currentEffect.stat == effect.stat) {
                    currentEffect.intensity += effect.intensity;
                    added = true;
                    this.effectAdded(effect);
                }
            }
            if (!added) {
                Effect newEffect = new Effect(effect);
                newEffect.entity = this;
                newEffect.origin = caster;
                this.effects.add(newEffect);
                this.effectAdded(effect);
            }
        }else {
            removePermanentEffect(effect.getClass());
            Effect newEffect = effect.getNew();
            newEffect.origin = caster;
            newEffect.entity = this;
            this.effects.add(newEffect);
            this.effectAdded(effect);
        }

    }
    private boolean getEffectFailure(Effect effect, Hero caster) {
        ConnectionPayload payload = new ConnectionPayload()
                .setEffect(effect)
                .setHero(caster);
        Connector.fireTopic(Connector.EFFECT_FAILURE, payload);
        return payload.boolVal;
    }

//CombatMagic
    public void heal(Hero caster, int heal, Skill skill) {
        int resultHeal = this.getHealChanges(caster, this, skill, heal);
        addToStat(Stat.CURRENT_LIFE, resultHeal);
        if (this.stats.get(Stat.CURRENT_LIFE) > this.stats.get(Stat.MAX_LIFE))
            changeStatTo(Stat.CURRENT_LIFE, this.stats.get(Stat.MAX_LIFE));
    }
}
