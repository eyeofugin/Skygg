package game.entities.individuals.thehealer;

import game.entities.Animator;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;

public class H_TheHealer extends Hero {
    public H_TheHealer() {
        super("The Healer");
        this.initBasePath("thehealer");
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
        this.skills = new Skill[] {
                new S_DivineRay(this),
                new S_HolyWords(this),
                new S_BlindingLight(this),
                new S_HolyLight(this),
                new S_ImbueWithLight(this)
        };
    }

    @Override
    protected void initStats() {

        this.stats.put(Stat.MAGIC, 12);
        this.stats.put(Stat.FORCE, 1);
        this.stats.put(Stat.STAMINA, 2);
        this.stats.put(Stat.ENDURANCE, 2);
        this.stats.put(Stat.FINESSE, 1);
        this.stats.put(Stat.SPEED, 11);

        //ResourceStats
        this.stats.put(Stat.LIFE, 9);
        this.stats.put(Stat.CURRENT_LIFE, 9);
        this.stats.put(Stat.LIFE_REGAIN, 1);

        this.stats.put(Stat.MANA, 18);
        this.stats.put(Stat.CURRENT_MANA, 18);
        this.stats.put(Stat.MANA_REGAIN, 5);
    }
}
