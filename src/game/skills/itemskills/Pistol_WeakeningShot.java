package game.skills.itemskills;

import game.entities.Entity;
import game.entities.Multiplier;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.backgroundskills.GUA_Adaptability;
import game.skills.changeeffects.effects.Shielded;
import game.skills.changeeffects.statusinflictions.Paralysed;
import game.skills.changeeffects.statusinflictions.Weakened;

import java.util.List;

public class Pistol_WeakeningShot extends Skill {
    public Pistol_WeakeningShot(Entity entity) {
        super(entity);
        this.name="pistol_weakeningshot";
        this.translation="Weakening Shot";
        this.description= "";
        this.animationR="runR";
        this.animationL="runL";
        this.dmgMultipliers = List.of(new Multiplier(Stat.PRECISION,0.4),
                new Multiplier(Stat.REFLEXES,0.1));
        this.effects = List.of(new Weakened(2,20));
        this.targetType = TargetType.SINGLE;
        this.tags = List.of(AiSkillTag.CC, AiSkillTag.DMG);
        this.actionCost = 1;
        this.distance = 3;
        this.dmg = entity.getPrimary().getAutoAttackPower();
        this.weaponSkill = true;
    }
    @Override
    public Skill getCast() {
        Pistol_WeakeningShot cast = new Pistol_WeakeningShot(this.entity);
        cast.copyFrom(this);
        return cast;
    }
}
