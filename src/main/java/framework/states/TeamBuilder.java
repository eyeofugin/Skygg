package framework.states;

import framework.Engine;
import framework.graphics.GUIElement;
import framework.graphics.containers.HUD;
import framework.graphics.containers.TeamBuildAbilitiesCard;
import framework.graphics.containers.TeamBuildHeroesCard;
import framework.graphics.containers.TeamBuildItemsCard;
import framework.graphics.text.Color;
import game.entities.Hero;
import game.entities.HeroTeam;
import java.util.HashMap;
import java.util.Map;

public class TeamBuilder extends GUIElement {


    private final Engine engine;
    public final HUD hud;

    private int activeTeam;
    private HeroTeam[] teams;
    public int cardPointer = 0;
    private Hero activeHero;
    public boolean finished = false;

    TeamBuildHeroesCard heroesCard;
    Map<Hero, TeamBuildAbilitiesCard> abilitiesCardMap = new HashMap<>();
    Map<Hero, TeamBuildItemsCard> itemsCardMap = new HashMap<>();

    public TeamBuilder(Engine e) {
        super(Engine.X, Engine.Y);
        this.engine = e;
        this.hud = new HUD(e);
        this.teams = this.engine.memory.teams;
        this.activeTeam = 1;
        this.active = true;
        this.heroesCard = new TeamBuildHeroesCard(this.engine, this.teams[0], this);
        this.children.add(heroesCard);

        initCards(0);
    }

    private void initCards(int teamIndex) {
        this.abilitiesCardMap = new HashMap<>();
        this.itemsCardMap = new HashMap<>();
        HeroTeam team = this.teams[teamIndex];
        for (Hero hero : team.heroes) {
            TeamBuildAbilitiesCard card = new TeamBuildAbilitiesCard(this.engine);
            card.setHero(hero);
            card.setActive(false);
            this.abilitiesCardMap.put(hero, card);
            TeamBuildItemsCard itemsCard = new TeamBuildItemsCard(this.engine);
            itemsCard.setHero(hero);
            card.setActive(false);
            this.itemsCardMap.put(hero, itemsCard);
        }
    }
    @Override
    public void update(int frame) {
        if (this.active) {
            if (engine.keyB._shoulderLeftPressed) {
                if (cardPointer != 0) {
                    cardPointer--;
                }
            }
            if (engine.keyB._shoulderRightPressed) {
                if (cardPointer != 2) {
                    cardPointer++;
                }
            }
            if (engine.keyB._contextPressed) {
                end();
            }
            updateCardSelection();
            updateChildren(frame);
        }
    }
    @Override
    protected void updateChildren(int frame) {
        super.updateChildren(frame);
        for (TeamBuildAbilitiesCard card : this.abilitiesCardMap.values()) {
            card.update(frame);
        }
        for (TeamBuildItemsCard card : this.itemsCardMap.values()) {
            card.update(frame);
        }
    }
    private void updateCardSelection() {
        deactivateAllChildren();
        this.heroesCard.setActive(this.cardPointer == 0);
        if (cardPointer == 1) {
            TeamBuildAbilitiesCard card = this.abilitiesCardMap.get(this.activeHero);
            if (card != null) {
                card.setActive(true);
            }
        }
        if (cardPointer == 2) {
            TeamBuildItemsCard card = this.itemsCardMap.get(this.activeHero);
            if (card != null) {
                this.itemsCardMap.get(this.activeHero).setActive(true);
            }
        }
    }
    @Override
    protected void deactivateAllChildren() {
        super.deactivateAllChildren();
        for (TeamBuildAbilitiesCard card : this.abilitiesCardMap.values()) {
            card.setActive(false);
        }
        for (TeamBuildItemsCard card : this.itemsCardMap.values()) {
            card.setActive(false);
        }
    }
    private void end() {
        finalizeHeroes();
        if (this.activeTeam == 1) {
            this.activeTeam = 2;
            this.children.remove(this.heroesCard);
            this.heroesCard = new TeamBuildHeroesCard(this.engine, this.teams[1], this);
            this.children.add(this.heroesCard);
            initCards(1);
        } else {
            this.engine.memory.teams = this.teams;
            this.finished = true;
        }
    }
    private void finalizeHeroes() {
        for (TeamBuildAbilitiesCard abilitiesCard : this.abilitiesCardMap.values()) {
            abilitiesCard.finish();
        }
        for (TeamBuildItemsCard itemCard : this.itemsCardMap.values()) {
            itemCard.finish();
        }
    }
    public void setActiveHero(Hero hero) {
        this.activeHero = hero;
    }
    public void activate() {
        this.active = true;
    }
    public void deactivate() {
        this.active = false;
    }

    @Override
    public int[] render() {
        background(Color.BLACK);
        renderDeco();
        renderTeam();
        renderAbilityCard();
        renderItemsCard();
        return this.pixels;
    }

    private void renderDeco() {
        fillWithGraphicsSize(10, 10, 200, 20, getTextLine("Team " + this.activeTeam, 200, 20, Color.WHITE), false);
    }

    private void renderTeam() {
        fillWithGraphicsSize(10, 20, this.heroesCard.getWidth(), this.heroesCard.getHeight(), this.heroesCard.render(), this.cardPointer == 0);
    }

    private void renderAbilityCard() {
        if (this.cardPointer == 1 || this.cardPointer == 0) {
            TeamBuildAbilitiesCard card = this.abilitiesCardMap.get(this.activeHero);
            if (card != null) {
                fillWithGraphicsSize(card.getX(), card.getY(), card.getWidth(), card.getHeight(), card.render(), this.cardPointer == 1);
            }
        }
    }
    private void renderItemsCard() {
        if (this.cardPointer == 2) {
            TeamBuildItemsCard card = this.itemsCardMap.get(this.activeHero);
            if (card != null) {
                fillWithGraphicsSize(card.getX(), card.getY(), card.getWidth(), card.getHeight(), card.render(), this.cardPointer == 2);
            }
        }
    }

}
