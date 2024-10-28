package utils;

import game.entities.Hero;

import java.util.ArrayList;
import java.util.List;

public class HeroQueue {
    List<Hero> heroList = new ArrayList<>();

    public Hero peek()  {
        return this.heroList.get(0);
    }
    public void addAll(List<Hero> heroes) {
        this.heroList.addAll(heroes);
    }
    public void removeAll(List<Hero> heroes) {
        this.heroList.removeAll(heroes);
    }
    public void sendToBack(Hero hero) {
        this.heroList.remove(hero);
        this.heroList.add(hero);
    }
}
