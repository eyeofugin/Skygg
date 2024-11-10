package game.skills.changeeffects.statusinflictions;

import game.objects.Equipment;
import game.skills.Effect;

public class Disenchanted extends Effect {

    public Disenchanted(int turns) {
        this.turns = turns;
        this.name = "Disenchanted";
        this.stackable = false;
        this.description = "Ignore equipment stats";
        this.type = ChangeEffectType.STATUS_INFLICTION;
    }
    @Override
    public Disenchanted getNew() {
        return new Disenchanted(this.turns);
    }

    @Override
    public void addSubscriptions() {}

    @Override
    public void addToHero() {
        for (Equipment equipment : this.hero.getEquipments()) {
            equipment.deactivateEquipment();
        }
    }

    @Override
    public void removeEffect() {
        for (Equipment equipment : this.hero.getEquipments()) {
            equipment.activateEquipment();
        }
    }
}