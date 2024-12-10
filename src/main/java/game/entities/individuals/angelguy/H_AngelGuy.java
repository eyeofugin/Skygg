package game.entities.individuals.angelguy;

import game.entities.Animator;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.genericskills.S_Skip;

public class H_AngelGuy extends Hero {

    public static final String NAME = "Angel Guy";
    public H_AngelGuy() {

        super(NAME);
        this.initBasePath("angelguy");
        this.secondaryResource = Stat.FAITH;
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
        this.skills = new Skill[] {
                new S_SpearOfLight(this),
                new S_Reengage(this),
//                new S_PiercingLight(this),
                new S_HolyShield(this),
                new S_LightSpikes(this),
                new S_Skip(this)
        };
    }
}
