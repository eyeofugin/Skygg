package game.entities.individuals.sniper;

import game.entities.Hero;
import game.skills.AbilityType;
import game.skills.Skill;
import game.skills.TargetType;
import game.skills.changeeffects.effects.RegenBoost;
import game.skills.changeeffects.effects.RegenStop;
import game.skills.changeeffects.statusinflictions.Injured;

public class S_ChemShot extends Skill {

    public S_ChemShot(Hero hero) {
        super(hero);
        this.iconPath = "entities/sniper/icons/chemshot.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE;
        this.distance = 4;
        this.primary = true;
        this.allowAllyForSingle = true;
        this.abilityType = AbilityType.PRIMARY;
    }



    @Override
    public int getAIRating(Hero target) {
        return target.getMissingLifePercentage() / 25;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        if (target.isTeam2() == this.hero.isTeam2()) {
            target.addEffect(new RegenBoost(1), this.hero);
        } else {
            target.addEffect(new Injured(1), this.hero);
        }
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "ally: regen-boost for 1 turn. enemy: injure 1 turn";
    }

    @Override
    public String getName() {
        return "Chemical arrow";
    }
}
