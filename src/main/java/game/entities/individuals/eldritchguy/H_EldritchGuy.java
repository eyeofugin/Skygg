package game.entities.individuals.eldritchguy;

import game.entities.Animator;
import game.entities.Hero;
import game.entities.individuals.duelist.S_AllOut;
import game.entities.individuals.duelist.S_DuelistDance;
import game.entities.individuals.duelist.S_GiveMeYourWorst;
import game.entities.individuals.duelist.S_Mobilize;
import game.entities.individuals.duelist.S_Reposte;
import game.entities.individuals.duelist.S_Slash;
import game.entities.individuals.duelist.S_SwirlingBlades;
import game.skills.Skill;

public class H_EldritchGuy extends Hero {

    public H_EldritchGuy() {
        super("Eldritch Guy");
        this.initBasePath("eldritchguy");
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
                new S_GraspOfTheAbyss(this), new S_VoidLeech(this)
        };
        this.tactical = new Skill[]{
                new S_VoidGraft(this), new S_UnnaturalDefenses(this),
                new S_TentacleGrab(this), new S_HorrificGlare(this)
        };
        this.ult = new S_UnleashEmptiness(this);
        randomizeSkills();
    }
}
