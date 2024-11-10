package game.entities.individuals.paladin;

import game.entities.Animator;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;

public class H_Paladin extends Hero {

    public H_Paladin() {
        super("Paladin");
        this.initBasePath("paladin");
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
        this.skills = new Skill[]{
                new S_RighteousHammer(this),
                new S_LightBlast(this),
                new S_DivineArmor(this),
                new S_ShiningShield(this),
                new S_ShatteringSwing(this)
        };
    }

    @Override
    protected void initStats() {
        //StatStats
        this.stats.put(Stat.MAGIC, 3);
        this.stats.put(Stat.FORCE, 7);
        this.stats.put(Stat.STAMINA, 12);
        this.stats.put(Stat.ENDURANCE, 19);
        this.stats.put(Stat.FINESSE, 1);
        this.stats.put(Stat.ACCURACY, 100);
        this.stats.put(Stat.SPEED, 2);

        //ResourceStats
        this.stats.put(Stat.LIFE, 45);
        this.stats.put(Stat.CURRENT_LIFE, 45);
        this.stats.put(Stat.LIFE_REGAIN, 4);

        this.stats.put(Stat.FAITH, 10);
        this.stats.put(Stat.CURRENT_FAITH, 0);
    }
}
