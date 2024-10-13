package game.skills.changeeffects.effects;

import framework.Logger;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

public class Stealth extends Effect {
    private static final int PERCENTAGE = 50;
    public Stealth() {
        this.name = "Stealth";
        this.stackable = false;
        this.intensity = PERCENTAGE;
        this.description =  PERCENTAGE + "% chance to dodge single target attacks.";
        this.type = ChangeEffectType.EFFECT;
    }

    public Stealth(int turns) {
        this();
        this.turns = turns;
    }
    @Override
    public Stealth getNew() {
        return new Stealth(this.turns);
    }
    @Override
    public int getTargetedStat(Stat stat, Skill targeted) {
        if (Stat.EVASION.equals(stat) && targeted.getTargetType().equals(TargetType.SINGLE)) {
            Logger.logLn(this.Hero.name + ".Stealth.getTargetedStat");
            return intensity;
        }
        return 0;
    }
}