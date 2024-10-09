package game.skills.itemskills;

import game.entities.Entity;
import game.entities.Multiplier;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.backgroundskills.GUA_Adaptability;
import utils.MyMaths;

import java.util.List;

public class Rifle_HipFire extends Skill {
    private static final double DMG = 0.3;

    public Rifle_HipFire(Entity entity) {
        super(entity);
        this.name="rifle_hipfire";
        this.translation="Hip Fire";
        this.description= "";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.SINGLE;
        this.dmgMultipliers = List.of(new Multiplier(Stat.PRECISION,0.1));
        this.accuracy = 80;
        this.actionCost = 1;
        this.distance = 3;
        this.overheatCost = 3;
        this.countAsHits = 3;
        this.dmg = (int)(entity.getPrimary().getAutoAttackPower()*DMG);
        this.tags = List.of(AiSkillTag.DMG);
        this.weaponSkill = true;
    }
    @Override
    public Skill getCast() {
        Rifle_HipFire cast = new Rifle_HipFire(this.entity);
        cast.copyFrom(this);
        return cast;
    }
}
