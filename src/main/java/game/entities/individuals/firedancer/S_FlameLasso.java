package game.entities.individuals.firedancer;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Combo;

import java.util.List;

public class S_FlameLasso extends Skill {

    public S_FlameLasso(Hero hero) {
        super(hero);
        this.iconPath = "entities/firedancer/icons/flamelasso.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG, SkillTag.PEEL);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.5));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{1,2};
        this.possibleTargetPositions = new int[]{5,6};
        this.dmg = 4;
        this.damageMode = DamageMode.MAGICAL;
        this.comboEnabled = true;
        this.faithCost = 4;
        this.abilityType = AbilityType.TACTICAL;
    }



    @Override
    public int getAIRating(Hero target) {
        int rating = 0;

        if (this.hero.hasPermanentEffect(Combo.class) > 0) {
            rating+=target.getMissingLifePercentage() / 25;
            Hero targetInFront = this.hero.arena.getAtPosition(target.getPosition()+1);
            if (targetInFront != null) {
                rating += targetInFront.getCurrentLifePercentage() < 75 ? 1: 0;
                if (targetInFront.getPosition() == targetInFront.getLastEffectivePosition()) {
                    rating +=5;
                }
            }
        }

        return rating;
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Pull 2";
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.arena.move(target, 2, target.isTeam2()?-1:1);
    }

    @Override
    public String getName() {
        return "Flame Lasso";
    }
}
