package game.entities.individuals.darkmage;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.LifeSteal;

import java.util.List;
import java.util.Map;

public class S_GiftOfTheLeech extends Skill {

    public S_GiftOfTheLeech(Hero hero) {
        super(hero);
        this.iconPath = "/icons/giftoftheleech.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.BUFF);
        this.targetType = TargetType.SINGLE_ALLY;
        this.distance = 2;
        this.primary = true;
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});

    }

    @Override
    public int getAIRating(Hero target) {
        return 2;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.addEffect(new LifeSteal(1), this.hero);
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Target gets lifesteal for 1 turn";
    }

    @Override
    public String getName() {
        return "Gift of the leech";
    }
}
