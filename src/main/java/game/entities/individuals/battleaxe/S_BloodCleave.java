package game.entities.individuals.battleaxe;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.statusinflictions.Bleeding;
import utils.MyMaths;

import java.util.ArrayList;
import java.util.List;

public class S_BloodCleave extends Skill {

    public S_BloodCleave(Hero hero) {
        super(hero);
        this.iconPath = "entities/battleaxe/icons/awesomeaxe.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.2));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{3};
        this.possibleTargetPositions = new int[]{4};
        this.dmg = 10;
        this.damageMode = DamageMode.PHYSICAL;
        this.effects = List.of(new Bleeding(1));
    }

    @Override
    public int getAIRating(Hero target) {
        return 1;
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return " ";
    }

    @Override
    public String getName() {
        return "Blood Cleave";
    }
}
