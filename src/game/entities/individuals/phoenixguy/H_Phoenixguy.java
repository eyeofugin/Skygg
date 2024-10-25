package game.entities.individuals.phoenixguy;

import game.entities.Animator;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;

public class H_Phoenixguy extends Hero {

    public H_Phoenixguy() {
        super("Phoenix Guy");
        this.initBasePath("phoenixguy");
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
                new S_Spark(this),
                new S_Fireblast(this),
                new S_PhoenixFlames(this),
                new S_HonorTheFirstFlame(this),
                new S_Combustion(this)
        };
    }

    @Override
    protected void initStats() {
        //StatStats
        this.stats.put(Stat.MAGIC, 18);
        this.stats.put(Stat.FORCE, 6);
        this.stats.put(Stat.STAMINA, 3);
        this.stats.put(Stat.ENDURANCE, 4);
        this.stats.put(Stat.FINESSE, 1);
        this.stats.put(Stat.ACCURACY, 100);
        this.stats.put(Stat.EVASION, 0);
        this.stats.put(Stat.SPEED, 10);

        //ResourceStats
        this.stats.put(Stat.LIFE, 15);
        this.stats.put(Stat.CURRENT_LIFE, 15);
        this.stats.put(Stat.LIFE_REGAIN, 1);

        this.stats.put(Stat.FAITH, 20);
        this.stats.put(Stat.CURRENT_FAITH, 0);

        this.stats.put(Stat.MANA, 0);
        this.stats.put(Stat.CURRENT_MANA, 0);

        this.stats.put(Stat.MAX_ACTION, 1);
        this.stats.put(Stat.CURRENT_ACTION, 1);



        this.stats.put(Stat.CRIT_CHANCE, 0);

        this.stats.put(Stat.SHIELD,0);
    }
}
