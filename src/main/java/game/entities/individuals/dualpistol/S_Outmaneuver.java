package game.entities.individuals.dualpistol;

import game.entities.Hero;
import game.skills.AbilityType;
import game.skills.Skill;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.statusinflictions.Dazed;
import game.skills.changeeffects.statusinflictions.Rooted;

public class S_Outmaneuver extends Skill {

    public S_Outmaneuver(Hero hero) {
        super(hero);
        this.iconPath = "entities/dualpistol/icons/outmaneuver.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{5,6};
        this.cdMax = 5;
        this.comboEnabled = true;
        this.abilityType = AbilityType.TACTICAL;
    }

    @Override
    public void individualResolve(Hero target) {
        this.hero.arena.move(target, 1, target.isTeam2()?-1:1);
        if (this.hero.hasPermanentEffect(Combo.class) > 0) {
            this.hero.removePermanentEffectOfClass(Combo.class);
            target.addEffect(new Dazed(1), this.hero);
        }
    }

    @Override
    public int getAIRating(Hero target) {
        int rating = 0;
        if (this.hero.hasPermanentEffect(Combo.class) > 0) {
            rating++;
        }
        if (target.getPosition() == target.team.getFirstPosition()) {
            return --rating;
        }
        if (target.getCurrentLifePercentage() < 50) {
            rating += 2;
        }
        if (this.hero.arena.getAtPosition(target.team.getFirstPosition()).getCurrentLifePercentage() < 50) {
            rating -= 2;
        }
        return rating;
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "pull 1. if combo: daze target";
    }

    @Override
    public String getName() {
        return "Outmaneuver";
    }
}
