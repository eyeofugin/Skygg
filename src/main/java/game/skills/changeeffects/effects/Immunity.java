package game.skills.changeeffects.effects;

import game.skills.Effect;

public class Immunity extends Effect {

    public Immunity(int turns) {
        this.turns = turns;
        this.name = "Immunity";
        this.stackable = false;
        this.description = "Cannot get new Effects.";
        this.type = ChangeEffectType.BUFF;
    }
    @Override
    public Effect getNew() {
        return new Immunity(this.turns);
    }

    @Override
    public void addSubscriptions() {

    }
}
