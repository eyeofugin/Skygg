package game.skills;

import game.entities.Hero;

import java.util.List;

public class Effect {
    public enum ChangeEffectType {
        STATUS_INFLICTION,
        EFFECT,
        STAT_CHANGE;
    }

    public int turns = -1;
    public int stacks = 1;
    public int intensity;
    public int successChance= 100;
    public List<EffectCondition> conditions;
    public Hero origin;
    public Hero entity;

    public String name;
    public String description;
    public boolean stackable;
    public ChangeEffectType type;
    public Stat stat;

    public Effect() {

    }

    public Effect(Effect effect) {
        this.turns = effect.turns;
        this.stacks = effect.stacks;
        this.intensity = effect.intensity;
        this.successChance = effect.successChance;
        this.conditions = effect.conditions;
        this.origin = effect.origin;
        this.entity = effect.entity;
        this.name = effect.name;
        this.description = effect.description;
        this.stackable = effect.stackable;
        this.type = effect.type;
        this.stat = effect.stat;
    }

    public Effect getNew() {return new Effect();}
    public Effect(int turns) {
        this(turns,  100);
    }
    public Effect(int turns, int successChance) {
        this.turns = turns;
        this.successChance = successChance;
    }

    public void turn() {
        if(turns>=0) {
            this.turns--;
            turnLogic();
        }
    }
    public void turnLogic(){}

    public static Effect newStatChange(int intensity, Stat stat) {
        Effect effect = new Effect();
        effect.stackable = false;
        effect.type = ChangeEffectType.STAT_CHANGE;
        effect.stat = stat;
        effect.intensity = intensity;
        return effect;
    }

    public static Effect getRdmBuff(int intensity) {
        Effect effect = new Effect();
        effect.stackable = false;
        effect.type = ChangeEffectType.STAT_CHANGE;
        effect.stat = Stat.getRdmStat();
        effect.intensity = intensity;
        return effect;
    }
    public static Effect getRdmDebuff(int intensity) {
        return getRdmBuff(-1*intensity);
    }

    public int getDamageChanges(Entity caster, Entity target, Skill damagingSkill, int result, Stat damageType, boolean simulated) {
        return result;
    }
    public int getHealChanges(Entity caster, Entity target, Skill damagingSkill, int result) {
        return result;
    }
    public int getActionChanges() {
        return 0;
    }
    public boolean canPerformCheck(Skill cast) {
        return true;
    }
    public int getAccuracyFor(Skill cast, int accuracy) {
        return accuracy;
    }
    public int getTargetedStat(Stat stat, Skill targeted) {
        return 0;
    }
    public void dmgTrigger(Entity target, Skill cast, int doneDamage) {}
    public void replacementEffect(Skill cast){}
    public void changeEffects(Skill cast) {}

    public int getCastingStat(Stat stat, Skill cast) {
        return 0;
    }

    public boolean performSuccessCheck(Skill cast) {
        return true;
    }

    @Override
    public String toString() {
        return "Effect{" +
                "turns=" + turns +
                ", stacks=" + stacks +
                ", intensity=" + intensity +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", stat=" + stat +
                '}';
    }
}