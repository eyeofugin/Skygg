package game.skills.changeeffects.effects;


import game.skills.Effect;

public class Resilient extends Effect {
    public Resilient() {
        this.name = "Resilient";
        this.description = "Immune to Status Effects";
        this.stackable = false;
        this.type = ChangeEffectType.EFFECT;
    }
    @Override
    public Resilient getNew() {
        return new Resilient();
    }
    public Resilient(int turns) {
        this();
        this.turns = turns;
    }
}
