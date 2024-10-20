package game.skills.changeeffects.statusinflictions;

import game.skills.Effect;

public class Injured extends Effect {
    public Injured(int turns) {
        this.turns = turns;
        this.name = "Injured";
        this.stackable = false;
        this.description = "You dont heal at the end of your turn.";
        this.type = ChangeEffectType.STATUS_INFLICTION;
    }

    @Override
    public Injured getNew() {
        return new Injured(this.turns);
    }

    @Override
    public void addSubscriptions() {

    }
}
