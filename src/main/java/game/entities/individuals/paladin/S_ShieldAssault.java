package game.entities.individuals.paladin;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.RighteousHammerCounter;
import game.skills.changeeffects.statusinflictions.Dazed;

import java.util.List;

public class S_ShieldAssault extends Skill {

    public S_ShieldAssault(Hero hero) {
        super(hero);
        this.iconPath = "entities/paladin/icons/shieldassault.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{1,2};
        this.possibleTargetPositions = new int[]{3};
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
    public String getDescriptionFor(Hero hero) {
        return "Moves. Dazes first opponent.";
    }

    @Override
    public String getName() {
        return "Shield Assault";
    }
}
