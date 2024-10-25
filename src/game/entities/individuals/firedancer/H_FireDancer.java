package game.entities.individuals.firedancer;

import game.entities.Animator;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;

public class H_FireDancer extends Hero {
    public H_FireDancer() {
        super("Fire Dancer");
        this.initBasePath("firedancer");
        this.secondaryResource = Stat.FAITH;
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
                new S_Slash(this),
                new S_FlameLasso(this),
                new S_FlameDance(this),
                new S_RushOfHeat(this),
                new S_SingingBlades(this)
        };
    }

    @Override
    protected void initStats() {
        //StatStats
        this.stats.put(Stat.MAGIC, 9);
        this.stats.put(Stat.FORCE, 5);
        this.stats.put(Stat.STAMINA, 4);
        this.stats.put(Stat.ENDURANCE, 3);
        this.stats.put(Stat.FINESSE, 12);
        this.stats.put(Stat.SPEED, 16);
        this.stats.put(Stat.ACCURACY, 100);
        this.stats.put(Stat.EVASION, 0);

        //ResourceStats
        this.stats.put(Stat.LIFE, 15);
        this.stats.put(Stat.CURRENT_LIFE, 15);
        this.stats.put(Stat.LIFE_REGAIN, 1);

        this.stats.put(Stat.FAITH, 15);
        this.stats.put(Stat.CURRENT_FAITH, 0);

        this.stats.put(Stat.MANA, 0);
        this.stats.put(Stat.CURRENT_MANA, 0);

        this.stats.put(Stat.MAX_ACTION, 1);
        this.stats.put(Stat.CURRENT_ACTION, 1);

        this.stats.put(Stat.CRIT_CHANCE, 10);

        this.stats.put(Stat.SHIELD,0);
    }
}
