package game.entities.individuals.phoenixguy;

import game.entities.Animator;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;

public class H_Phoenixguy extends Hero {

    public H_Phoenixguy() {
        super("Phoenix Guy");
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

        anim.setupAnimation("res/sprites/dev/idle_w.png", "idle", new int[]{40,80});
        anim.setupAnimation("res/sprites/dev/damaged_w.png", "damaged", new int[]{3,6,9,12});

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
                new S_HonorTheFirstFlame(this)
        };
    }

    @Override
    protected void initStats() {
        //ResourceStats
        this.stats.put(Stat.LIFE, 30);
        this.stats.put(Stat.CURRENT_LIFE, 30);
        this.stats.put(Stat.LIFE_REGAIN, 2);

        this.stats.put(Stat.FAITH, 40);
        this.stats.put(Stat.CURRENT_FAITH, 0);

        this.stats.put(Stat.MANA, 0);
        this.stats.put(Stat.CURRENT_MANA, 0);

        this.stats.put(Stat.MAX_ACTION, 1);
        this.stats.put(Stat.CURRENT_ACTION, 1);

        //StatStats
        this.stats.put(Stat.MAGIC, 10);
        this.stats.put(Stat.FORCE, 10);
        this.stats.put(Stat.ENDURANCE, 10);
        this.stats.put(Stat.FINESSE, 10);
        this.stats.put(Stat.ACCURACY, 100);
        this.stats.put(Stat.EVASION, 0);
        this.stats.put(Stat.SPEED, 10);

        this.stats.put(Stat.CRIT_CHANCE, 0);

        //DefenseStats
        this.stats.put(Stat.NORMAL, 2);
        this.stats.put(Stat.HEAT, 2);
        this.stats.put(Stat.DIVINE, 2);
        this.stats.put(Stat.DARK, 2);
        this.stats.put(Stat.CHEMICAL, 2);
        this.stats.put(Stat.ARCANE, 2);
        this.stats.put(Stat.ELDRITCH, 2);
        this.stats.put(Stat.COSMIC, 2);
    }
    @Override
    public void update(int frame) {
        this.anim.animate();
    }
}
