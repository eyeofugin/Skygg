package game.entities.individuals.angelguy;

import game.entities.Animator;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;

public class H_AngelGuy extends Hero {

    public static final String NAME = "Angel Guy";
    public H_AngelGuy() {

        super(NAME);
        this.initBasePath("angelguy");
        this.secondaryResource = Stat.HALO;
        this.initAnimator();
        this.initSkills();
        this.initStats();
        this.setLevel(1);
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
                new S_DeepThrust(this),
                new S_LightJavelin(this)};
        this.tactical = new Skill[] {
                new S_Reengage(this),
                new S_AngelicWings(this),
                new S_LightSpikes(this),
                new S_PiercingLight(this)};
        this.ult = new S_Halo(this);
        randomizeSkills();
    }
}
