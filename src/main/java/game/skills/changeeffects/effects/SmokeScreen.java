package game.skills.changeeffects.effects;

import game.skills.Effect;
import game.skills.Stat;
import java.util.HashMap;
import java.util.Map;

public class SmokeScreen extends Effect {

    public SmokeScreen(int turns) {
        this.turns = turns;
        this.name = "Smoke Screen";
        this.description = "Gain 20 evasion";
        this.type = ChangeEffectType.DEBUFF;
        Map<Stat, Integer> statBonusMap = new HashMap<>();
        statBonusMap.put(Stat.EVASION, 50);
        this.statBonus = statBonusMap;
    }

    @Override
    public Effect getNew() {
        return new SmokeScreen(this.turns);
    }

    @Override
    public void addSubscriptions() {

    }
}
