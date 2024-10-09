package game.skills.itemskills;

import game.entities.Entity;
import game.entities.Multiplier;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.backgroundskills.GUA_Adaptability;
import game.skills.changeeffects.statusinflictions.Injured;
import game.skills.changeeffects.statusinflictions.Weakened;

import java.util.List;

public class Pistol_AnkleShot extends Skill {
    public Pistol_AnkleShot(Entity entity) {
        super(entity);
        this.name="pistol_ankleshot";
        this.translation="Ankle Shot";
        this.description= "";
        this.animationR="runR";
        this.animationL="runL";
        this.dmgMultipliers = List.of(new Multiplier(Stat.PRECISION,0.6));
        this.effects = List.of(new Injured(2,20));
        this.targetType = TargetType.SINGLE;
        this.tags = List.of(AiSkillTag.CC, AiSkillTag.DMG);
        this.actionCost = 1;
        this.distance = 3;
        this.dmg = entity.getPrimary().getAutoAttackPower();
        this.weaponSkill = true;
    }
    @Override
    public Skill getCast() {
        Pistol_AnkleShot cast = new Pistol_AnkleShot(this.entity);
        cast.copyFrom(this);
        return cast;
    }
}
