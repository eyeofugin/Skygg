package game.entities;

import framework.states.Arena;
import game.entities.individuals.angelguy.H_AngelGuy;
import game.entities.individuals.battleaxe.H_BattleAxe;
import game.entities.individuals.burner.H_Burner;
import game.entities.individuals.darkmage.H_DarkMage;
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
import game.entities.individuals.thewizard.H_TheWizard;
import game.objects.equipments.SimpleBow;
import game.objects.equipments.SimpleDagger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DraftBuilder {

    public static Hero getRandomBackLiner(List<Hero> exclusions) {
        return getHeroFromClassList(new ArrayList<>(List.of(
                H_Burner.class,
                H_DarkMage.class,
                H_DivineMage.class,
                H_Sniper.class,
                H_TheHealer.class,
                H_TheWizard.class)), exclusions);
    }
    public static Hero getRandomMiddleLiner(List<Hero> exclusions) {

        Hero hero = getHeroFromClassList(new ArrayList<>(List.of(
                H_DualPistol.class,
                H_Duelist.class,
                H_FireDancer.class,
                H_Rifle.class,
                H_Phoenixguy.class)), exclusions);

        SimpleDagger simpleDagger = new SimpleDagger();
        simpleDagger.equipToHero(hero);
        SimpleBow simpleBow = new SimpleBow();
        simpleBow.equipToHero(hero);
        return hero;
    }
    public static Hero getRandomFrontLiner(List<Hero> exclusions) {
        return getHeroFromClassList(new ArrayList<>(List.of(
                H_BattleAxe.class,
                H_DragonBreather.class,
                H_Longsword.class,
                H_Paladin.class,
                H_AngelGuy.class)), exclusions);
    }
    public static Hero getRandomFlexPick(List<Hero> exclusions) {
        return getHeroFromClassList(new ArrayList<>(List.of(
                H_BattleAxe.class,
                H_DragonBreather.class,
                H_Longsword.class,
                H_Paladin.class,
                H_AngelGuy.class,
                H_Burner.class,
                H_DarkMage.class,
                H_DivineMage.class,
                H_Sniper.class,
                H_TheHealer.class,
                H_TheWizard.class,
                H_DualPistol.class,
                H_Duelist.class,
                H_FireDancer.class,
                H_Rifle.class,
                H_Phoenixguy.class)), exclusions);
    }
    public static Hero getHeroFromClassList(List<Class<? extends Hero>> availClasses, List<Hero> exclusions) {

        exclusions.stream().map(Hero::getClass).forEach(c -> availClasses.removeIf(hc -> hc == c));
        if (availClasses.isEmpty()) {
            return null;
        }
        Class<? extends Hero> heroClass = availClasses.get(new Random().nextInt(availClasses.size()));
        try {
            return heroClass.getDeclaredConstructor(new Class[0]).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HeroTeam getRandomEnemyTeam(Arena arena) {
        Hero[] heroes = new Hero[3];
        heroes[0] = getRandomFrontLiner(new ArrayList<>());
        heroes[0].enterArena(4, arena);
        heroes[1] = getRandomMiddleLiner(new ArrayList<>());
        heroes[1].enterArena(5, arena);
        heroes[2] = getRandomBackLiner(new ArrayList<>());
        heroes[2].enterArena(6, arena);

        return new HeroTeam(-1, heroes, 2);
    }
}
