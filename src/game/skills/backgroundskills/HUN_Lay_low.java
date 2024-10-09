package game.skills.backgroundskills;

import framework.Logger;
import game.entities.Entity;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.changeeffects.effects.Bounty;
import game.skills.changeeffects.effects.Stealth;

public class HUN_Lay_low extends Skill {

    public HUN_Lay_low(Entity e) {
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
        HUN_Lay_low cast = new HUN_Lay_low(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Entity e) {
        return "When gaining a bounty stack, gain stealth for one turn.";
    }
    @Override
    public void effectAddedTrigger(Effect effect, Entity target) {
        if (this.entity == target &&
            effect.getClass().equals(Bounty.class)) {
            Logger.logLn(this.entity.name + ".HUN_Lay_low.effectAddedTrigger");
            this.entity.addEffect(new Stealth(2), null);
        }
    }
}
