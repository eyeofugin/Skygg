package game.entities.individuals.battleaxe;

import game.entities.Hero;
import game.skills.*;
import game.skills.changeeffects.statusinflictions.Bleeding;

import java.util.List;

public class S_WideSwing extends Skill {

    public S_WideSwing(Hero hero) {
        super(hero);
        this.iconPath = "entities/battleaxe/icons/wideswing.png";
        addSubscriptions();
        setToInitial();

    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.targetType = TargetType.ALL_TARGETS;
        this.possibleCastPositions = new int[]{2,3};
        this.possibleTargetPositions = new int[]{4,5};
        this.damageMode = DamageMode.PHYSICAL;
        this.ultimate = true;
        this.cdMax = 2;
        this.abilityType = AbilityType.ULT;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.addEffect(new Bleeding(1), this.hero);
    }
    @Override
    public int getDmg(Hero target) {
        return this.hero.getMissingLifePercentage() / 2;
    }

    @Override
    public boolean performCheck(Hero hero) {
        return hero.getCurrentLifePercentage() < 40;
    }

    @Override
    public int getAIRating(Hero target) {
        if (this.hero.getCurrentLifePercentage() > 40) {
            return -10;
        }
        return 1;
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "scales with missing health. Can only activate under 40% Max life.";
    }

    @Override
    public String getName() {
        return "Wide Swing";
    }
}
