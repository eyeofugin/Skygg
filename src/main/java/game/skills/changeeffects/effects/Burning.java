package game.skills.changeeffects.effects;

import game.skills.Effect;

public class Burning extends Effect {

    public static String ICON_STRING = "BRN";
    public Burning(int stacks) {
        this.name = "Burning";
        this.iconString = ICON_STRING;
        this.stackable = true;
        this.stacks = stacks;
        this.description = "Loses 1 health per stack each turn.";
        this.type = ChangeEffectType.DEBUFF;
    }
    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }

    @Override
    public void turnLogic() {
        this.hero.effectDamage(this.stacks, this);
    }

    @Override
    public Effect getNew() {
        return new Burning(this.stacks);
    }

    @Override
    public void addSubscriptions() {}
}
