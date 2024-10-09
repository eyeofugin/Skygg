package framework;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Loader.load();
        TitleContainer titleContainer = new TitleContainer();
        JFrame window = new JFrame("Fps: " + titleContainer.val);
        window.setBounds(0, 0, 0, 0);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        Engine engine = new Engine(window);

        window.add(engine);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        engine.start();
    }
}
