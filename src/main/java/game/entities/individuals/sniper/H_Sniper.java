package game.entities.individuals.sniper;

import game.entities.Animator;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.genericskills.S_Skip;

public class H_Sniper extends Hero {
    public H_Sniper() {
        super("Sniper");
        this.initBasePath("sniper");
        initAnimator();
        initSkills();
        initStats();
        setLevel(1);
        effectiveRange = 4;
    }

    @Override
    protected void initAnimator() {
        this.anim = new Animator();
        anim.width = 64;
        anim.height = 64;

        anim.setupAnimation(this.basePath + "/sprites/idle_w.png", "idle", new int[]{40,80});
        anim.setupAnimation(this.basePath + "/sprites/damaged_w.png", "damaged", new int[]{3,6,9,12});

        anim.setDefaultAnim("idle");
        anim.currentAnim = anim.getDefaultAnim();
        anim.onLoop = true;
    }

    @Override
    protected void initSkills() {
        this.skills = new Skill[] {
                new S_ChemShot(this),
                new S_BlindingShot(this),
                new S_GotYourBack(this),
//                new S_Cloaked(this),
                new S_SmokeGrenade(this),
                new S_Skip(this)
        };
    }
}
