package game.skills.backgroundskills;

import framework.Logger;
import game.entities.Entity;
import game.skills.Skill;
import game.skills.TargetType;

public class HUN_Single_out extends Skill {
    private static final int ACCURACY_BONUS_PERCENTAGE = 30;

    public HUN_Single_out(Entity e) {
        super(e);
        this.name="hun_single_out";
        this.translation="Single out";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.passive = true;
        this.powerPercentage = 20;
    }
    @Override
    public Skill getCast() {
        HUN_Single_out cast = new HUN_Single_out(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Entity e) {
        return "Skills with one target have " + ACCURACY_BONUS_PERCENTAGE + "% more accuracy.";
    }

    @Override
    public int getAccuracyFor(Skill s, int baseAcc){
        if (s.entity == this.entity && s.getTargetType().equals(TargetType.SINGLE)) {
            Logger.logLn(this.entity.name + ".HUN_Single_out.getAccuracyFor");
            baseAcc = baseAcc*(100+ACCURACY_BONUS_PERCENTAGE) / 100;
        }
        return baseAcc;
    }
}
