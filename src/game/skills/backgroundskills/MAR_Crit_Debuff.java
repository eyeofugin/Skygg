package game.skills.backgroundskills;

import framework.Logger;
import game.entities.Entity;
import game.skills.Effect;
import game.skills.Skill;

public class MAR_Crit_Debuff extends Skill {

    public MAR_Crit_Debuff(Entity e) {
        super(e);
        this.name="mar_crit_debuff";
        this.translation="Crit Debuff";
        this.description= "getDescription()";
        this.passive = true;
    }
    @Override
    public Skill getCast() {
        MAR_Crit_Debuff cast = new MAR_Crit_Debuff(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Entity e) {
        return "Crits will grant the target a random debuff.";
    }

    @Override
    public void critTrigger(Entity target, Skill cast) {
        if (cast.entity == this.entity) {
            Logger.logLn(this.entity.name + ".mar_critdebuff.crittrigger");
            Effect debuff = Effect.getRdmDebuff(1);
            target.addEffect(debuff, this.entity);
        }
    }
}
