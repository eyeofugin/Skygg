package game.skills;


import java.util.Random;

public enum Stat {

    MAGIC("Magic","MAG"),
    FORCE("Force", "FOR"),
    FINESSE("Finesse", "FIN"),
    STAMINA("Stamina", "STA"),
    ENDURANCE("Endurance", "END"),
    SPEED("Speed", "SPE"),

    ACCURACY("Accuracy", "ACC"),
    EVASION("Evasion", "EVA"),

    MAX_ACTION("Max", "ACT"),
    CURRENT_ACTION("Action", "CAC"),

    LIFE("Max", "LIF"),
    LIFE_REGAIN("Regain", "LRE"),
    CURRENT_LIFE("Life", "CLI"),

    MANA("Max", "MAN"),
    MANA_REGAIN("Regain", "MRE"),
    CURRENT_MANA("Mana", "CMA"),

    FAITH("Max", "FAI"),
    CURRENT_FAITH("Faith", "CFA"),

    CRIT_CHANCE("Crit Chance", "CRI");

    private final String translationString;
    private final String iconKey;

    Stat(String translationString, String iconKey) {
        this.translationString = translationString;
        this.iconKey = iconKey;
    }

    public String getTranslationString() {
        return translationString;
    }
    public String getIconKey() {
        return iconKey;
    }
    public String getIconString() {
        return "["+iconKey+"]";
    }

    public static Stat getRdmStat() {

        Stat[] stdStats = new Stat[]{MAGIC, FORCE, ENDURANCE, FINESSE, ACCURACY, EVASION, SPEED};
        Random random = new Random();
        int rndInt = random.nextInt(7);
        return stdStats[rndInt];
    }
}
