package game.entities.individuals.phoenixguy;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.effects.Exalted;

import java.util.List;

public class S_PhoenixFlames extends Skill {

    public S_PhoenixFlames(Hero hero) {
        super(hero);
        this.name = "Phoenix Flames";
        this.iconPath = "/res/icons/phoenixflames.png";
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.BUFF);
        this.targetType = TargetType.SELF;
        this.effects = List.of(new Exalted(3));
        this.cdMax = 3;
        this.faithCost = 20;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        for (Hero hero : this.hero.getEnemies()) {
            hero.addEffect(new Burning(-1, 3), this.hero);
        }
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/res/sprites/action_w.png", this.name, new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Enter exalted mode for 3 turns. +1 Range on skills. All enemies get 3 Burning stacks.";
    }

    @Override
    public void addSubscriptions() {

    }
}
