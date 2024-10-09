package game.entities;

import framework.graphics.elements.ArenaHeroElement;

import java.util.ArrayList;
import java.util.List;

public class HeroTeam {

    private List<ArenaHeroElement> heroes = new ArrayList<>();
    private List<ArenaHeroElement> deadHeroes = new ArrayList<>();

    public HeroTeam(ArenaHeroElement... heroes) {
        this.heroes = List.of(heroes);
    }

    public List<ArenaHeroElement> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<ArenaHeroElement> heroes) {
        this.heroes = heroes;
    }

    public List<ArenaHeroElement> getDeadHeroes() {
        return deadHeroes;
    }

    public void setDeadHeroes(List<ArenaHeroElement> deadHeroes) {
        this.deadHeroes = deadHeroes;
    }
}
