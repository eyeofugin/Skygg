package game.skills.changeeffects.statusinflictions;

import game.skills.Effect;
import game.skills.Stat;

public class Bleeding extends Effect {

    public Bleeding(int turns) {
        this.turns = turns;
        this.name = "Bleeding";
        this.stackable = false;
        this.description = "Loses 15% health each turn.";
        this.type = ChangeEffectType.STATUS_INFLICTION;
    }

    @Override
    public void turnLogic() {
        int dmg = this.hero.getStat(Stat.LIFE) * 15 / 100;
        this.hero.effectDamage(dmg, this);
    }

    @Override
    public Effect getNew() {
        return new Bleeding(this.turns);
    }

    @Override
    public void addSubscriptions() {}
}
