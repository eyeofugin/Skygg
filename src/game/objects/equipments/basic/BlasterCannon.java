package game.objects.equipments.basic;

import game.objects.Equipment;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.itemskills.AutoAttack;
import game.skills.itemskills.Cannon_Reload;

public class BlasterCannon extends Equipment {


    public BlasterCannon() {
        this.autoAttackPower = 2;
        this.autoAttackDistance = 4;
        this.autoAttackOverheatCost = 1;
        this.autoAttackActionCost = 1;
        this.autoAttackMultiplier = Stat.STRENGTH;
        this.autoAttackMultiplierAmount = 0.2;
        this.damageType = Stat.RANGED;
        this.currentOverheat = 0;
        this.maxOverheat = 4;
        this.inOverheat = false;
        this.type = EquipmentType.WEAPON;
        this.aaIcon = "aa_blaster";
        this.initSkillList();
    }
    private void initSkillList() {
        //this.skillList.add(new ...);
        //...
    }
    public void reload() {
        this.currentOverheat = 0;
        this.inOverheat = false;
        this.entity.skills[0] = new AutoAttack(this.entity, this);
    }
    @Override
    protected void overHeat() {
        this.inOverheat = true;
        this.entity.skills[0] = new Cannon_Reload(this.entity);
    }
}
