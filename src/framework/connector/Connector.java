package framework.connector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Connector {

    public static String CAST_CHANGE = "CAST_CHANGE"; //Replacements like
    public static String TARGET_MODE = "TARGET_MODE"; //return the target viability of a skill's target
    public static String CAN_PERFORM = "CAN_PERFORM";
    public static String ON_PERFORM = "ON_PERFORM";
    public static String IS_TARGETED = "IS_TARGETED";
    public static String EOT_HEAL_CHANGES = "EOT_HEAL_CHANGES";
    public static String BASE_HEAL_CHANGES ="BASE_HEAL_CHANGES"; //Before defense calc
    public static String BASE_DMG_CHANGES ="BASE_DMG_CHANGES"; //Before defense calc
    public static String CRITICAL_TRIGGER = "CRITICAL_TRIGGER";
    public static String DMG_CHANGES = "DMG_CHANGES"; //Dmg actually done, after def calc
    public static String EFFECT_DMG_CHANGES = "EFFECT_DMG_CHANGES"; //Dmg actually done, after def calc
    public static String DMG_TRIGGER = "DMG_TRIGGER";
    public static String EFFECT_DMG_TRIGGER = "EFFECT_DMG_TRIGGER";
    public static String HEAL_CHANGES = "HEAL_CHANGES";
    public static String EFFECT_FAILURE = "EFFECT_FAILURE";
    public static String EFFECT_ADDED = "EFFECT_ADDED";
    public static String ACTION_INFLICTION = "ACTION_INFLICTION";
    public static String SHIELD_BROKEN = "SHIELD_BROKEN";
    public static String DMG_TO_SHIELD = "DMG_TO_SHIELD";

    public static String PREPARE_UPDATE = "PREPARE_UPDATE";
    public static String UPDATE = "UPDATE";
    public static String START_OF_TURN = "START_OF_TURN";
    public static String END_OF_TURN = "END_OF_TURN";
    public static String START_OF_MATCH = "START_OF_MATCH";

    public static Map<String, ArrayList<Connection>> subscriptions = new HashMap<>();

    public static void addSubscription(String topic, Connection connection) {
        if (subscriptions.containsKey(topic)) {
            subscriptions.get(topic).add(connection);
        } else {
            ArrayList<Connection> newList = new ArrayList<>();
            newList.add(connection);
            subscriptions.put(topic, newList);
        }
    }
    public static void fireTopic(String topic, ConnectionPayload payload) {
        if (subscriptions.containsKey(topic)) {
            for (Connection connection : subscriptions.get(topic)) {
                try {
                    Method method = connection.element.getClass().getMethod(connection.methodName, connection.payloadClass);
                    method.invoke(connection.element, payload);
                } catch (NoSuchMethodException e) {
                    System.out.println("Hä");
                } catch (InvocationTargetException e) {
                    System.out.println("Hä2");
                } catch (IllegalAccessException e) {
                    System.out.println("Hä3");
                }
            }
        }
    }
}
