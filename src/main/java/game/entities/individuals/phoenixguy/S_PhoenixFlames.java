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
        this.iconPath = "/icons/phoenixflames.png";
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.BUFF);
        this.targetType = TargetType.SELF;
        this.effects = List.of(new Exalted(3));
        this.faithCost = 8;
    }

    @Override
    public int getAIRating(Hero target) {
        return 10;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        for (Hero hero : this.hero.getEnemies()) {
            hero.addEffect(new Burning(3), this.hero);
        }
    }

    @Override
    public String getName() {
        return "Phoenix Flames";
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Get Exalted(3). All enemies get 3 Burning stacks.";
    }

}
