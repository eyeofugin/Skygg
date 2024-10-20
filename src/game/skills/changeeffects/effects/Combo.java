package game.skills.changeeffects.effects;

import game.skills.Effect;

public class Combo extends Effect {

    public Combo() {
        this.name = "Combo";
        this.stackable = false;
        this.description = "Combo.";
        this.type = ChangeEffectType.EFFECT;
    }
    @Override
    public Effect getNew() {
        return new Combo();
    }

    @Override
    public void addSubscriptions() {}
}
