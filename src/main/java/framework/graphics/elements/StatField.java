package framework.graphics.elements;

import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import framework.graphics.text.TextAlignment;
import game.entities.Hero;
import game.skills.Stat;

public class StatField extends GUIElement {
    private final Hero hero;
    private final Stat[] leftStatArray = new Stat[]{Stat.MAGIC, Stat.POWER, Stat.STAMINA, Stat.ENDURANCE};
    private final Stat[] rightStatArray = new Stat[]{Stat.SPEED, Stat.EVASION, Stat.ACCURACY, Stat.CRIT_CHANCE, Stat.LETHALITY};

    public StatField(Hero hero) {
        this.hero = hero;
        this.setSize(98, 100);
    }

    @Override
    public int[] render() {
        background(Color.VOID);
        renderSide(leftStatArray, 3);
        renderSide(rightStatArray, this.width / 2);
        return pixels;
    }

    private void renderSide(Stat[] stats, int x) {
        int yf = 3;
        for (Stat stat : stats) {
            int totalValue = hero.getStat(stat);
            int statChange = hero.getStatChange(stat);
            int baseValue = totalValue - statChange;
            String baseStatString = stat.getIconString() + ":" + baseValue;
            int[] baseStatStringPixels = getTextLine(baseStatString, this.width / 2 - 5, 8, TextAlignment.LEFT, Color.VOID, Color.WHITE);
            fillWithGraphicsSize(x,yf,this.width / 2 - 5, 8, baseStatStringPixels, false);
            if (statChange != 0) {
                String statChangeString = statChange > 0 ? "{006}+" : "{012}";
                statChangeString += statChange;
                statChangeString += "{000}";
                int[] statChangeStringPixels = getTextLine(statChangeString, this.width / 2 - 5, 8, TextAlignment.RIGHT, Color.VOID, Color.WHITE);
                fillWithGraphicsSize(x, yf, this.width / 2 - 5, 8, statChangeStringPixels, false);
            }
//            String statString = stat.getIconString() + ": " + baseValue;
//            if (statChange != 0) {
//                statString += statChange > 0 ? "{006}+" : "{012}-";
//                statString += statChange;
//                statString += "{000}";
//            }
//            int[] statStringPixels = getTextLine(statString, this.width / 2 - 3, 8, TextAlignment.LEFT, Color.BLACK, Color.WHITE);
//            fillWithGraphicsSize(x, yf, this.width / 2 - 3, 8, statStringPixels, false);
            yf += 9;
        }
    }
}
