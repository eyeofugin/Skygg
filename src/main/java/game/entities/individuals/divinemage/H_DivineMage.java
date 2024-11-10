package game.entities.individuals.divinemage;

import game.entities.Animator;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;

public class H_DivineMage extends Hero {

    public H_DivineMage() {
        super("Divine Mage");
        this.initBasePath("divinemage");
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

        anim.setupAnimation(this.basePath + "/sprites/idle_w.png", "idle", new int[]{40,80});
        anim.setupAnimation(this.basePath + "/sprites/damaged_w.png", "damaged", new int[]{3,6,9,12});

        anim.defaultAnim = "idle";
        anim.currentAnim = anim.defaultAnim;
        anim.onLoop = true;
    }

    @Override
    protected void initSkills() {
        this.skills = new Skill[] {
                new S_Prayer(this),
                new S_ShieldRay(this),
                new S_HolyWords(this),
                new S_HealingGleam(this),
                new S_Immunity(this)
        };
    }

    @Override
    protected void initStats() {
        //StatStats
        this.stats.put(Stat.MAGIC, 12);
        this.stats.put(Stat.FORCE, 1);
        this.stats.put(Stat.STAMINA, 3);
        this.stats.put(Stat.ENDURANCE, 4);
        this.stats.put(Stat.FINESSE, 1);
        this.stats.put(Stat.SPEED, 1);

        //ResourceStats
        this.stats.put(Stat.LIFE, 10);
        this.stats.put(Stat.CURRENT_LIFE, 10);
        this.stats.put(Stat.LIFE_REGAIN, 2);

        this.stats.put(Stat.FAITH, 18);
    }
}
