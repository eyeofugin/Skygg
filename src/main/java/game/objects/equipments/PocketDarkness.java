package game.objects.equipments;

import game.objects.Equipment;
import game.objects.equipments.skills.S_PocketDarkness;

public class PocketDarkness extends Equipment {
    public PocketDarkness() {
        super("pocketdarkness", "Pocket Darkness");
        this.tempStatBonus = this.loadTempStatBonus();
        this.skill = new S_PocketDarkness(this);
    }
}
