package game.skills.backgroundskills;

import framework.Loader;
import framework.Logger;
import game.entities.Entity;
import game.skills.Skill;
import game.skills.changeeffects.effects.Cover;

public class GUA_Cover extends Skill {

    public GUA_Cover(Entity e) {
        super(e);
        this.name="gua_cover";
        this.translation="Cover";
        this.description= "getDescription()";
        this.passive = true;
    }
    @Override
    public Skill getCast() {
        GUA_Cover cast = new GUA_Cover(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Entity e) {
        return "The ally behind this one has cover.";
    }
    @Override
    public void update() {
        int entityPositionLookup = entity.position + (entity.enemy?1:-1);
        if (entityPositionLookup < entity.arena.getAllLivingEntities().length && entityPositionLookup >= 0) {
            Entity ally = entity.arena.getAtPosition(entityPositionLookup);
            if (ally != null) {
                Logger.logLn(this.entity.name + ".GUA_Cover.update: Add cover to " + ally.name);
                ally.addEffect(new Cover(), null);
            }
        }
    }
}
