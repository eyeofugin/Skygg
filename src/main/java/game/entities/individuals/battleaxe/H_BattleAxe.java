package game.entities.individuals.battleaxe;

import game.entities.Animator;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.genericskills.S_Skip;

public class H_BattleAxe extends Hero {

    public H_BattleAxe() {
        super("Battle Axe");
        this.initBasePath("battleaxe");
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

        anim.defaultAnim = "idle";
        anim.currentAnim = anim.defaultAnim;
        anim.onLoop = true;
    }

    @Override
    protected void initSkills() {
        this.skills = new Skill[]{
                new S_AwesomeAxe(this),
//                new S_WideSwing(this),
                new S_Headsmash(this),
                new S_Kick(this),
                new S_Bloodlust(this),
                new S_Skip(this)
        };
    }
}
