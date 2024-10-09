package game.skills;

import game.skills.changeeffects.effects.Resilient;

import java.util.Random;

public enum Stat {

    //Damage Types
    RANGED("Ranged", 1, "BLS"),
    MELEE("Melee", 2, "BLA"),
    FORCE("Burning",3, "LIG"),

//Stat Changeable {
    //Offensive Stats
    STRENGTH("Strength",4,"STR"),
    PRECISION("Precision", 5,"PRE"),
    WILLPOWER("Willpower", 6,"WLP"),

    //Defensive Stats
    VITALITY("Vitality", 7, "VIT"),
    REFLEXES("Reflexes", 8, "REF"),
    HARMONY("Harmony", 9,"HMN"),

    //Other Stats
    SPEED("Speed", 18, "SPD"),
    EVASION("Evasion", 19, "EVA"),
    ACCURACY("Accuracy", 20, "ACC"),
    LETHALITY("Lethality", 17, "LET"),

//    COUNTER_CHANCE("Counter Chance", 15, "CCH"),
    CRIT_CHANCE("Crit Chance", 16, "CRT"),
//}

    MAX_LIFE("Max", 10, "MLI"),
    LIFE_REGAIN("Regain", 12, "LRE"),
    MAX_MANA("Max", 21, "MMA"),

    //Temp Stats
    CURRENT_LIFE("Life", 11, "CLI"),
    CURRENT_MANA("Mana", 22, "CMA"),
    TURN_START_ACTION("Starting Action", 13, "TSA"),
    CURRENT_ACTION("Current Action", 14, "CAC");

    private String translationString;
    private int id;
    private String iconKey;
    public static Stat[] changeableStats = new Stat[]{STRENGTH, PRECISION, WILLPOWER, VITALITY, REFLEXES, HARMONY, SPEED, EVASION, ACCURACY, LETHALITY, CRIT_CHANCE};

    Stat(String translationString, int id, String iconKey) {
        this.translationString = translationString;
        this.id = id;
        this.iconKey = iconKey;
    }

    public static Stat getRdmStat() {
        Random random = new Random();
        return changeableStats[random.nextInt(changeableStats.length)];
    }

    public String getTranslationString() {
        return translationString;
    }

    public int getId() {
        return id;
    }

    public String getIconKey() {
        return iconKey;
    }
    public String getIconString() {
        return "["+iconKey+"]";
    }
}
