package game.entities.individuals.divinemage;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Immunity;

import java.util.List;

public class S_Immunity extends Skill {

    public S_Immunity(Hero hero) {
        super(hero);
        this.iconPath = "/icons/immunity.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE_ALLY;
        this.cdMax = 3;
        this.faithCost = 5;
        this.effects = List.of(new Immunity(3));
        this.distance = 2;
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public int getAIRating(Hero target) {
        int posBonus = target.team.getLastPosition() - target.getPosition();
        return 3 + posBonus;
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gains immunity for 3 turns";
    }


    @Override
    public String getName() {
        return "Immunity";
    }
}
