package game.objects.equipments;

import game.objects.Equipment;
import game.objects.equipments.skills.S_WingedBoots;

public class WingedBoots extends Equipment {

    public WingedBoots() {
        super("wingedboots", "Winged Boots");
        this.statBonus = this.loadBaseStatBonus();
        this.skill = new S_WingedBoots(this);
    }
}
