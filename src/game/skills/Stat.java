package game.skills;

import game.skills.changeeffects.effects.Resilient;

import java.util.Random;

public enum Stat {

    MAGIC("Magic","MAG"),
    FORCE("Force", "FOR"),
    ENDURANCE("Endurance", "END"),
    FINESSE("Finesse", "FIN"),
    ACCURACY("Accuracy", "ACC"),
    EVASION("Evasion", "EVA"),
    SPEED("Speed", "SPE"),
    MAX_ACTION("Max", "ACT"),
    CURRENT_ACTION("Action", "CAC"),
    MAX_LIFE("Max", "LIF"),
    LIFE_REGAIN("Regain", "LRE"),
    CURRENT_LIFE("Life", "CLI"),
    MANA("Max", "MAN"),
    MANA_REGAIN("Regain", "MRE"),
    CURRENT_MANA("Mana", "CMA"),
    FAITH("Max", "FAI"),
    CURRENT_FAITH("Faith", "CFA"),
    CRIT_CHANCE("Crit Chance", "CRI");

    private String translationString;
    private String iconKey;

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
}
