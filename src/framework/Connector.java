package framework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Connector {

    public static Map<String, List<Connection>> subscriptions = new HashMap<>();

    public void addSubscription(String topic, Connection connection) {
        if (subscriptions.containsKey(topic)) {
            subscriptions.get(topic).add(connection);
        } else {
            subscriptions.put(topic, List.of(connection));
        }
    }
    public void fireTopic(String topic, ConnectionPayload payload) {
        if (subscriptions.containsKey(topic)) {
            for (Connection connection : subscriptions.get(topic)) {
                try {
                    Method method = connection.element.getClass().getMethod(connection.methodName);
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
