package game.skills.changeeffects.effects;

import game.skills.Effect;
import game.skills.Stat;

public class Burning extends Effect {

    public Burning(int turns, int stacks) {
        this.turns = turns;
        this.name = "Burning";
        this.stackable = true;
        this.stacks = stacks;
        this.description = "Loses 1 health per stack each turn.";
        this.type = ChangeEffectType.EFFECT;
        this.dmgType = Stat.HEAT;
    }

    @Override
    public void turnLogic() {
        this.hero.effectDamage(this.stacks, this);
    }

    @Override
    public Effect getNew() {
        return new Burning(this.turns, this.stacks);
    }

    @Override
    public void addSubscriptions() {}
}
