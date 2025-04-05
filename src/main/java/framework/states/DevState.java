package framework.states;

import framework.Engine;
import framework.graphics.GUIElement;
import framework.graphics.elements.SkillElement;
import framework.graphics.elements.SkillInfo;
import framework.graphics.elements.StatField;
import framework.graphics.text.Color;
import game.entities.DraftBuilder;
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
import game.skills.Skill;
import game.skills.Stat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DevState extends GUIElement {

    public final Engine engine;
    List<Hero> heroList = new ArrayList<>();

    Hero hero;
    List<Skill> skillList;
    private StatField stats;

    private int x = 0;
    private int y = 0;

    public DevState(Engine engine) {
        super(Engine.X, Engine.Y);
        this.engine = engine;
        List<Class<? extends Hero>> availableHeroes = DraftBuilder.getAllHeroes();
        for ( Class<? extends Hero> clazz : availableHeroes) {
            try {
                this.heroList.add(clazz.getConstructor().newInstance());
            } catch (Exception e) {

            }
        }
        this.setHero(x);
    }
    @Override
    public void update(int frame) {
        if (active) {
            updateKeys();
        }
    }
    private void updateKeys() {
        if (engine.keyB._leftPressed) {
            this.x = this.x == 0 ? this.heroList.size()-1 : this.x - 1;
            this.setHero(x);
            this.y = 0;
        }
        if (engine.keyB._rightPressed) {
            this.x = this.x == this.heroList.size()-1 ? 0 : this.x + 1;
            this.setHero(x);
            this.y = 0;
        }
        if (engine.keyB._upPressed) {
            this.y = this.y == 0 ? 6 : this.y - 1;
        }
        if (engine.keyB._downPressed) {
            this.y = this.y == 6 ? 0 : this.y + 1;
        }
    }

    private void setHero(int index) {
        this.hero = this.heroList.get(index);
        this.skillList = new ArrayList<>();
        this.skillList.addAll(Arrays.stream(this.hero.getPrimary()).toList());
        this.skillList.addAll(Arrays.stream(this.hero.getTactical()).toList());
        this.skillList.add(this.hero.getUlt());
        Stat[] lArray = new Stat[]{Stat.LIFE, Stat.LIFE_REGAIN, Stat.MANA, Stat.MANA_REGAIN, Stat.FAITH, Stat.HALO, Stat.SHIELD};
        Stat[] rArray = new Stat[]{Stat.MAGIC, Stat.POWER, Stat.STAMINA, Stat.ENDURANCE, Stat.SPEED, Stat.ACCURACY, Stat.EVASION, Stat.CRIT_CHANCE, Stat.LETHALITY};
        this.stats = new StatField(this.hero, lArray, rArray);
    }

    @Override
    public int[] render() {
        background(Color.BLACK);
        renderHero();
        renderAbilities();
        renderSkillInfo();
        renderStats();
        return this.pixels;
    }

    private void renderHero() {
        if (this.hero == null) {
            return;
        }
        fillWithGraphicsSize(10, 10, this.hero.getWidth(), this.hero.getHeight(), this.hero.render(), false);
    }

    private void renderStats() {
        if (this.hero == null) {
            return;
        }
        fillWithGraphicsSize(200, 10, this.stats.getWidth(), this.stats.getHeight(), this.stats.render(), false);
    }

    private void renderAbilities() {
        if (this.hero == null || this.skillList.isEmpty()) {
            return;
        }
        int y = 200;
        for (int i = 0; i < this.skillList.size(); i++) {
            Skill skill = this.skillList.get(i);
            SkillElement element = new SkillElement(skill);
            fillWithGraphicsSize(10, y, SkillElement._WIDTH, SkillElement._HEIGHT, element.render(), true, this.y == i ? Color.WHITE : Color.VOID);
            y += SkillElement._HEIGHT + 2;

        }
    }

    private void renderSkillInfo() {
        SkillInfo info = new SkillInfo(this.skillList.get(this.y));
        fillWithGraphicsSize(200, 200, info.getWidth(), info.getHeight(), info.render(), false);
    }
}
