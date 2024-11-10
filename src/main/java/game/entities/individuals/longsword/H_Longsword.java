package game.entities.individuals.longsword;

import game.entities.Animator;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;

public class H_Longsword extends Hero {

    public H_Longsword() {
        super("Longsword");
        this.initBasePath("longsword");
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
        this.skills = new Skill[] {
                new S_Swing(this),
                new S_Stab(this),
                new S_Steadfast(this),
                new S_SupremeDefense(this),
                new S_Taunt(this)
        };
    }

    @Override
    protected void initStats() {
        this.stats.put(Stat.MAGIC, 1);
        this.stats.put(Stat.FORCE, 4);
        this.stats.put(Stat.STAMINA, 16);
        this.stats.put(Stat.ENDURANCE, 13);
        this.stats.put(Stat.FINESSE, 4);
        this.stats.put(Stat.SPEED, 2);

        //ResourceStats
        this.stats.put(Stat.LIFE, 43);
        this.stats.put(Stat.CURRENT_LIFE, 43);
        this.stats.put(Stat.LIFE_REGAIN, 3);
    }
}
