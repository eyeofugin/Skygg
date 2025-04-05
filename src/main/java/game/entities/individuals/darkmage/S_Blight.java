package game.entities.individuals.darkmage;

import game.entities.Hero;
import game.skills.*;
import game.skills.changeeffects.effects.Blight;

import java.util.List;

public class S_Blight extends Skill {

    public S_Blight(Hero hero) {
        super(hero);
        this.iconPath = "entities/darkmage/icons/blight.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.targetType = TargetType.SINGLE;
        this.damageMode = DamageMode.MAGICAL;
        this.possibleCastPositions = new int[]{0,1,2,3};
        this.possibleTargetPositions = new int[]{4,5,6};
    }


    @Override
    public int getDmg(Hero target) {
        return target.getStat(Stat.ENDURANCE) / 2;
    }

    @Override
    public String getDmgOrHealString() {
        return "DMG: 50% of target's " + Stat.ENDURANCE.getIconString() + ".";
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return " ";
    }

    @Override
    public String getName() {
        return "Dark Blast";
    }
}
