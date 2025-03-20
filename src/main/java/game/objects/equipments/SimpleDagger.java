package game.objects.equipments;

import game.objects.Equipment;

public class SimpleDagger extends Equipment {

    public SimpleDagger() {
        super("simpledagger",
                "Simple Dagger");
        this.statBonus = this.loadBaseStatBonus();
    }


}
