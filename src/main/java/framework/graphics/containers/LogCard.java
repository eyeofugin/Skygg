package framework.graphics.containers;

import framework.Engine;
import framework.Property;
import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import java.util.Stack;

public class LogCard extends GUIElement {
    Stack<String> logs = new Stack<>();
    Engine engine;

    public LogCard(Engine e) {
        super(Property.LOG_WIDTH, Property.LOG_HEIGHT);
        this.x = Property.LOG_X;
        this.y = Property.LOG_Y;
        this.engine = e;
        logs.add("Small Message");
        logs.add("Small Message");
        logs.add("This is a test of a medium log message");
        logs.add("This is a test for a very long text message containing lots of info");
    }

    @Override
    public void update(int frame) {
        if (this.active) {
            if (engine.keyB._backPressed) {
                logs.pop();
            }
        }
    }

    @Override
    public int[] render() {
        clear();
        background(Color.VOID);
//        renderVisibleLogs();
//        renderXButton();
        return this.pixels;
    }
    private void renderVisibleLogs() {
//        int canvasW = this.width-20;
//        int canvasH = this.height-20;
//        int innerPadding = (canvasH - (Property.LOG_ITEM_H * 4)) / 3 - 1;
//        int[] p = new Builder()
//                .setColor(Color.VOID)
//                .setAnchors(0,0)
//                .setDimensions(canvasW, canvasH)
//                .setInnerPadding(innerPadding)
//                .addVertical(
//                        getLogSizes(),
//                        getLogs()
//                ).build().render();
//        fillWithGraphicsSize(0, 20, canvasW, canvasH, p, null);
    }
//    private int[] getLogSizes() {
//        int[] logSizes = new int[getLogAmount()];
//        Arrays.fill(logSizes, Property.LOG_ITEM_H);
//        return logSizes;
//    }
    private int getLogAmount() {
        return Math.min(4, this.logs.size());
    }
//    private Builder[] getLogs() {
//        Builder[] builders = new Builder[getLogAmount()];
//        for (int i = 0; i < builders.length; i++) {
//            int stackIndex = this.logs.size() - (1 + i);
//            String log = this.logs.get(stackIndex);
//            builders[i] = new Builder()
//                    .setBordered(this.active?Color.RED:Color.WHITE)
//                    .setPadding(5,5)
//                    .addHorizontal(
//                            new Builder()
//                                    .setGraphics(getTextBlock(log,Property.LOG_ITEM_W - 10, 2, TextAlignment.LEFT, Color.VOID, Color.WHITE),
//                                    Property.LOG_ITEM_W - 10, -1)
//                    );
//        }
//        return builders;
//    }
//    private void renderXButton() {
//        int buttonW = 30;
//        int buttonH = 30;
//        int[] p = new Builder()
//                .setAnchors(this.width - buttonW, 0)
//                .setDimensions(buttonW, buttonH)
//                .setGraphics(getTextLine("X", buttonW, buttonH, 2, Color.WHITE), buttonW, buttonH).build().render();
//        fillWithGraphicsSize(this.width - buttonW, 0, buttonW, buttonH, p, Color.WHITE);
//    }
}
