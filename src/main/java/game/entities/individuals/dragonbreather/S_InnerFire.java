package game.entities.individuals.dragonbreather;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;
import utils.MyMaths;

import java.util.List;

public class S_InnerFire extends Skill {

    public S_InnerFire(Hero hero) {
        super(hero);
        this.iconPath = "/icons/innerfire.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.BUFF);
        this.targetType = TargetType.SELF;
        this.manaCost = 15;
        this.cdMax = 6;
        this.ultimate = true;
    }

    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public int getAIRating(Hero target) {
        int rating = 10;
        rating -= this.hero.getMissingLifePercentage() / 20;
        return rating;
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "+5"+Stat.FORCE.getIconString()+", +4"+Stat.ENDURANCE.getIconString()+", +3"+Stat.MAGIC.getIconString();
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.addToStat(Stat.FORCE, 5);
        this.hero.addToStat(Stat.ENDURANCE, 4);
        this.hero.addToStat(Stat.MAGIC, 3);
    }

    @Override
    public String getName() {
        return "Inner Fire";
    }
}
