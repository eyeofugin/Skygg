package game.entities.individuals.burner;

import game.entities.Animator;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.genericskills.S_Skip;

public class H_Burner extends Hero {

    public final static String NAME = "Burner";
    public H_Burner() {
        super(NAME);
        this.initBasePath("burner");
        this.secondaryResource = Stat.FAITH;
        this.initAnimator();
        this.initSkills();
        this.initStats();
        this.setLevel(1);
        this.effectiveRange = 3;
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
                new S_Fireball(this),
                new S_SpreadingFlames(this),
                new S_FlameAbsorption(this),
                new S_Heat(this),
//                new S_TwinFlames(this)
                new S_Skip(this)
        };
    }
}
