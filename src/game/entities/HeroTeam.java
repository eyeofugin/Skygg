package game.entities;

import game.skills.Stat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HeroTeam {

    private final int fillUpDirection;
    public  Hero[] heroes;
    public  List<Hero> deadHeroes = new ArrayList<>();

    public HeroTeam(int fillUpDirection, Hero[] heroes) {
        this.heroes = heroes;
        this.fillUpDirection = fillUpDirection;
    }

    public void updateAnimations(int frame) {
        Arrays.stream(this.heroes).filter(Objects::nonNull).forEach(e -> e.update(frame));
    }

    public List<Hero> removeTheDead() {
        List<Hero> removed = new ArrayList<>();
        for (int i = 0; i < this.heroes.length; i++) {
            if (this.heroes[i] != null && this.heroes[i].getStats().get(Stat.CURRENT_LIFE) < 1) {
                this.deadHeroes.add(this.heroes[i]);
                removed.add(this.heroes[i]);
                this.heroes[i] = null;
                for (int j = i - fillUpDirection; j >= 0 && j < this.heroes.length; j-=fillUpDirection) {
                    if (this.heroes[j] != null) {
                        this.heroes[j+fillUpDirection] = this.heroes[j];
                        this.heroes[j+fillUpDirection].position = j+fillUpDirection;
                        this.heroes[j] = null;
                    }
                }
            }
        }
        return removed;
    }

    public List<Hero> getHeroesAsList() {
        return List.of(heroes);
    }

    public void setHeroes(Hero[] heroes) {
        this.heroes = heroes;
    }

    public List<Hero> getDeadHeroes() {
        return deadHeroes;
    }

    public void setDeadHeroes(List<Hero> deadHeroes) {
        this.deadHeroes = deadHeroes;
    }
}
