package game.entities.individuals.paladin;

import game.entities.Animator;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;

public class H_Paladin extends Hero {

    public H_Paladin() {
        super("Paladin");
        this.initBasePath("paladin");
        this.secondaryResource = Stat.FAITH;
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
                new S_ShatteringSwing(this),
                new S_QuickPrayer(this)
        };
        this.tactical = new Skill[]{
                new S_LightBlast(this), new S_LightPillar(this),
                new S_ShieldAssault(this), new S_FierceGlow(this)
        };
        this.ult = new S_HoliestShield(this);
    }
}
