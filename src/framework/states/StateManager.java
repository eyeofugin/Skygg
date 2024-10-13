package framework.states;

import framework.Engine;
import framework.graphics.GUIElement;
import game.entities.Hero;
import game.entities.HeroGroup;
import game.entities.individuals.dev.BAZE_TEST;
import game.entities.individuals.dev.Chirrut_TEST;
import game.entities.individuals.dev.DUMMY;

public class StateManager {

    public Engine e;
    private GUIElement activeScene;

    public StateManager(Engine e) {
        this.e = e;
        Arena arena = new Arena(e);
        HeroGroup friends = new HeroGroup(
                new Hero[]{
                        null,
                        null,
                        new Chirrut_TEST(arena, false, 2),
                        new BAZE_TEST(arena, false, 3)});
        HeroGroup enemies = new HeroGroup(
                new Hero[]{new DUMMY(arena, true, 4),
                        new DUMMY(arena, true, 5),
                        new DUMMY(arena, true, 6),
                        new DUMMY(arena, true, 7)});
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