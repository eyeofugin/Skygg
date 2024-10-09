package game.skills.changeeffects.statusinflictions;

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
}
