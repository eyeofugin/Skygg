package game.entities.individuals.divinemage;

import game.entities.Animator;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.genericskills.S_Skip;

public class H_DivineMage extends Hero {

    public H_DivineMage() {
        super("Divine Mage");
        this.initBasePath("divinemage");
        this.secondaryResource = Stat.FAITH;
        initAnimator();
        initSkills();
        this.initStats();
        setLevel(1);
        this.effectiveRange = 4;
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
                new S_Prayer(this), new S_Praise(this)
        };
        this.tactical = new Skill[]{
                new S_SummonTheLight(this), new S_Invincibility(this),
                new S_HolyWords(this), new S_ShieldRay(this)
        };
        this.ult = new S_HealingGleam(this);
        randomizeSkills();
    }
}
