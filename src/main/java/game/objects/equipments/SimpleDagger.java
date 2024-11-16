package game.objects.equipments;

import game.objects.Equipment;

public class SimpleDagger extends Equipment {

    public SimpleDagger() {
        super("simpledagger",
                "A Simple dagger. Some other words to test out how much space i have to write a description",
                "Simple Dagger");
        this.statBonus = loadStatBonus();
    }


}
