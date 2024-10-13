package game.skills.backgroundskills;

import framework.Logger;
import game.entities.Hero;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.Stat;

public class SEN_Sith_Rage extends Skill {
    public SEN_Sith_Rage(Hero e) {
        super(e);
        this.name="sen_sith_rage";
        this.translation="Sith Rage";
        this.description= "getDescription()";
        this.passive = true;
    }
    @Override
    public Skill getCast() {
        SEN_Sith_Rage cast = new SEN_Sith_Rage(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Hero e) {
        return "Gain an rage stack whenever receiving damage.";
    }


    @Override
    public void dmgTrigger(Hero target, Skill cast, int doneDamage) {
        if (this.Hero == target) {
            Logger.logLn(this.Hero.name + ".sen_sithrage.dmgTrigger");
            this.Hero.addEffect(Effect.newStatChange(1, Stat.STRENGTH), null);
            this.Hero.addEffect(Effect.newStatChange(1, Stat.WILLPOWER), null);
            this.Hero.addEffect(Effect.newStatChange(-1, Stat.ACCURACY), null);
        }
    }

}
