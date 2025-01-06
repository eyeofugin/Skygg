package framework.states;

import framework.graphics.GUIElement;
import game.entities.Hero;
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
import game.entities.individuals.thewizard.H_TheWizard;
import game.skills.Stat;

public class DevState extends GUIElement {

    public DevState() {

        H_AngelGuy angelGuy = new H_AngelGuy();

        H_BattleAxe battleAxe = new H_BattleAxe();
        H_Burner burner = new H_Burner();
        H_DarkMage darkMage = new H_DarkMage();
        H_DivineMage divineMage = new H_DivineMage();
        H_DragonBreather dragonBreather = new H_DragonBreather();
        H_DualPistol dualPistol = new H_DualPistol();
        H_Duelist duelist = new H_Duelist();
        H_FireDancer fireDancer = new H_FireDancer();
        H_Longsword longsword = new H_Longsword();
        H_Paladin paladin = new H_Paladin();
        H_Phoenixguy phoenixguy = new H_Phoenixguy();
        H_Rifle rifle = new H_Rifle();
        H_Sniper sniper = new H_Sniper();
        H_TheHealer theHealer = new H_TheHealer();
        H_TheWizard theWizard = new H_TheWizard();

        DUMMY dummy = new DUMMY(1);

        System.out.print("AngelGuy\t");
        singleTest(angelGuy, 0, dummy);
        singleTest(angelGuy, 2, dummy);
        singleTest(angelGuy, 4, dummy);
        System.out.println();

        System.out.println("BattleAxe\t");
        singleTest(battleAxe, 0, dummy);
        singleTest(battleAxe, 1, dummy);
        singleTest(battleAxe, 2, dummy);
        singleTest(battleAxe, 3, dummy);
        System.out.println();

    }
    private void singleTest(Hero hero, int index, Hero target) {
        int healthInit = target.getStat(Stat.CURRENT_LIFE);
        try {
            hero.devDMGTestSkill(index, target);
        } catch (Exception e) {

        }
        int dmgDone = healthInit - target.getStat(Stat.CURRENT_LIFE);
        System.out.print(dmgDone+"\t");
        target.getStats().put(Stat.CURRENT_LIFE, target.getStat(Stat.LIFE));
    }
}
