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
        anim.setupAnimation(this.basePath + "/sprites/action_w.png", "action_w", new int[]{15, 30, 45});

        anim.setDefaultAnim("idle");
        anim.currentAnim = anim.getDefaultAnim();
        anim.onLoop = true;
    }

    @Override
    protected void initSkills() {
        this.primary = new Skill[]{
                new S_Chop(this),
                new S_BlazingCleaver(this)};
        this.tactical = new Skill[]{
                new S_HeatOfTheDragon(this),
                new S_DragonBreath(this),
                new S_InnerFire(this),
                new S_DragonScales(this)};
        this.ult = new S_BellowingRoar(this);
        this.randomizeSkills();
    }
}
