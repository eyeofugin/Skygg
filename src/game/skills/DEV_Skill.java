package game.skills;

import game.entities.Hero;

public class DEV_Skill extends Skill {
    public DEV_Skill(Hero Hero) {
        super(Hero);
        this.name="gua_cover";
        this.translation="Cover";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.SELF;
        this.actionCost = 1;
        this.cdMax = 0;
    }
    @Override
    public Skill getCast() {
        DEV_Skill cast = new DEV_Skill(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public void individualResolve(Hero target) {

    }

    @Override
    public int getAIRating(Hero target, int beatDownMeter) {
        return 100;
    }

}
