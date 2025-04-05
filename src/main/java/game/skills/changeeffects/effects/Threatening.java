package game.skills.changeeffects.effects;

import game.skills.Effect;

public class Threatening extends Effect {

    public static String ICON_STRING = "THR";
    public Threatening(int turns) {
        this.turns = turns;
        this.iconString = ICON_STRING;
        this.name = "Threatening";
        this.stackable = false;
        this.description = "Must be the target of single target skills.";
        this.type = ChangeEffectType.BUFF;
    }
    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }
    @Override
    public Effect getNew() {
        return new Threatening(this.turns);
    }

    @Override
    public void addSubscriptions() {
    }
}
