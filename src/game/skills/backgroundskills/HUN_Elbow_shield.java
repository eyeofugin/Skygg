package game.skills.backgroundskills;

import framework.Logger;
import game.entities.Entity;
import game.skills.Skill;
import game.skills.Stat;

public class HUN_Elbow_shield extends Skill {
    public static final int DEF_BONUS_PERCENTAGE = 10;

    public HUN_Elbow_shield(Entity e) {
        super(e);
        this.name="hun_elbow_shield";
        this.translation="Elbow Shield";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.passive = true;
        this.powerPercentage = DEF_BONUS_PERCENTAGE;
    }
    @Override
    public Skill getCast() {
        HUN_Elbow_shield cast = new HUN_Elbow_shield(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Entity e) {
        return "Receive " + DEF_BONUS_PERCENTAGE + "% less dmg from blade and blunt attacks.";
    }
    @Override
    public int getDamageChanges(Entity caster, Entity target, Skill damagingSkill, int result, Stat damageStat, boolean simulated) {
        if (this.entity == target) {
            if (damageStat.equals(Stat.MELEE)) {
                Logger.logLn(this.entity.name + ".HUN_ElbowShield.damageChange");
                int reduction = result * DEF_BONUS_PERCENTAGE / 100;
                return result - reduction;
            }
        }
        return result;
    }
}
