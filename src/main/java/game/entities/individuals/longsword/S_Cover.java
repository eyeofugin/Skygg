package game.entities.individuals.longsword;

import game.entities.Hero;
import game.skills.AbilityType;
import game.skills.Skill;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.effects.Cover;

import java.util.List;

public class S_Cover extends Skill {

    public S_Cover(Hero hero) {
        super(hero);
        this.iconPath = "entities/longsword/icons/cover.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.BUFF);
        this.targetType = TargetType.SINGLE_OTHER;
        this.possibleCastPositions = new int[]{2,3};
        this.possibleTargetPositions = new int[]{1,2};
        this.cdMax = 4;
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.addEffect(new Cover(3), this.hero);
        this.abilityType = AbilityType.TACTICAL;
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gives Cover for 3 turns";
    }


    @Override
    public String getName() {
        return "Cover";
    }
}
