package game.entities.individuals.rifle;

import game.entities.Hero;
import game.skills.*;
import game.skills.changeeffects.effects.Combo;

import java.util.List;

public class S_Mobilize extends Skill {

    public S_Mobilize(Hero hero) {
        super(hero);
        this.iconPath = "entities/rifle/icons/mobilize.png";
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
        this.cdMax = 2;
        this.aiTags = List.of(AiSkillTag.COMBO_ENABLED);
    }


    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int currentPosition = this.hero.getPosition();
        int targetPosition = target.getPosition();
        this.hero.arena.moveTo(this.hero, targetPosition);

        boolean forward = this.hero.isTeam2() && currentPosition > targetPosition
                || !this.hero.isTeam2() && currentPosition < targetPosition;
        if (forward) {
            this.hero.addToStat(Stat.SPEED, 1);
        } else {
            this.hero.addToStat(Stat.EVASION, 5);
        }

        if (this.hero.hasPermanentEffect(Combo.class)>0) {
            this.hero.removePermanentEffectOfClass(Combo.class);
            this.cdCurrent--;
        }
    }

    @Override
    public int getAIRating(Hero target) {
        return getRollRating(target);
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Move 1. If back: +5 Evasion. If forward: +1 Speed. Combo: -1 Cooldown";
    }


    @Override
    public String getName() {
        return "Mobilize";
    }
}
