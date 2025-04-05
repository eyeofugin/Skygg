package game.entities.individuals.divinemage;

import game.entities.Hero;
import game.skills.*;
import game.skills.changeeffects.effects.Invincible;

import java.util.List;

public class S_Cleanse extends Skill {

    public S_Cleanse(Hero hero) {
        super(hero);
        this.iconPath = "entities/divinemage/icons/cleanse.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE;
        this.faithCost = 5;
        this.possibleCastPositions = new int[]{0,1,2,3};
        this.possibleTargetPositions = new int[]{0,1,2,3};
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
