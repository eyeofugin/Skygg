package game.entities.individuals.rifle;

import game.entities.Animator;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.genericskills.S_Skip;

public class H_Rifle extends Hero {

    public H_Rifle() {
        super("Rifle");
        this.initBasePath("rifle");
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
                new S_Blasting(this),
                new S_UseTheScope(this),
                new S_Engage(this),
                new S_Retreat(this),
                new S_Skip(this)
//                new S_Barrage(this)
        };
    }
}
