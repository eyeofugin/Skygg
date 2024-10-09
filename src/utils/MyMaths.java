package utils;

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
        return (int) (rdmize(att) * ((dmgEqualizer + (lethal / 2)) / (dmgEqualizer + (double) rdmize(def))));
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
}
