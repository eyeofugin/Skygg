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
        this.tags = List.of(SkillTag.DMG);
        this.targetType = TargetType.SINGLE;
        this.damageMode = DamageMode.MAGICAL;
        this.distance = 3;
        this.primary = true;
        this.abilityType = AbilityType.PRIMARY;
    }


    @Override
    public int getDmg(Hero target) {
        return target.getStat(Stat.ENDURANCE) / 2;
    }

    @Override
    public String getDmgOrHealString() {
        return "True damage equal to 50% of targets endurance";
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
