package game.skills.genericskills;

import game.entities.Hero;
import game.skills.Skill;

public class S_Skip extends Skill {
    public S_Skip(Hero hero) {
        super(hero);
        this.iconPath = "/icons/skip.png";
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
