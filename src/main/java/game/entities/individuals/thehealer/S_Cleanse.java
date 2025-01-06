package game.entities.individuals.thehealer;

import game.entities.Hero;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.TargetType;

public class S_Cleanse extends Skill {

    public S_Cleanse(Hero hero) {
        super(hero);
        this.iconPath = "/icons/cleanse.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE_ALLY;
        this.manaCost = 4;
        this.distance = 2;
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
    public int getAIRating(Hero target) {
        return target.getEffects().stream().filter(e->e.type.equals(Effect.ChangeEffectType.DEBUFF)).toList().size();
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Cleanse target of all debuffs";
    }


    @Override
    public String getName() {
        return "Cleanse";
    }
}
