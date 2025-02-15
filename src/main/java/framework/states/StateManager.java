package framework.states;

import framework.Engine;
import framework.graphics.GUIElement;
import game.entities.DraftBuilder;
import game.entities.Hero;
import game.entities.HeroTeam;
import game.entities.individuals.angelguy.H_AngelGuy;
import game.entities.individuals.divinemage.H_DivineMage;
import game.entities.individuals.dragonbreather.H_DragonBreather;
import game.entities.individuals.dualpistol.H_DualPistol;
import game.entities.individuals.duelist.H_Duelist;
import game.entities.individuals.longsword.H_Longsword;
import game.entities.individuals.rifle.H_Rifle;
import game.entities.individuals.sniper.H_Sniper;
import game.objects.equipments.SimpleBow;
import game.objects.equipments.SimpleDagger;

import java.util.List;

public class StateManager {

    public Engine e;
    private GUIElement activeScene;

    public StateManager(Engine e) {
        this.e = e;
//        Arena arena = new Arena(e, false);
//        H_Sniper hero0 = new H_Sniper();
//        hero0.enterArena(0, arena);
//
//        H_Rifle hero1 = new H_Rifle();
//        hero1.enterArena( 1, arena);
//
//        H_AngelGuy hero3 = new H_AngelGuy();
//        hero3.enterArena(2, arena);
//
//        H_Longsword hero2 = new H_Longsword();
//        hero2.enterArena(3, arena);
//
//        SimpleDagger simpleDagger = new SimpleDagger();
//        simpleDagger.equipToHero(hero0);
//
//        SimpleBow simpleBow = new SimpleBow();
//        simpleBow.equipToHero(hero0);
//
//
//        H_DragonBreather dummy1 = new H_DragonBreather();
//        dummy1.enterArena(4, arena);
//        H_Duelist dummy2 = new H_Duelist();
//        dummy2.enterArena( 5, arena);
//        H_DualPistol dummy3 = new H_DualPistol();
//        dummy3.enterArena( 6, arena);
//        H_DivineMage dummy4 = new H_DivineMage();
//        dummy4.enterArena( 7, arena);
//
//        HeroTeam friends = new HeroTeam(1,
//                new Hero[]{
//                        hero0,
//                        hero1,
//                        hero2,
//                        hero3}, 1);
//        HeroTeam enemies = new HeroTeam(-1,
//                new Hero[]{dummy1,dummy2,dummy3,dummy4}, 2);
//        arena.setTeams(friends, enemies);
//
//        activeScene = arena;
        Draft draft = new Draft(e);
        draft.setActive(true);
        this.activeScene = draft;

    }

    public void joinArena(Draft draft) {
        Arena arena = new Arena(this.e, false);
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

    private void checkEndOfDraft() {
        if (this.activeScene instanceof Draft) {
            if (((Draft) this.activeScene).finished) {
                joinArena((Draft) this.activeScene);
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