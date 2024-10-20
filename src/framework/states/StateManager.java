package framework.states;

import framework.Engine;
import framework.graphics.GUIElement;
import game.entities.Hero;
import game.entities.HeroTeam;
import game.entities.individuals.dev.DUMMY;
import game.entities.individuals.phoenixguy.H_Phoenixguy;

public class StateManager {

    public Engine e;
    private GUIElement activeScene;

    public StateManager(Engine e) {
        this.e = e;
        Arena arena = new Arena(e);
        H_Phoenixguy pGuy = new H_Phoenixguy();
        pGuy.enterArena(false, 3, arena);

        DUMMY dummy1 = new DUMMY(1);
        dummy1.enterArena(true, 4, arena);
        DUMMY dummy2 = new DUMMY(2);
        dummy2.enterArena(true, 5, arena);
        DUMMY dummy3 = new DUMMY(3);
        dummy3.enterArena(true, 6, arena);
        DUMMY dummy4 = new DUMMY(4);
        dummy4.enterArena(true, 7, arena);

        HeroTeam friends = new HeroTeam(1,
                new Hero[]{
                        null,
                        null,
                        null,
                        pGuy});
        HeroTeam enemies = new HeroTeam(-1,
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