package game.entities.individuals.longsword;

import game.entities.Animator;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
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
        this.skills = new Skill[] {
                new S_Swing(this),
                new S_Stab(this),
                new S_Steadfast(this),
//                new S_SupremeDefense(this),
                new S_Taunt(this),
                new S_Skip(this)
        };
    }
}
