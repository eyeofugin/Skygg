package game.entities.individuals.duelist;

import game.entities.Animator;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;

public class H_Duelist extends Hero {

    public H_Duelist() {
        super("Duelist");
        this.initBasePath("duelist");
        initAnimator();
        initSkills();
        initStats();
    }

    @Override
    protected void initAnimator() {
        this.anim = new Animator();
        anim.width = 64;
        anim.height = 64;

        anim.setupAnimation(this.basePath + "/sprites/idle_w.png", "idle", new int[]{40,80});
        anim.setupAnimation(this.basePath + "/sprites/damaged_w.png", "damaged", new int[]{3,6,9,12});

        anim.defaultAnim = "idle";
        anim.currentAnim = anim.defaultAnim;
        anim.onLoop = true;
    }

    @Override
    protected void initSkills() {
        this.skills = new Skill[]{
                new S_SwiftStrike(this),
                new S_Mobilize(this),
                new S_Lifesteal(this),
                new S_AllOut(this),
                new S_SwirlingBlades(this)
        };
    }

    @Override
    protected void initStats() {
        //StatStats
        this.stats.put(Stat.MAGIC, 1);
        this.stats.put(Stat.FORCE, 3);
        this.stats.put(Stat.STAMINA, 10);
        this.stats.put(Stat.ENDURANCE, 10);
        this.stats.put(Stat.FINESSE, 10);
        this.stats.put(Stat.ACCURACY, 100);
        this.stats.put(Stat.SPEED, 9);

        //ResourceStats
        this.stats.put(Stat.LIFE, 25);
        this.stats.put(Stat.CURRENT_LIFE, 25);
        this.stats.put(Stat.LIFE_REGAIN, 1);

        this.stats.put(Stat.CRIT_CHANCE, 5);
    }
}
