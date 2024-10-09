package game.skills.backgroundskills;

import framework.Logger;
import game.entities.Entity;
import game.skills.Effect;
import game.skills.Skill;
import utils.MyMaths;

public class GUA_Steadfast extends Skill {

    public GUA_Steadfast(Entity e) {
        super(e);
        this.name="gua_steadfast";
        this.translation="Steadfast";
        this.description= "getDescription()";
        this.passive = true;
    }
    @Override
    public Skill getCast() {
        GUA_Steadfast cast = new GUA_Steadfast(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Entity e) {
        return "20% chance to ignore status inflictions.";
    }

    @Override
    public boolean effectFailure(Effect e, Entity target) {
        if (e.type.equals(Effect.ChangeEffectType.STATUS_INFLICTION) && target.equals(this.entity)) {
            Logger.logLn("Steadfast check.");
            return MyMaths.success(20);
        }
        return false;
    }
}
