package game.skills.itemskills;

import game.entities.Entity;
import game.entities.Multiplier;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.backgroundskills.GUA_Adaptability;
import utils.MyMaths;

import java.util.List;

public class Rifle_Crossfire extends Skill {
    private static final int DMG = 10;
    private static final int DMG_BONUS = 5;
    public Rifle_Crossfire(Entity entity) {
        super(entity);
        this.name="rifle_crossfire";
        this.translation="Crossfire";
        this.description= "Deals damage to the first two enemies";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.FIRST_TWO_ENEMIES;
        this.dmgMultipliers = List.of(new Multiplier(Stat.PRECISION,0.5));
        this.actionCost = 1;
        this.distance = 6;
        this.overheatCost = 2;
        this.dmg = DMG;
        this.tags = List.of(AiSkillTag.DMG);
        this.weaponSkill = true;
    }
    @Override
    public Skill getCast() {
        Rifle_Crossfire cast = new Rifle_Crossfire(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    protected void individualResolve(Entity target) {
        int critChance = this.entity.getCastingStat(this, Stat.CRIT_CHANCE);
        if (!this.entity.hasStatBuff()) {
            this.dmg += DMG_BONUS;
        }
        if (MyMaths.success(critChance)) {
            this.dmg*=2;
            this.entity.arena.critTrigger(target, this);
        }
        int dmg = this.getDamage();
        Stat dt = this.getDamageType();
        int lethality = this.entity.getCastingStat(this, Stat.LETHALITY);
        if (dmg>0) {
            int doneDamage = target.damage(this.entity, dmg, dt, lethality, this);
            this.entity.arena.dmgTrigger(target,this, doneDamage);
        }
        this.applySkillEffects(target);
    }
}
