package framework;

import framework.graphics.GUIElement;

import java.util.List;

public class Connection {

    GUIElement element;
    String methodName;

    public Connection(GUIElement element, String methodName) {
        this.element = element;
        this.methodName = methodName;
    }
}
