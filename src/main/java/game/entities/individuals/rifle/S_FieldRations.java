package game.entities.individuals.rifle;

import game.entities.Hero;
import game.skills.AbilityType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;

import java.util.List;

public class S_FieldRations extends Skill {

    public S_FieldRations(Hero hero) {
        super(hero);
        this.iconPath = "entities/rifle/icons/fieldrations.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.HEAL);
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{0,1,2,3};
        this.cdMax = 3;
        this.comboEnabled = true;
        this.abilityType = AbilityType.TACTICAL;
    }


    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int heal = target.getStat(Stat.LIFE) / 4;
        if (this.hero.hasPermanentEffect(Combo.class) > 0) {
            this.hero.removePermanentEffectOfClass(Combo.class);
            heal*=2;
        }
        target.heal(this.hero, heal, this, false);
    }

    @Override
    public int getAIRating(Hero target) {
        if (target.getResourcePercentage(Stat.CURRENT_LIFE) < 25) {
            return 4;
        }
        if (target.getResourcePercentage(Stat.CURRENT_LIFE) < 50) {
            return 2;
        }
        return 0;
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Heals target for 1/4 max life. Combo: 1/2 max life instead.";
    }


    @Override
    public String getName() {
        return "Field Rations";
    }
}
