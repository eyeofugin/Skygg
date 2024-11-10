package game.entities.individuals.thewizard;

import game.entities.Animator;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;

public class H_TheWizard extends Hero {
    public H_TheWizard() {
        super("The Wizard");
        this.initBasePath("thewizard");
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

        anim.setupAnimation(this.basePath + "/sprites/idle_w.png", "idle", new int[]{40,80});
        anim.setupAnimation(this.basePath + "/sprites/damaged_w.png", "damaged", new int[]{3,6,9,12});

        anim.defaultAnim = "idle";
        anim.currentAnim = anim.defaultAnim;
        anim.onLoop = true;
    }

    @Override
    protected void initSkills() {
        this.skills = new Skill[]{
                new S_MysticShot(this),
                new S_ChainLightning(this),
                new S_Dispel(this),
                new S_AetherStep(this),
                new S_ArcaneBombardment(this)
        };
    }

    @Override
    protected void initStats() {
        //StatStats
        this.stats.put(Stat.MAGIC, 19);
        this.stats.put(Stat.FORCE, 2);
        this.stats.put(Stat.STAMINA, 2);
        this.stats.put(Stat.ENDURANCE, 3);
        this.stats.put(Stat.FINESSE, 1);
        this.stats.put(Stat.ACCURACY, 100);
        this.stats.put(Stat.SPEED, 11);

        //ResourceStats
        this.stats.put(Stat.LIFE, 10);
        this.stats.put(Stat.CURRENT_LIFE, 10);
        this.stats.put(Stat.LIFE_REGAIN, 1);

        this.stats.put(Stat.MANA, 25);
        this.stats.put(Stat.CURRENT_MANA, 25);
        this.stats.put(Stat.MANA_REGAIN, 2);
    }
}
