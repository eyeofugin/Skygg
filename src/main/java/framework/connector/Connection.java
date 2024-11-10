package framework.connector;


public class Connection {

    Object element;
    String methodName;
    Class<? extends ConnectionPayload> payloadClass;

    public Connection(Object element, Class<? extends ConnectionPayload> payloadClass, String methodName) {
        this.element = element;
        this.methodName = methodName;
        this.payloadClass = payloadClass;
    }
}
