package framework;

public class Logger {
    private static final boolean DEBUG = true;
    private static final boolean AIDEBUG = false;

    public static void logLn(String s) {
        if (DEBUG) {
            System.out.println(s);
        }
    }

    public static void log(String s) {
        if (DEBUG) {
            System.out.print(s);
        }
    }

    public static void aiLog(String s) {
        if (AIDEBUG) {
            System.out.print(s);
        }
    }

    public static void aiLogln(String s) {
        if (AIDEBUG) {
            System.out.println(s);
        }
    }
}
