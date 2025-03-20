package game.objects.equipments;

import game.objects.Equipment;
import game.skills.Stat;

public class SceptedOfTheGods extends Equipment {

    public SceptedOfTheGods() {
        super("scepterofthegods", "Scepter of the Gods");
        this.statBonus = this.loadBaseStatBonus();
    }

    @Override
    public void turn() {
        if (this.active && this.hero.getSecondaryResource().equals(Stat.FAITH)) {
            this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, 3, this.hero);
        }
    }
}
