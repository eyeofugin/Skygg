package game.skills.itemskills;

import framework.Logger;
import game.entities.Entity;
import game.entities.Multiplier;
import game.objects.Equipment;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.backgroundskills.GUA_Adaptability;

import java.util.List;

//TODO meh probably more an equipment skill than class
public class Pistol_QuickShot extends Skill {
    private static final int ACTION_COST = 1;
    private static final int CD_TOTAL = 1;

    public Pistol_QuickShot(Entity e) {
        super(e);
        this.name="pistol_quick_shot";
        this.translation="Quick Shot";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.SINGLE;
        this.autoAttack = true;
        this.dmgMultipliers = of(new Multiplier[] {
                new Multiplier(Stat.SPEED,0.8)});
        this.actionCost = ACTION_COST;
        this.cdMax = CD_TOTAL;
        this.tags = List.of(AiSkillTag.DMG);
        this.weaponSkill = true;
    }
    @Override
    public Skill getCast() {
        Pistol_QuickShot cast = new Pistol_QuickShot(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Entity e) {
        return "Auto attack that scales with the speed of ~.";
    }
    public void setEquipment(Equipment equipment) {
        Logger.logLn(this.entity.name + ".Pistol_QuickShot.setEquipment:" + equipment);
        if (equipment != null) {
            this.setDamageType(equipment.getDamageType());
            this.setDistance(equipment.getAutoAttackDistance());
            this.setPower(equipment.getAutoAttackPower());
        }
    }
}
