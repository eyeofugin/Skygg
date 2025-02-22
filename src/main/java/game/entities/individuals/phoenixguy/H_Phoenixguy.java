package game.entities.individuals.phoenixguy;

import game.entities.Animator;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.genericskills.S_Skip;

public class H_Phoenixguy extends Hero {

    public H_Phoenixguy() {
        super("Phoenix Guy");
        this.initBasePath("phoenixguy");
        this.secondaryResource = Stat.FAITH;
        initAnimator();
        initSkills();
        this.initStats();
        setLevel(1);
        this.effectiveRange = 3;
    }

    @Override
    protected void initAnimator() {
        this.anim = new Animator();
        anim.width = 64;
        anim.height = 64;

        anim.setupAnimation(this.basePath + "/sprites/idle_w.png", "idle", new int[]{40,80});
        anim.setupAnimation(this.basePath + "/sprites/damaged_w.png", "damaged", new int[]{3,6,9,12});
        anim.setupAnimation(this.basePath + "/sprites/action_w.png", "action_w", new int[]{15, 30, 45});

        anim.setDefaultAnim("idle");
        anim.currentAnim = anim.getDefaultAnim();
        anim.onLoop = true;
    }

    @Override
    protected void initSkills() {
        this.primary = new Skill[]{
                new S_Spark(this), new S_Hotwings(this)
        };
        this.tactical = new Skill[]{
                new S_Fireblast(this), new S_AshenHeat(this),
                new S_HonorTheFirstFlame(this), new S_Combustion(this)
        };
        this.ult = new S_PhoenixFlames(this);
        randomizeSkills();
    }
}
