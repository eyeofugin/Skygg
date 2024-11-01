package game.entities.individuals.divinemage;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.TargetType;

public class S_HealingGleam extends Skill {

    public S_HealingGleam(Hero hero) {
        super(hero);
        this.iconPath = "/res/icons/healinggleam.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.LINE;
        this.distance = 4;
        this.cdMax = 4;
        this.faithCost = 15;
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/res/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.removeNegativeEffects();
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Heal all status effects and debuffs;";
    }
    @Override
    public String getName() {
        return "Healing Gleam";
    }
}
