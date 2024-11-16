package game.skills;


import java.util.List;
import java.util.Random;

public enum Stat {

    MAGIC("Magic","MAG", "{013}"),
    FORCE("Force", "FOR", "{014}"),
    FINESSE("Finesse", "FIN", "{015}"),
    STAMINA("Stamina", "STA", "{016}"),
    ENDURANCE("Endurance", "END", "{017}"),
    SPEED("Speed", "SPE", "{018}"),

    ACCURACY("Accuracy", "ACC", "{001}"),
    EVASION("Evasion", "EVA", "{001}"),
    CRIT_CHANCE("Crit Chance", "CRI", "{001}"),
    LETHALITY("Lethality", "LET", "{001}"),


    LIFE("Max", "LIF", "{009}"),
    LIFE_REGAIN("Regain", "LRE", "{009}"),
    CURRENT_LIFE("Life", "CLI", "{009}"),

    MANA("Max", "MAN", "{011}"),
    MANA_REGAIN("Regain", "MRE", "{011}"),
    CURRENT_MANA("Mana", "CMA", "{011}"),

    FAITH("Max", "FAI", "{007}"),
    CURRENT_FAITH("Faith", "CFA", "{007}"),

    SHIELD("Shield", "SHI", "{008}"),


    MAX_ACTION("Max", "ACT", "{001}"),
    CURRENT_ACTION("Action", "CAC", "{001}");


    private final String translationString;
    private final String iconKey;
    private final String colorKey;

    public static List<Stat> nonResourceStats = List.of(Stat.MAGIC, Stat.FINESSE,
            Stat.FORCE, Stat.ENDURANCE, Stat.STAMINA, Stat.EVASION, Stat.CRIT_CHANCE,
            Stat.ACCURACY, Stat.LETHALITY, Stat.SPEED);

    Stat(String translationString, String iconKey, String colorKey) {
        this.translationString = translationString;
        this.iconKey = iconKey;
        this.colorKey = colorKey;
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
    public String getColorKey() {
        return colorKey;
    }

    public static Stat getRdmStat() {

        Stat[] stdStats = new Stat[]{MAGIC, FORCE, ENDURANCE, FINESSE, ACCURACY, EVASION, SPEED};
        Random random = new Random();
        int rndInt = random.nextInt(7);
        return stdStats[rndInt];
    }
}
