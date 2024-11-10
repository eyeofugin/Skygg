package utils;

import game.skills.Stat;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "statsheet")
public class StatSheet {


    @XmlElement(name = "stat")
    public Stat stat;

    public void setStat(Stat stat) {
        this.stat = stat;
    }
}
