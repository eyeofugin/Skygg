package game.entities.individuals.darkmage;

import game.entities.Hero;
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
        this.targetType = TargetType.SINGLE_ALLY;
        this.distance = 3;
        this.cdMax = 1;
        this.lifeCost = 4;
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});

    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "heal ally for 1/4 max life";
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.heal(this.hero, target.getStat(Stat.LIFE)/4, this, false);
    }
    @Override
    public String getName() {
        return "Life Force Sharing";
    }
}
