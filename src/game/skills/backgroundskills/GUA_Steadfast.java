package game.skills.backgroundskills;

import framework.Logger;
import game.entities.Hero;
import game.skills.Effect;
import game.skills.Skill;
import utils.MyMaths;

public class GUA_Steadfast extends Skill {

    public GUA_Steadfast(Hero e) {
        super(e);
        this.name="gua_steadfast";
        this.translation="Steadfast";
        this.description= "getDescription()";
        this.passive = true;
    }
    @Override
    public Skill getCast() {
        GUA_Steadfast cast = new GUA_Steadfast(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Hero e) {
        return "20% chance to ignore status inflictions.";
    }

    @Override
    public boolean effectFailure(Effect e, Hero target) {
        if (e.type.equals(Effect.ChangeEffectType.STATUS_INFLICTION) && target.equals(this.Hero)) {
            Logger.logLn("Steadfast check.");
            return MyMaths.success(20);
        }
        return false;
    }
}
