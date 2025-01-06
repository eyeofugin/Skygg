package game.skills.changeeffects.effects;

import game.skills.Effect;

public class Threatening extends Effect {
    public Threatening(int turns) {
        this.turns = turns;
        this.name = "Threatening";
        this.stackable = false;
        this.description = "Must be the target of single target skills.";
        this.type = ChangeEffectType.BUFF;
    }
    @Override
    public Effect getNew() {
        return new Threatening(this.turns);
    }

    @Override
    public void addSubscriptions() {
    }
}
