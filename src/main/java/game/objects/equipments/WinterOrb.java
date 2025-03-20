package game.objects.equipments;

import game.objects.Equipment;
import game.objects.equipments.skills.S_WinterOrb;

public class WinterOrb extends Equipment {
    public WinterOrb() {
        super("winterorb", "Winter Orb");
        this.statBonus = this.loadBaseStatBonus();
        this.skill = new S_WinterOrb(this);
    }
}
