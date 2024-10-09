package game.skills.backgroundskills;

import framework.Logger;
import game.entities.Entity;
import game.skills.Effect;
import game.skills.Skill;

public class MAR_Crit_Buff extends Skill {

    public MAR_Crit_Buff(Entity e) {
        super(e);
        this.name="mar_crit_buff";
        this.translation="Crit Buff";
        this.description= "getDescription()";
        this.passive = true;
    }
    @Override
    public Skill getCast() {
        MAR_Crit_Buff cast = new MAR_Crit_Buff(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Entity e) {
        return "Crits will grant ~ a random buff.";
    }

    @Override
    public void critTrigger(Entity target, Skill cast) {
        if (cast.entity == this.entity) {
            Logger.logLn(this.entity.name + ".mar_critbuff.crittrigger");
            Effect effect = Effect.getRdmBuff(1);
            this.entity.addEffect(effect, this.entity);
        }
    }
}
