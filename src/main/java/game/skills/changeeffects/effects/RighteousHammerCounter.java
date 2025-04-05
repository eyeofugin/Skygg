package game.skills.changeeffects.effects;

import game.skills.Effect;

public class RighteousHammerCounter extends Effect {

    public static String ICON_STRING = "RIH";
    public RighteousHammerCounter(int stacks) {
        this.turns = -1;
        this.iconString = ICON_STRING;
        this.name = "Hammer Counter";
        this.stackable = true;
        this.stacks = stacks;
        this.description = "Hammer Swings.";
        this.type = ChangeEffectType.BUFF;
    }

    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }
    @Override
    public Effect getNew() {
        return new RighteousHammerCounter(this.stacks);
    }

    @Override
    public void addSubscriptions() {
    }
}
