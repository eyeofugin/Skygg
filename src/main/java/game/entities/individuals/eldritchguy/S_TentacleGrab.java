package game.entities.individuals.eldritchguy;

import game.entities.Hero;
import game.skills.*;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.statusinflictions.Dazed;

import java.util.List;

public class S_TentacleGrab extends Skill {

    public S_TentacleGrab(Hero hero) {
        super(hero);
        this.iconPath = "entities/eldritchguy/icons/tentaclegrab.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{3};
        this.possibleTargetPositions = new int[]{5,6};
        this.damageMode = DamageMode.PHYSICAL;
        this.manaCost = 6;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.arena.move(target, 1, target.isTeam2()?-1:1);
    }

    @Override
    public int getDmg(Hero target) {
        return target.getStat(Stat.LIFE) * 10 / 100;
    }
    @Override
    public String getDmgOrHealString() {
        return "10% Target Max Life";
    }
    @Override
    public int getAIRating(Hero target) {
        int rating = 0;
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
        return "Pull 1.";
    }

    @Override
    public String getName() {
        return "Tentacle Grab";
    }
}
