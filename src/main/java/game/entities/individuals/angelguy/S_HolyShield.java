package game.entities.individuals.angelguy;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.effects.Exalted;

import java.util.List;

public class S_HolyShield extends Skill {

    public S_HolyShield(Hero hero) {
        super(hero);
        this.iconPath = "/icons/holyshield.png";
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.BUFF);
        this.targetType = TargetType.ALL_ALLY;
        this.cdMax = 1;
        this.faithCost = 4;
        this.shield = 3;
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.shield(this.shield, this.hero);
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "All allies get 3 shield";
    }


    @Override
    public String getName() {
        return "Holy Shield";
    }
}
