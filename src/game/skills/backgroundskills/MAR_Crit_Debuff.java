package game.skills.backgroundskills;

import framework.Logger;
import game.entities.Hero;
import game.skills.Effect;
import game.skills.Skill;

public class MAR_Crit_Debuff extends Skill {

    public MAR_Crit_Debuff(Hero e) {
        super(e);
        this.name="mar_crit_debuff";
        this.translation="Crit Debuff";
        this.description= "getDescription()";
        this.passive = true;
    }
    @Override
    public Skill getCast() {
        MAR_Crit_Debuff cast = new MAR_Crit_Debuff(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Hero e) {
        return "Crits will grant the target a random debuff.";
    }

    @Override
    public void critTrigger(Hero target, Skill cast) {
        if (cast.Hero == this.Hero) {
            Logger.logLn(this.Hero.name + ".mar_critdebuff.crittrigger");
            Effect debuff = Effect.getRdmDebuff(1);
            target.addEffect(debuff, this.Hero);
        }
    }
}
