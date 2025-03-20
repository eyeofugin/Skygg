package framework.states;

import framework.Engine;
import framework.graphics.GUIElement;
import game.entities.DraftBuilder;
import game.entities.Hero;
import game.entities.HeroTeam;

public class StateManager {

    public Engine engine;
    private GUIElement activeScene;

    public StateManager(Engine e) {
        this.engine = e;
        HeroTeam left = new HeroTeam(1, new Hero[0], 1);
        HeroTeam right = new HeroTeam(-1, new Hero[0], 2);

        this.engine.memory = new Memory();
        this.engine.memory.teams = new HeroTeam[]{left,right};
        PvpDraft draft = new PvpDraft(e);
        draft.setActive(true);
        this.activeScene = draft;
//        Draft draft = new Draft(e);
//        draft.setActive(true);
//        this.activeScene = draft;
    }

    public void joinArena(Memory memory) {
        Arena arena = new Arena(this.engine, true);
        arena.round = memory.pvpRound;
        arena.setTeams(memory.teams[0], memory.teams[1]);
        this.activeScene = arena;
    }

    public void joinArena(Draft draft) {
        Arena arena = new Arena(this.engine, false);
        arena.round = draft.round;
        for (Hero hero: draft.draftResult) {
            hero.enterArena(hero.getDraftChoice()-1, arena);
            hero.removeFromDraft();
        }
        HeroTeam friends = new HeroTeam(1, draft.draftResult, 1);
        HeroTeam enemies = DraftBuilder.getRandomEnemyTeam(arena);
        arena.setTeams(friends, enemies);
        this.activeScene = arena;
    }

    public void joinTeamBuilding() {
        TeamBuilder teamBuilder = new TeamBuilder(this.engine);
        this.activeScene = teamBuilder;
    }

    private void checkEndOfDraft() {
        if (this.activeScene instanceof Draft) {
            if (((Draft) this.activeScene).finished) {
                joinArena((Draft) this.activeScene);
            }
        }
        if (this.activeScene instanceof PvpDraft) {
            if (((PvpDraft) this.activeScene).finished) {
                joinTeamBuilding();
            }
        }
        if (this.activeScene instanceof TeamBuilder) {
            if (((TeamBuilder) this.activeScene).finished) {
                joinArena(this.engine.memory);
            }
        }
        if (this.activeScene instanceof Arena) {
            if (((Arena) this.activeScene).finished) {
//                joinDraft(this.engine.memory);
            }
        }
    }

    public int[] render(){

        return this.activeScene.render();
    }
    public void update(int frame) {
        this.activeScene.update(frame);
        checkEndOfDraft();
    }
}