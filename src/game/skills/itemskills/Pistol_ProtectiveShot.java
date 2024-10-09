package game.skills.itemskills;

import framework.Logger;
import game.entities.Entity;
import game.entities.Multiplier;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.backgroundskills.GUA_Adaptability;
import game.skills.changeeffects.effects.Shielded;

import java.util.List;

public class Pistol_ProtectiveShot extends Skill {

    private int INTENSITY = 50;

    public Pistol_ProtectiveShot(Entity entity) {
        super(entity);
        this.name="pistol_protectiveshot";
        this.translation="Protective Shot";
        this.description= "";
        this.animationR="runR";
        this.animationL="runL";
        this.dmgMultipliers = List.of(new Multiplier(Stat.PRECISION,0.4),
                new Multiplier(Stat.REFLEXES,0.3));
        this.casterEffects = List.of(new Shielded());
        this.targetType = TargetType.SINGLE;
        this.tags = List.of(AiSkillTag.BUFF, AiSkillTag.DMG);
        this.actionCost = 1;
        this.distance = 3;
        this.dmg = entity.getPrimary().getAutoAttackPower();
        this.weaponSkill = true;
    }
    @Override
    public Skill getCast() {
        Pistol_ProtectiveShot cast = new Pistol_ProtectiveShot(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public int getCastingStat(Stat stat, Skill cast) {
        if (stat.equals(Stat.CRIT_CHANCE) && cast.getClass().equals(Pistol_ProtectiveShot.class)) {
            Logger.logLn(this.entity.name + ".Pistol_ProShot.getCastingStat");
            return INTENSITY;
        }
        return 0;
    }
}
