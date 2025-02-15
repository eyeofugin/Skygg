package game.entities.individuals.sniper;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;

import java.util.List;

public class S_GotYourBack extends Skill {

    public S_GotYourBack(Hero hero) {
        super(hero);
        this.iconPath = "/icons/gotyourback.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE_ALLY;
        this.distance = 1;
        this.cdMax = 1;
        this.effects = List.of(new Combo());
    }

    @Override
    public int getAIRating(Hero target) {
        int rating = 2;
        for (Skill skill : target.getSkills()) {
            if (skill.isComboEnabled()) {
                rating += 2;
            }
        }
        return rating;
    }



    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.addToStat(Stat.SPEED, 3);
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gives Combo, +3 Speed";
    }


    @Override
    public String getName() {
        return "Got Your Back";
    }
}
