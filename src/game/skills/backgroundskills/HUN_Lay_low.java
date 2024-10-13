package game.skills.backgroundskills;

import framework.Logger;
import game.entities.Hero;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.changeeffects.effects.Bounty;
import game.skills.changeeffects.effects.Stealth;

public class HUN_Lay_low extends Skill {

    public HUN_Lay_low(Hero e) {
        super(e);
        this.name="hun_lay_low";
        this.translation="Lay low";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.passive = true;
    }
    @Override
    public Skill getCast() {
        HUN_Lay_low cast = new HUN_Lay_low(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Hero e) {
        return "When gaining a bounty stack, gain stealth for one turn.";
    }
    @Override
    public void effectAddedTrigger(Effect effect, Hero target) {
        if (this.Hero == target &&
            effect.getClass().equals(Bounty.class)) {
            Logger.logLn(this.Hero.name + ".HUN_Lay_low.effectAddedTrigger");
            this.Hero.addEffect(new Stealth(2), null);
        }
    }
}
