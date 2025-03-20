package framework.connector.payloads;

import framework.connector.ConnectionPayload;
import game.entities.Hero;
import game.objects.Equipment;

public class EquipmentChangePayload extends ConnectionPayload {
    public Hero target;
    public Equipment equipment;
    public EquipmentChangeMode mode;

    public EquipmentChangePayload setTarget(Hero target) {
        this.target = target;
        return this;
    }

    public EquipmentChangePayload setEquipment(Equipment equipment) {
        this.equipment = equipment;
        return this;
    }
    public EquipmentChangePayload setMode(EquipmentChangeMode mode) {
        this.mode = mode;
        return this;
    }

    public enum EquipmentChangeMode {
        EQUIP,
        UNEQUIP,
        ACTIVATE,
        DEACTIVATE;
    }
}
