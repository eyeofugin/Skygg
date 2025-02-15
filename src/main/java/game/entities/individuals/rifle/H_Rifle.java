package game.entities.individuals.rifle;

import com.sun.jdi.Field;
import game.entities.Animator;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.genericskills.S_Skip;

public class H_Rifle extends Hero {

    public H_Rifle() {
        super("Crossbow");
        this.initBasePath("rifle");
        initAnimator();
        initSkills();
        this.initStats();
        setLevel(1);
        effectiveRange = 3;
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
                new S_PiercingBolt(this),
                new S_AnkleShot(this)
        };
        this.tactical = new Skill[]{
                new S_Mobilize(this),
                new S_UseTheScope(this),
                new S_DoubleShot(this),
                new S_FieldRations(this)
        };
        this.ult = new S_Barrage(this);
        randomizeSkills();
    }
}
