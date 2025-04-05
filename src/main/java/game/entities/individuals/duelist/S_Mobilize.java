package game.entities.individuals.duelist;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;

import java.util.List;

public class S_Mobilize extends Skill {

    public S_Mobilize(Hero hero) {
        super(hero);
        this.iconPath = "entities/duelist/icons/mobilize.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE_OTHER;
        this.possibleCastPositions = new int[]{0,1,2,3};
        this.possibleTargetPositions = new int[]{0,1,2,3};
        this.cdMax = 3;
    }

    @Override
    public int getAIRating(Hero target) {
        return getRollRating(target);
    }


    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int targetPosition = target.getPosition();
        this.hero.arena.moveTo(this.hero, targetPosition);
        if (this.hero.hasPermanentEffect(Combo.class) > 0) {
            this.hero.addToStat(Stat.POWER, this.hero.getStat(Stat.POWER)/10);
        }
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Move 1. Combo: +10% Power";
    }

    @Override
    public String getName() {
        return "Mobilize";
    }
}
