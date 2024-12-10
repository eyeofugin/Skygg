package game.entities.individuals.longsword;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.TargetType;
import game.skills.changeeffects.statusinflictions.Taunted;

import java.util.List;

public class S_Taunt extends Skill {

    public S_Taunt(Hero hero) {
        super(hero);
        this.iconPath = "/icons/taunt.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE;
        this.distance = 2;
        this.cdMax = 5;
        this.effects = List.of(new Taunted(2));
    }

    @Override
    public int getAIRating(Hero target) {
        return 5;
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Taunts target";
    }

    @Override
    public String getName() {
        return "Taunt";
    }
}
