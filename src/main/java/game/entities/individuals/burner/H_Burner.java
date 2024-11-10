package game.entities.individuals.burner;

import game.entities.Animator;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;

public class H_Burner extends Hero {

    public H_Burner() {
        super("Burner");
        this.initBasePath("burner");
        this.secondaryResource = Stat.FAITH;
        this.initAnimator();
        this.initSkills();
        this.initStats();
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
        this.skills = new Skill[] {
                new S_Fireball(this),
                new S_SpreadingFlames(this),
                new S_FlameAbsorption(this),
                new S_Heat(this),
                new S_TwinFlames(this)
        };
    }

    @Override
    protected void initStats() {

        //StatStats
        this.stats.put(Stat.MAGIC, 15);
        this.stats.put(Stat.FORCE, 3);
        this.stats.put(Stat.STAMINA, 3);
        this.stats.put(Stat.ENDURANCE, 2);
        this.stats.put(Stat.FINESSE, 1);
        this.stats.put(Stat.SPEED, 13);

        this.stats.put(Stat.ACCURACY, 100);
        this.stats.put(Stat.EVASION, 0);

        //ResourceStats
        this.stats.put(Stat.LIFE, 13);
        this.stats.put(Stat.CURRENT_LIFE, 13);
        this.stats.put(Stat.LIFE_REGAIN, 1);

        this.stats.put(Stat.FAITH, 15);
    }
}
