package framework.states;

import framework.Engine;
import framework.graphics.GUIElement;
import game.entities.Hero;
import game.entities.HeroTeam;
import game.entities.individuals.burner.H_Burner;
import game.entities.individuals.dev.dummy.DUMMY;
import game.entities.individuals.dragonbreather.H_DragonBreather;
import game.entities.individuals.firedancer.H_FireDancer;
import game.entities.individuals.paladin.H_Paladin;
import game.entities.individuals.phoenixguy.H_Phoenixguy;

public class StateManager {

    public Engine e;
    private GUIElement activeScene;

    public StateManager(Engine e) {
        this.e = e;
        Arena arena = new Arena(e);
        H_Burner hero = new H_Burner();
        hero.enterArena(false, 1, arena);

        H_FireDancer hero3 = new H_FireDancer();
        hero3.enterArena(false, 2, arena);

        H_DragonBreather hero2 = new H_DragonBreather();
        hero2.enterArena(false,3, arena);

        H_Paladin paladin = new H_Paladin();


//        H_Phoenixguy hero = new H_Phoenixguy();
//        hero.enterArena(false, 3, arena);

        DUMMY dummy1 = new DUMMY(1);
        dummy1.enterArena(true, 4, arena);
        DUMMY dummy2 = new DUMMY(2);
        dummy2.enterArena(true, 5, arena);
        DUMMY dummy3 = new DUMMY(3);
        dummy3.enterArena(true, 6, arena);
        DUMMY dummy4 = new DUMMY(4);
        dummy4.enterArena(true, 7, arena);

        HeroTeam friends = new HeroTeam(1, false,
                new Hero[]{
                        null,
                        hero,
                        hero3,
                        hero2});
        HeroTeam enemies = new HeroTeam(-1, true,
                new Hero[]{dummy1,dummy2,dummy3,dummy4});
        arena.setTeams(friends, enemies);
        activeScene = arena;

    }
    public int[] render(){

        return this.activeScene.render();
    }
    public void update(int frame) {
        this.activeScene.update(frame);
    }
}