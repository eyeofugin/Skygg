package game.entities.individuals.dragonbreather;

import game.entities.Animator;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.genericskills.S_Skip;

public class H_DragonBreather extends Hero {

    public H_DragonBreather() {
        super("Dragon Breather");
        this.initBasePath("dragonbreather");
        this.secondaryResource = Stat.MANA;
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
                new S_AxeSwing(this),
                new S_DragonBreath(this),
                new S_BlazingSkin(this),
                new S_Heat(this),
//                new S_InnerFire(this)
                new S_Skip(this)
        };
    }
}
