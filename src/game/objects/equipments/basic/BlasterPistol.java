package game.objects.equipments.basic;

import framework.Logger;
import game.objects.Equipment;
import game.skills.Stat;

public class BlasterPistol extends Equipment {
    public int overheatCdCurrent = 0;
    public int overHeatCdMax = 2;

    public BlasterPistol() {
        this.autoAttackPower = 3;
        this.autoAttackDistance = 3;
        this.autoAttackOverheatCost = 1;
        this.autoAttackActionCost = 1;
        this.autoAttackMultiplier = Stat.PRECISION;
        this.damageType = Stat.RANGED;
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
        Logger.logLn(this.getClass().getName() + ".startofturn");
        if (inOverheat) {
            this.overheatCdCurrent--;
            if (this.overheatCdCurrent == 0) {
                inOverheat = false;
            }
        } else if (this.currentOverheat>0){
            this.currentOverheat--;
        }
    }
    @Override
    protected void overHeat() {
        this.inOverheat = true;
        this.overheatCdCurrent = this.overHeatCdMax;
    }

}
