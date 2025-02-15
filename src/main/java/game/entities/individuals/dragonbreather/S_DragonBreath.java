package game.entities.individuals.dragonbreather;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;
import utils.MyMaths;

import java.nio.file.LinkPermission;
import java.util.List;

public class S_DragonBreath extends Skill {

    public S_DragonBreath(Hero hero) {
        super(hero);
        this.iconPath = "/icons/dragonbreath.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.5));
        this.effects = List.of(new Burning(4));
        this.targetType = TargetType.LINE;
        this.distance = 2;
        this.dmg = 8;
        this.damageType = DamageType.HEAT;
        this.damageMode = DamageMode.MAGICAL;
        this.manaCost = 6;
    }



    @Override
    public String getDescriptionFor(Hero hero) {
        return "Burns twice";
    }

    @Override
    public String getName() {
        return "Dragon Breath";
    }
}
