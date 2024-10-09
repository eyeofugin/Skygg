package game.skills;

public class EffectCondition {
    public Effect effectAsCondition;
    public boolean targetCondition;
    public boolean casterCondition;
    public boolean allyCondition;
    public boolean enemyCondition;

    public EffectCondition(Effect effectAsCondition, boolean targetCondition) {
        this.effectAsCondition = effectAsCondition;
        this.targetCondition = targetCondition;
    }
}
