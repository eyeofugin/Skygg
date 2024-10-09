package game.skills.itemskills;

import game.entities.Entity;
import game.entities.Multiplier;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.backgroundskills.GUA_Adaptability;
import game.skills.changeeffects.statusinflictions.Paralysed;
import utils.MyMaths;

import java.util.List;

public class Rifle_ShockAmmo extends Skill {
    private static final int DMG = 5;
    public Rifle_ShockAmmo(Entity entity) {
        super(entity);
        this.name="rifle_shockammo";
        this.translation="Shock Ammo";
        this.description= "Deals damage with chance to paralyse";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.SINGLE;
        this.dmgMultipliers = List.of(new Multiplier(Stat.PRECISION,0.3));
        this.effects = List.of(new Paralysed(2,10));
        this.actionCost = 1;
        this.distance = 4;
        this.overheatCost = 1;
        this.dmg = DMG;
        this.tags = List.of(AiSkillTag.DMG, AiSkillTag.CC);
        this.weaponSkill = true;
    }
    @Override
    public Skill getCast() {
        Rifle_ShockAmmo cast = new Rifle_ShockAmmo(this.entity);
        cast.copyFrom(this);
        return cast;
    }
}
