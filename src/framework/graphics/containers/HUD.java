package framework.graphics.containers;

import framework.Engine;
import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import framework.states.Arena;
import game.entities.Entity;


public class HUD extends GUIElement {
    Engine e;
    Arena arena;
    TeamArenaOverview arenaOv;

    public HUD(Engine e) {
        super(Engine.X, Engine.Y);
        this.e = e;
    }

    public void update(int frame) {
        if (this.arenaOv!=null){
            this.arenaOv.update(frame);
        }
    }
    public int[] render() {
        background(Color.VOID);
        renderChildren();
        return this.pixels;
    }
    public void setArena(Arena arena) {
        this.arena = arena;
        this.arenaOv = new TeamArenaOverview(e, arena);
        this.children.add(arenaOv);
    }
    public void setActiveEntity(Entity e) {
        this.arenaOv.setActiveEntity(e);
    }
    public void activateTeamOverview() {
        this.arenaOv.activate();
    }
    public void disableTeamArenaOV() {
        this.arenaOv.deactivate();
    }
}
