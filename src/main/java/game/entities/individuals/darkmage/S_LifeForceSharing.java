package game.entities.individuals.darkmage;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class S_LifeForceSharing extends Skill {

    public S_LifeForceSharing(Hero hero) {
        super(hero);
        this.iconPath = "/icons/lifeforcesharing.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.HEAL);
        this.healMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.5));
        this.targetType = TargetType.SINGLE_ALLY;
        this.distance = 2;
        this.lifeCost = 4;
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public int getAIRating(Hero target) {
        int rating = 0;
        rating -= this.hero.getMissingLifePercentage() / 50;
        rating += target.getMissingLifePercentage() / 15;
        return rating;
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "heal ally for 50% Magic";
    }

    @Override
    public String getName() {
        return "Life Force Sharing";
    }
}
