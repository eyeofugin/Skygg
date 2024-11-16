package game.objects.equipments;

import game.objects.Equipment;

public class SimpleBow extends Equipment {

    public SimpleBow() {
        super("simplebow", "A simple bow", "Simple Bow");
        this.statBonus = this.loadStatBonus();

    }
}
