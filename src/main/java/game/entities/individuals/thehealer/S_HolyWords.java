package game.entities.individuals.thehealer;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class S_HolyWords extends Skill {

    public S_HolyWords(Hero hero) {
        super(hero);
        this.iconPath = "/icons/holywords.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE_ALLY;
        this.distance = 2;
        this.manaCost = 3;
        this.healMultipliers = List.of(new Multiplier(Stat.LIFE, 0.2),
                new Multiplier(Stat.MANA, 0.3));
    }
    @Override
    public int getAIRating(Hero target) {
        return target.getMissingLifePercentage() / 25;
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Strong heal.";
    }

    @Override
    public String getName() {
        return "Holy Words";
    }
}
