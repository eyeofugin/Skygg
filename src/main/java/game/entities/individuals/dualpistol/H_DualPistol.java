package game.entities.individuals.dualpistol;

import game.entities.Animator;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;

public class H_DualPistol extends Hero {

    public H_DualPistol() {
        super("Dual Pistol");
        this.initBasePath("dualpistol");
        initAnimator();
        initSkills();
        this.initStats();
        setLevel(1);
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
                new S_QuickShot(this),
                new S_DoubleShot(this),
                new S_FocusedFire(this),
                new S_Outmaneuver(this),
                new S_Roll(this)
        };
    }
}
