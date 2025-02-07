package game.entities.individuals.duelist;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;

import java.util.List;

public class S_AllOut extends Skill {

    public S_AllOut(Hero hero) {
        super(hero);
        this.iconPath = "/icons/allout.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.BUFF);
        this.targetType = TargetType.SELF;
        this.cdMax = 5;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.addToStat(Stat.ENDURANCE, -3);
        this.hero.addToStat(Stat.POWER, 5);
        this.hero.addToStat(Stat.SPEED, 2);
    }

    @Override
    public int getAIRating(Hero target) {
        int rating = 5;
        rating -= this.hero.getMissingLifePercentage() / 20;
        return rating;
    }


    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "5"+Stat.POWER.getIconString()+", 2"+Stat.SPEED.getIconString()+", -3"+Stat.ENDURANCE.getIconString();
    }

    @Override
    public String getName() {
        return "All Out";
    }
}
