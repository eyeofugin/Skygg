package framework.connector;


public class Connection {

    Object element;
    String methodName;

    public Connection(Object element, String methodName) {
        this.element = element;
        this.methodName = methodName;
    }
}
