package framework.states;

import framework.Engine;
import framework.graphics.GUIElement;
import game.entities.Hero;
import game.entities.HeroTeam;
import game.entities.individuals.angelguy.H_AngelGuy;
import game.entities.individuals.battleaxe.H_BattleAxe;
import game.entities.individuals.burner.H_Burner;
import game.entities.individuals.darkmage.H_DarkMage;
import game.entities.individuals.dev.dummy.DUMMY;
import game.entities.individuals.divinemage.H_DivineMage;
import game.entities.individuals.dragonbreather.H_DragonBreather;
import game.entities.individuals.dualpistol.H_DualPistol;
import game.entities.individuals.duelist.H_Duelist;
import game.entities.individuals.firedancer.H_FireDancer;
import game.entities.individuals.longsword.H_Longsword;
import game.entities.individuals.paladin.H_Paladin;
import game.entities.individuals.phoenixguy.H_Phoenixguy;
import game.entities.individuals.rifle.H_Rifle;
import game.entities.individuals.sniper.H_Sniper;
import game.entities.individuals.thehealer.H_TheHealer;
import game.objects.equipments.SimpleBow;
import game.objects.equipments.SimpleDagger;
import game.skills.Effect;
import game.skills.Stat;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.effects.DarkSecrets;
import game.skills.changeeffects.effects.RegenStop;
import game.skills.changeeffects.effects.SmokeScreen;
import game.skills.changeeffects.statusinflictions.Dazed;
import game.skills.changeeffects.statusinflictions.Injured;

import java.util.List;

public class StateManager {

    public Engine e;
    private GUIElement activeScene;

    public StateManager(Engine e) {
        this.e = e;
        Arena arena = new Arena(e, false);
        H_Sniper hero0 = new H_Sniper();
        hero0.enterArena(0, arena);

        H_Rifle hero1 = new H_Rifle();
        hero1.enterArena( 1, arena);

        H_AngelGuy hero3 = new H_AngelGuy();
        hero3.enterArena(2, arena);

        H_Longsword hero2 = new H_Longsword();
        hero2.enterArena(3, arena);

        SimpleDagger simpleDagger = new SimpleDagger();
        simpleDagger.equipToHero(hero0);

        SimpleBow simpleBow = new SimpleBow();
        simpleBow.equipToHero(hero0);


        H_DragonBreather dummy1 = new H_DragonBreather();
        dummy1.enterArena(4, arena);
        H_Duelist dummy2 = new H_Duelist();
        dummy2.enterArena( 5, arena);
        H_DualPistol dummy3 = new H_DualPistol();
        dummy3.enterArena( 6, arena);
        H_DivineMage dummy4 = new H_DivineMage();
        dummy4.enterArena( 7, arena);

        HeroTeam friends = new HeroTeam(1,
                new Hero[]{
                        hero0,
                        hero1,
                        hero2,
                        hero3}, 1);
        HeroTeam enemies = new HeroTeam(-1,
                new Hero[]{dummy1,dummy2,dummy3,dummy4}, 2);
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