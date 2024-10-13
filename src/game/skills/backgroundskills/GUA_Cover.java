package game.skills.backgroundskills;

import framework.Loader;
import framework.Logger;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.changeeffects.effects.Cover;

public class GUA_Cover extends Skill {

    public GUA_Cover(Hero e) {
        super(e);
        this.name="gua_cover";
        this.translation="Cover";
        this.description= "getDescription()";
        this.passive = true;
    }
    @Override
    public Skill getCast() {
        GUA_Cover cast = new GUA_Cover(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Hero e) {
        return "The ally behind this one has cover.";
    }
    @Override
    public void update() {
        int HeroPositionLookup = Hero.position + (Hero.enemy?1:-1);
        if (HeroPositionLookup < Hero.arena.getAllLivingEntities().length && HeroPositionLookup >= 0) {
            Hero ally = Hero.arena.getAtPosition(HeroPositionLookup);
            if (ally != null) {
                Logger.logLn(this.Hero.name + ".GUA_Cover.update: Add cover to " + ally.name);
                ally.addEffect(new Cover(), null);
            }
        }
    }
}
