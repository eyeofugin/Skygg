package game.skills.changeeffects.effects;

import game.skills.Effect;

public class Combo extends Effect {

    public static String ICON_STRING = "CMB";
    public Combo() {
        this.name = "Combo";
        this.iconString = ICON_STRING;
        this.stackable = false;
        this.description = "Combo.";
        this.type = ChangeEffectType.BUFF;
    }
    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }
    @Override
    public Effect getNew() {
        return new Combo();
    }

    @Override
    public void addSubscriptions() {}
}
