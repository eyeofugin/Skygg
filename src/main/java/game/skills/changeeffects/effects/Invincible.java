package game.skills.changeeffects.effects;

import game.skills.Effect;

public class Invincible extends Effect {
    public Invincible(int turns) {
        this.turns = turns;
        this.name = "Invincible";
        this.stackable = false;
        this.description = "Cannot die.";
        this.type = ChangeEffectType.BUFF;
    }
    @Override
    public Effect getNew() {
        return new Invincible(this.turns);
    }

    @Override
    public void addSubscriptions() {

    }
}
