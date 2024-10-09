package game.skills.changeeffects.effects;

import framework.Logger;
import game.entities.Entity;
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
    public void dmgTrigger(Entity target, Skill cast, int doneDamage) {
        if (this.entity == target) {
            Logger.logLn(this.entity.name + ".BountyTarget.dmgTrigger");
            this.entity.removePermanentEffect(BountyTarget.class);
            cast.entity.addEffect(new Bounty(), this.entity);
        }
    }
}
