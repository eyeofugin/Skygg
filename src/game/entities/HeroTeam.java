package game.entities;

import framework.connector.Connector;
import framework.connector.payloads.DeathTriggerPayload;
import game.skills.Stat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HeroTeam {

    private final int fillUpDirection;
    private final boolean enemy;
    public  Hero[] heroes;
    public  List<Hero> deadHeroes = new ArrayList<>();

    public HeroTeam(int fillUpDirection, boolean enemy, Hero[] heroes) {
        this.heroes = heroes;
        for (Hero hero: this.heroes) {
            if (hero != null) {
                hero.team = this;
            }
        }
        this.enemy = enemy;
        this.fillUpDirection = fillUpDirection;
    }

    public void updateAnimations(int frame) {
        Arrays.stream(this.heroes).filter(Objects::nonNull).forEach(e -> e.update(frame));
    }

    public List<Hero> removeTheDead() {
        List<Hero> removed = new ArrayList<>();
        int enemyOffset = this.enemy ? 4: 0;
        for (int i = 0; i < this.heroes.length; i++) {
            if (this.heroes[i] != null && this.heroes[i].getStat(Stat.CURRENT_LIFE) < 1) {
                this.deadHeroes.add(this.heroes[i]);
                removed.add(this.heroes[i]);
                DeathTriggerPayload pl = new DeathTriggerPayload()
                        .setDead(this.heroes[i]);
                Connector.fireTopic(Connector.DEATH_TRIGGER, pl);
                this.heroes[i] = null;
                for (int j = i - fillUpDirection; j >= 0 && j < this.heroes.length; j-=fillUpDirection) {
                    if (this.heroes[j] != null) {
                        this.heroes[j+fillUpDirection] = this.heroes[j];
                        this.heroes[j+fillUpDirection].setPosition(enemyOffset + j+fillUpDirection);
                        this.heroes[j] = null;
                    }
                }
            }
        }
        return removed;
    }

    public List<Hero> getHeroesAsList() {
        List<Hero> list = new ArrayList<>();
        for (Hero hero : this.heroes) {
            if (hero != null) {
                list.add(hero);
            }
        }
        return list;
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
