package game.objects;

import game.entities.Hero;
import game.skills.Stat;
import java.util.HashMap;
import java.util.Map;

public class Equipment {

    protected Map<Stat, Integer> statBonus = new HashMap<>();
    private Hero hero;
    private boolean active;

    public void equipToHero(Hero hero) {
        this.hero = hero;
        this.hero.getEquipments().add(this);

        statChange(1);
    }

    public void unEquipFromHero() {
        this.hero.getEquipments().remove(this);
        statChange(-1);
        this.hero = null;
    }

    private void statChange(int sign) {
        for (Map.Entry<Stat, Integer> statBonus : this.statBonus.entrySet()) {
            this.hero.addToStat(statBonus.getKey(), statBonus.getValue() * sign);
        }
    }

    public void deactivateEquipment() {
        this.active = false;
        statChange(-1);
    }

    public void activateEquipment() {
        if (active) {
            return;
        }
        statChange(1);
        this.active = true;
    }


}
