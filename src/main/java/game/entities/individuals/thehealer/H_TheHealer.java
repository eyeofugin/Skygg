package game.entities.individuals.thehealer;

import game.entities.Animator;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.genericskills.S_Skip;

public class H_TheHealer extends Hero {
    public H_TheHealer() {
        super("The Healer");
        this.initBasePath("thehealer");
        this.secondaryResource = Stat.MANA;
        initAnimator();
        initSkills();
        this.initStats();
        setLevel(1);
        effectiveRange = 4;
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
                new S_DivineRay(this), new S_LuxBomb(this)
        };
        this.tactical = new Skill[]{
                new S_HolyWords(this), new S_BlindingLight(this),
                new S_HolyLight(this), new S_Cleanse(this)
        };
        this.ult = new S_ImbueWithLight(this);
        randomizeSkills();
    }
}
