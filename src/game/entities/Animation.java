package game.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Animation {
    public Map<Integer, int[]> images = new HashMap<>();
    public int length;
    public int animationCounter = 0;
    public String name = "";

    public int[] getImage(int counter) {
        List<Integer> sortedKeys = images.keySet().stream().sorted().toList();
        for (Integer i : sortedKeys) {
            if (counter <= i) {
                return images.get(i);
            }
        }
        return new int[0];
    }
}
