package game.skills.genericskills;

import framework.resources.SpriteLibrary;
import game.entities.Hero;
import game.skills.Skill;

public class S_Skip extends Skill {
    public S_Skip(Hero hero) {
        super(hero);
        this.iconPath = "/icons/skip.png";
        this.iconPixels = SpriteLibrary.getSprite(this.getClass().getName());
    }

    @Override
    protected void initAnimation() {
    }

    @Override
    public void resolve() {}
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Skips the turn";
    }

    @Override
    public String getName() {
        return "Skip";
    }
}
