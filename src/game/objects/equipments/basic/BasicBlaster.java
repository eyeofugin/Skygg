package game.objects.equipments.basic;

import game.objects.Equipment;
import game.skills.Stat;

public class BasicBlaster extends Equipment {
    public BasicBlaster() {
        super();
        this.autoAttackPower = 3;
        this.autoAttackDistance = 4;
        this.damageType = Stat.RANGED;
        this.autoAttackActionCost = 1;
    }
}
