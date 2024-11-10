package framework;

import framework.resources.SpriteLibrary;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import utils.StatSheet;

import javax.swing.*;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) {


        try {
            URI uri = SpriteLibrary.class.getClassLoader().getResource("testfile.xml").toURI();
            JAXBContext context = JAXBContext.newInstance(StatSheet.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StatSheet statSheet = (StatSheet) unmarshaller.unmarshal(new File(uri));
            System.out.println(statSheet.stat);
        } catch (URISyntaxException | JAXBException e) {
            throw new RuntimeException(e);
        }

        Loader.load();
        TitleContainer titleContainer = new TitleContainer();
        JFrame window = new JFrame();
        window.setBounds(0, 0, 0, 0);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setUndecorated(true);

        Engine engine = new Engine(window);

        window.add(engine);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        engine.start();
    }
}
