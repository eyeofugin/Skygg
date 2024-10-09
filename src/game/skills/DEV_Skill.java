package game.skills;

import game.entities.Entity;

public class DEV_Skill extends Skill {
    public DEV_Skill(Entity entity) {
        super(entity);
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
        DEV_Skill cast = new DEV_Skill(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public void individualResolve(Entity target) {

    }

    @Override
    public int getAIRating(Entity target, int beatDownMeter) {
        return 100;
    }

}
