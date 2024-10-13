package game.skills.backgroundskills;

import framework.Logger;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;

public class MAR_High_Crit extends Skill {
    private static final int INTENSITY = 15;

    public MAR_High_Crit(Hero e) {
        super(e);
        this.name="mar_high_crit";
        this.translation="High Crit";
        this.description= "getDescription()";
        this.passive = true;
    }
    @Override
    public Skill getCast() {
        MAR_High_Crit cast = new MAR_High_Crit(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Hero e) {
        return "~ has a " + INTENSITY + "% higher chance to crit.";
    }

    @Override
    public int getCastingStat(Stat stat, Skill cast) {
        if (stat.equals(Stat.CRIT_CHANCE)) {
            Logger.logLn(this.Hero.name + ".mar_highcrit.getcastingstat");
            return INTENSITY;
        }
        return 0;
    }
}
