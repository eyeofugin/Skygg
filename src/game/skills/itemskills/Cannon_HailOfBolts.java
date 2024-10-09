package game.skills.itemskills;

import game.entities.Entity;
import game.entities.Multiplier;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.backgroundskills.GUA_Adaptability;

import java.util.List;

public class Cannon_HailOfBolts extends Skill {
    public Cannon_HailOfBolts(Entity entity) {
        super(entity);
        this.name="cannon_hailofbolts";
        this.translation="Hail of Bolts";
        this.description= "";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.SINGLE;
        this.dmgMultipliers = List.of(new Multiplier(Stat.PRECISION,0.2),
                new Multiplier(Stat.STRENGTH,0.1));
        this.actionCost = 1;
        this.distance = 3;
        this.countAsHits = 2;
        this.dmg = entity.getPrimary().getAutoAttackPower();
        this.tags = List.of(AiSkillTag.DMG);
        this.weaponSkill = true;

    }
    @Override
    public Skill getCast() {
        Cannon_HailOfBolts cast = new Cannon_HailOfBolts(this.entity);
        cast.copyFrom(this);
        return cast;
    }
}
