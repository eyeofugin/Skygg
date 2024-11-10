package game.entities.individuals.angelguy;

import game.entities.Animator;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;

public class H_AngelGuy extends Hero {

    public H_AngelGuy() {

        super("Angel Guy");
        this.initBasePath("angelguy");
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
                new S_SpearOfLight(this),
                new S_Reengage(this),
                new S_PiercingLight(this),
                new S_HolyShield(this),
                new S_LightSpikes(this)
        };
    }

    @Override
    protected void initStats() {

        //StatStats
        this.stats.put(Stat.MAGIC, 10);
        this.stats.put(Stat.FORCE, 10);
        this.stats.put(Stat.STAMINA, 10);
        this.stats.put(Stat.ENDURANCE, 10);
        this.stats.put(Stat.FINESSE, 1);
        this.stats.put(Stat.SPEED, 9);
        //ResourceStats
        this.stats.put(Stat.LIFE, 20);
        this.stats.put(Stat.CURRENT_LIFE, 20);
        this.stats.put(Stat.LIFE_REGAIN, 2);

        this.stats.put(Stat.FAITH, 18);
    }
}
