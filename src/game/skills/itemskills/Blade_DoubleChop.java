package game.skills.itemskills;

import game.entities.Entity;
import game.entities.Multiplier;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.backgroundskills.GUA_Adaptability;
import game.skills.changeeffects.effects.Disadvantage;

import java.util.List;

public class Blade_DoubleChop extends Skill {

    public Blade_DoubleChop(Entity entity) {
        super(entity);
        this.name="blade_doublechop";
        this.translation="Double Chop";
        this.description= "";
        this.animationR="runR";
        this.animationL="runL";
        this.dmgMultipliers = List.of(new Multiplier(Stat.STRENGTH,0.2),
                new Multiplier(Stat.SPEED,0.2));
        this.targetType = TargetType.SINGLE;
        this.tags = List.of(AiSkillTag.DMG);
        this.cdMax = 4;
        this.actionCost = 1;
        this.distance = 2;
        this.countAsHits = 2;
        this.dmg = entity.getPrimary().getAutoAttackPower();
        this.weaponSkill = true;
    }

    @Override
    public Skill getCast() {
        Blade_DoubleChop cast = new Blade_DoubleChop(this.entity);
        cast.copyFrom(this);
        return cast;
    }
}
