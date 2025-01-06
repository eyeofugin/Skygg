package game.entities.individuals.longsword;

import game.entities.Animator;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.changeeffects.statusinflictions.Taunted;
import game.skills.genericskills.S_Skip;

public class H_Longsword extends Hero {

    public H_Longsword() {
        super("Longsword");
        this.initBasePath("longsword");
        initAnimator();
        initSkills();
        this.initStats();
        setLevel(1);
        this.effectiveRange = 2;
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
        this.primary = new Skill[]{
                new S_Stab(this), new S_Swing(this)
        };
        this.tactical = new Skill[]{
                new S_Challenge(this),
                new S_Taunt(this),
                new S_Cover(this),
                new S_Steadfast(this)
        };
        this.ult = new S_SupremeDefense(this);
        randomizeSkills();
    }
}
