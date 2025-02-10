package game.entities.individuals.divinemage;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class S_HealingGleam extends Skill {

    public S_HealingGleam(Hero hero) {
        super(hero);
        this.iconPath = "/icons/healinggleam.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.LINE;
        this.heal = 10;
        this.healMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.3));
        this.distance = 2;
        this.faithCost = 12;
        this.ultimate = true;
    }

    @Override
    public int getAIRating(Hero target) {
        int rating = 0;
        for (Effect effect : target.getEffects()) {
            if (effect.type != null && effect.type.equals(Effect.ChangeEffectType.DEBUFF) ||
                effect.type.equals(Effect.ChangeEffectType.STATUS_INFLICTION)) {
                rating += 2;
            }
        }
        return rating;
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.removeNegativeEffects();
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Heals and removes all debuffs.";
    }
    @Override
    public String getName() {
        return "Healing Gleam";
    }
}
