package game.skills.changeeffects.effects;

import framework.Logger;
import game.entities.Hero;
import game.skills.Effect;
import game.skills.Skill;

public class BountyTarget extends Effect {
    public BountyTarget() {
        this.name = "Bounty Target";
        this.description = "A bounty target.";
        this.stackable = false;
        this.type = ChangeEffectType.EFFECT;
    }

    @Override
    public BountyTarget getNew() {
        return new BountyTarget();
    }
    @Override
    public void dmgTrigger(Hero target, Skill cast, int doneDamage) {
        if (this.Hero == target) {
            Logger.logLn(this.Hero.name + ".BountyTarget.dmgTrigger");
            this.Hero.removePermanentEffect(BountyTarget.class);
            cast.Hero.addEffect(new Bounty(), this.Hero);
        }
    }
}
