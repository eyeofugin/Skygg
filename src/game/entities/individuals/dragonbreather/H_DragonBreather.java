package game.entities.individuals.dragonbreather;

import game.entities.Animator;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.changeeffects.effects.AxeSwingCounter;

public class H_DragonBreather extends Hero {

    protected H_DragonBreather() {
        super("Dragon Breather");
        this.initBasePath("dragonbreather");
        this.secondaryResource = Stat.MANA;
        initAnimator();
        initSkills();
        initStats();
    }

    @Override
    protected void initAnimator() {
        this.anim = new Animator();
        anim.width = 64;
        anim.height = 64;

        anim.setupAnimation(this.basePath + "/res/sprites/idle_w.png", "idle", new int[]{40,80});
        anim.setupAnimation(this.basePath + "/res/sprites/damaged_w.png", "damaged", new int[]{3,6,9,12});

        anim.defaultAnim = "idle";
        anim.currentAnim = anim.defaultAnim;
        anim.onLoop = true;
    }

    @Override
    protected void initSkills() {
        this.skills = new Skill[] {
                new S_AxeSwing(this),
                new S_DragonBreath(this),
                new S_BlazingSkin(this),
                new S_Heat(this),
                new S_InnerFire(this)
        };
    }

    @Override
    protected void initStats() {
        //StatStats
        this.stats.put(Stat.MAGIC, 4);
        this.stats.put(Stat.FORCE, 10);
        this.stats.put(Stat.STAMINA, 14);
        this.stats.put(Stat.ENDURANCE, 18);
        this.stats.put(Stat.FINESSE, 1);
        this.stats.put(Stat.ACCURACY, 100);
        this.stats.put(Stat.EVASION, 0);
        this.stats.put(Stat.SPEED, 4);

        //ResourceStats
        this.stats.put(Stat.LIFE, 35);
        this.stats.put(Stat.CURRENT_LIFE, 35);
        this.stats.put(Stat.LIFE_REGAIN, 2);

        this.stats.put(Stat.FAITH, 0);
        this.stats.put(Stat.CURRENT_FAITH, 0);

        this.stats.put(Stat.MANA, 15);
        this.stats.put(Stat.CURRENT_MANA, 15);
        this.stats.put(Stat.MANA_REGAIN, 2);

        this.stats.put(Stat.MAX_ACTION, 1);
        this.stats.put(Stat.CURRENT_ACTION, 1);

        this.stats.put(Stat.CRIT_CHANCE, 0);
    }
}
