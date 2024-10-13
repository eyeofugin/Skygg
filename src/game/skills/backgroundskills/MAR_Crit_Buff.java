package game.skills.backgroundskills;

import framework.Logger;
import game.entities.Hero;
import game.skills.Effect;
import game.skills.Skill;

public class MAR_Crit_Buff extends Skill {

    public MAR_Crit_Buff(Hero e) {
        super(e);
        this.name="mar_crit_buff";
        this.translation="Crit Buff";
        this.description= "getDescription()";
        this.passive = true;
    }
    @Override
    public Skill getCast() {
        MAR_Crit_Buff cast = new MAR_Crit_Buff(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Hero e) {
        return "Crits will grant ~ a random buff.";
    }

    @Override
    public void critTrigger(Hero target, Skill cast) {
        if (cast.Hero == this.Hero) {
            Logger.logLn(this.Hero.name + ".mar_critbuff.crittrigger");
            Effect effect = Effect.getRdmBuff(1);
            this.Hero.addEffect(effect, this.Hero);
        }
    }
}
