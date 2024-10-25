package game.skills;

import framework.Property;
import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import game.entities.Hero;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Effect {
    public enum ChangeEffectType {
        STATUS_INFLICTION,
        BUFF,
        DEBUFF;
    }

    public int turns = -1;
    public int stacks = 1;
    public int intensity;
    public int successChance= 100;
    public List<EffectCondition> conditions;
    public Hero origin;
    public Hero hero;

    public String name;
    public String description;
    public boolean stackable;
    public ChangeEffectType type;
    protected Map<Stat, Integer> statBonus = new HashMap<>();

    public abstract Effect getNew();
    public abstract void addSubscriptions();
    public void turnLogic() {}

    public void addToHero(){
        for (Map.Entry<Stat, Integer> mapEntry : statBonus.entrySet()) {
            hero.addToStat(mapEntry.getKey(), mapEntry.getValue());
        }
        addSubscriptions();
    }
    public void addStack(int stacks){
        for (int i = 0; i < stacks; i++) {
            for (Map.Entry<Stat, Integer> mapEntry : statBonus.entrySet()) {
                hero.addToStat(mapEntry.getKey(), mapEntry.getValue());
            }
        }
        this.stacks+=stacks;
    }
    public void removeEffect() {
        for (int i = 0; i < stacks; i++) {
            removeStack();
        }

    }
    public void removeStack() {
        for (Map.Entry<Stat, Integer> mapEntry : statBonus.entrySet()) {
            hero.addToStat(mapEntry.getKey(), mapEntry.getValue() * -1);
        }
    }

    public void turn() {
        if (turns == -1) {
            turnLogic();
            return;
        }
        if(turns>0) {
            this.turns--;
            turnLogic();
        }
    }
    public int getDamageChanges(Hero caster, Hero target, Skill damagingSkill, int result, Stat damageType, boolean simulated) {
        return result;
    }

    public void addStacksToSprite(int[] effectSprite) {
        if (stackable) {
            int x = Property.EFFECT_ICON_SIZE - 2;
            for (int i = 0; i < this.stacks; i++) {
                GUIElement.verticalLine(x, Property.EFFECT_ICON_SIZE - 3,Property.EFFECT_ICON_SIZE - 1, Property.EFFECT_ICON_SIZE, effectSprite, Color.RED);
                x-=2;
            }
        }
    }

    @Override
    public String toString() {
        return "Effect{" +
                "turns=" + turns +
                ", stacks=" + stacks +
                ", intensity=" + intensity +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}