package game.entities.individuals.thehealer;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.TargetType;

public class S_HolyLight extends Skill {

    public S_HolyLight(Hero hero) {
        super(hero);
        this.iconPath = "/icons/holylight.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.ARENA;
        this.cdMax = 4;
        this.manaCost = 6;
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Summons the holy light effect";
    }


    @Override
    public String getName() {
        return "Holy Light";
    }
}
