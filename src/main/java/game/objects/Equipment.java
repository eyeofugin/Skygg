package game.objects;

import framework.connector.Connector;
import framework.graphics.text.Color;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import utils.FileWalker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Equipment {

    protected final String packageName;
    protected Map<Stat, Integer> statBonus = new HashMap<>();
    protected Hero hero;
    protected boolean active;
    protected Skill skill;
    protected final String name;
    protected List<Stat> adaptiveStats;

    public Equipment(String packageName, String name) {
        this.packageName = packageName;
        this.name = name;
    }
    public void addSubscriptions() {}
    public void removeSubscriptions() {
        Connector.removeSubscriptions(this);
    }

    public void turn() {}
    public void equipToHero(Hero hero) {
        this.hero = hero;
        this.hero.equip(this);
        addSubscriptions();
        statChange(1);
    }

    public void unEquipFromHero() {
        this.hero.unequip(this);
        statChange(-1);
        removeSubscriptions();
        this.hero = null;
    }

    private void statChange(int sign) {
        for (Map.Entry<Stat, Integer> statBonus : this.statBonus.entrySet()) {
            if (this.adaptiveStats != null && !this.adaptiveStats.isEmpty()) {
                if (this.adaptiveStats.contains(statBonus.getKey())) {
                    this.hero.addToStat(statBonus.getKey(), statBonus.getValue() * this.hero.arena.round * sign);
                }
            } else {
                this.hero.addToStat(statBonus.getKey(), statBonus.getValue() * sign);
            }
        }
    }

    public void deactivateEquipment() {
        this.active = false;
        statChange(-1);
    }

    public void activateEquipment() {
        if (active) {
            return;
        }
        statChange(1);
        this.active = true;
    }
    protected Map<Stat, Integer> loadStatBonus() {
        Map<Stat, Integer> result = FileWalker.getEquipmentStatJson("equipments/" + this.packageName + "/stats.json");
        if (result != null) {
            return result;
        }
        return new HashMap<>();
    }

    public String getName() {
        return this.name;
    }
    public String getDescription() { return " "; }
    public Skill getSkill() { return skill; }
    public boolean isActive() { return active; }
    public String getStatBonusString() {
        if (statBonus == null || statBonus.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Stat, Integer> entry : statBonus.entrySet()) {
            builder.append(entry.getKey().getColorKey());
            if (entry.getValue()>0) {
                builder.append(Color.MEDIUMGREEN.getCodeString());
                builder.append("+");
            } else {
                builder.append(Color.DARKRED.getCodeString());
            }
            builder.append(entry.getValue()).append(" ");
            builder.append(entry.getKey().getIconString());
            builder.append(" ");
        }
        return builder.toString();
    }

}
