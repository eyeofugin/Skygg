package game.entities;

import game.objects.Equipment;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.changeeffects.statusinflictions.Disenchanted;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hero {

    public static final String RESOURCE_MANA = "MANA";
    public static final String RESOURCE_FAITH = "FAITH";
    public static final String RESOURCE_LIFE = "LIFE";

    private String name;
    private Skill[] skills;
    private List<Equipment> equipments = new ArrayList<>();
    private String portraitName;

    private Map<Stat, Integer> stats = new HashMap<>();
    private String secondaryResource;
    private List<Effect> effects = new ArrayList<>();

    public Hero(String name, Map<Stat, Integer> stats, Skill[] skills) {
        this.name = name;
        this.stats = stats;
        this.skills = skills;
    }

//StatMagic
    public int getStat(Stat stat) {
        if (stat == null) {
            return 0;
        }
        int baseStat = this.stats.get(stat);
        if (!(this.getPermanentEffectStacks(Disenchanted.class) > 0)) {
            baseStat += getEquipmentStat(stat);
        }
        baseStat += getSkillStat(stat);
        baseStat += getEffectStat(stat);
    }
    private int getEquipmentStat(Stat stat) {
        int p = 0;
        for (Equipment e : this.equipments) {
            if (e!=null) {
                p += e.getStat(stat);
            }
        }
        return p;
    }

//EffectHandling
    public <T extends Effect> int getPermanentEffectStacks(Class<T> clazz) {
        int amount = 0;
        for (Effect currentEffect : effects) {
            if (currentEffect.getClass().equals(clazz)) {
                amount+=currentEffect.stacks;
            }
        }
        return amount;
    }
}
