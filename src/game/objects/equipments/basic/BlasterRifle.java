package game.objects.equipments.basic;

import game.objects.Equipment;
import game.skills.Stat;

public class BlasterRifle extends Equipment {
    public int overheatCdCurrent = 0;
    public int overHeatCdMax = 2;

    public BlasterRifle() {
        this.autoAttackPower = 6;
        this.autoAttackDistance = 4;
        this.damageType = Stat.RANGED;
        this.autoAttackActionCost = 1;
        this.currentOverheat = 0;
        this.maxOverheat = 4;
        this.inOverheat = false;
        this.type = EquipmentType.WEAPON;
        this.initSkillList();
    }
    private void initSkillList() {
        //this.skillList.add(new ...);
        //...
    }
    @Override
    public void startOfTurn() {
        if (inOverheat) {
            this.overheatCdCurrent--;
            if (this.overheatCdCurrent == 0) {
                inOverheat = false;
            }
        }
    }
    @Override
    protected void overHeat() {
        this.inOverheat = true;
        this.overheatCdCurrent = this.overHeatCdMax;
    }
}
