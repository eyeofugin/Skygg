package utils;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class MyMaths {
//    public static int damageRdmValue = 2;
    public static int dmgRdmPercentage = 15;
    public static int dmgEqualizer = 20;
    public static int getPcProbability(int turn, int tier) {
        int m = 1;
        int t = 1;
        int apprTier = 3 - tier;
        return 2;
    }

    public static int getDamage(int att, int def, int lethal) {
        int defResult = def - (def*lethal/100);
        return (int) (rdmize(att) * (dmgEqualizer / (dmgEqualizer + (double) rdmize(defResult))));
    }

    static private int rdmize(int a) {
        int rdmValue = a * dmgRdmPercentage / 100;
        return a + ThreadLocalRandom.current().nextInt(-1*rdmValue, rdmValue+1);

    }

    static public int[] getIntArrayWithExclusiveRandValues(int from, int until, int size) {
        if (size <= (until-from) + 1) {
            int[] result = new int[size];
            Random rand = new Random();
            int valuesFound = 0;
            while (valuesFound < size) {
                int tryInt = rand.nextInt(from, until+1);
                if (IntStream.of(result).noneMatch(x->x==tryInt)) {
                    result[valuesFound++] = tryInt;
                }
            }
            return result;
        } else {
            return new int[0];
        }
    }
    static public boolean success(int chance) {
        Random rand = new Random();
        float f = rand.nextFloat();
        if(f <= (chance/100.0)) {
            return true;
        }else {
            return false;
        }
    }

    static public int getFromToIncl(int from, int to, List<Integer> exclude) {
        Random rand = new Random();
        boolean found = false;
        if (to - from + 1 <= exclude.size()) {
            return 0;
        }
        int randomInt = 0;
        int counter = 0;
        while (!found && counter < 100) {
            randomInt = rand.nextInt(from, to + 1);
            if (!exclude.contains(randomInt)) {
                found = true;
            }
            counter++;
        }
        return randomInt;
    }
}
