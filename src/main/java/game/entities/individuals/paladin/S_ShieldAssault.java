package game.entities.individuals.paladin;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.RighteousHammerCounter;
import game.skills.changeeffects.statusinflictions.Dazed;

import java.util.List;

public class S_ShieldAssault extends Skill {

    public S_ShieldAssault(Hero hero) {
        super(hero);
        this.iconPath = "/icons/shieldassault.png";
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.MOVE);
        this.targetType = TargetType.SINGLE_ALLY_IN_FRONT;
        this.distance = 1;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int targetPosition = target.getPosition();
        this.hero.arena.moveTo(this.hero, targetPosition);
        Hero firstEnemy = this.hero.arena.getEntitiesAt(new int[]{3})[0];
        firstEnemy.addEffect(new Dazed(1), this.hero);
    }

    @Override
    public int getAIRating(Hero target) {
        return getRollRating(target);
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Moves 1. Dazes first opponent.";
    }

    @Override
    public String getName() {
        return "Shield Assault";
    }
}
