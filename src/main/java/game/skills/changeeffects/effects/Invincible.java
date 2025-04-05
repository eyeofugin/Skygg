package game.skills.changeeffects.effects;

import game.skills.Effect;

public class Invincible extends Effect {

    public static String ICON_STRING = "INV";
    public Invincible(int turns) {
        this.turns = turns;
        this.iconString = ICON_STRING;
        this.name = "Invincible";
        this.stackable = false;
        this.description = "Cannot die.";
        this.type = ChangeEffectType.BUFF;
    }
    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }
    @Override
    public Effect getNew() {
        return new Invincible(this.turns);
    }

    @Override
    public void addSubscriptions() {

    }
}
