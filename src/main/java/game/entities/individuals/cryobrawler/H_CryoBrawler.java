package game.entities.individuals.cryobrawler;

import game.entities.Animator;
import game.entities.Hero;
import game.entities.individuals.eldritchguy.S_GraspOfTheAbyss;
import game.entities.individuals.eldritchguy.S_HorrificGlare;
import game.entities.individuals.eldritchguy.S_TentacleGrab;
import game.entities.individuals.eldritchguy.S_UnleashEmptiness;
import game.entities.individuals.eldritchguy.S_UnnaturalDefenses;
import game.entities.individuals.eldritchguy.S_VoidGraft;
import game.entities.individuals.eldritchguy.S_VoidLeech;
import game.skills.Skill;

public class H_CryoBrawler extends Hero {

    public H_CryoBrawler() {
        super("Cryo Brawler");
        this.initBasePath("cryobrawler");
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
