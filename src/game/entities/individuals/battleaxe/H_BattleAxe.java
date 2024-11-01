package game.entities.individuals.battleaxe;

import game.entities.Animator;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;

public class H_BattleAxe extends Hero {

    public H_BattleAxe(String name) {
        super("Battle Axe");
        this.initBasePath("battleaxe");
        initAnimator();
        initSkills();
        initStats();
    }

    @Override
    protected void initAnimator() {
        this.anim = new Animator();
        anim.width = 64;
        anim.height = 64;

        anim.setupAnimation(this.basePath + "/res/sprites/idle_w.png", "idle", new int[]{40,80});
        anim.setupAnimation(this.basePath + "/res/sprites/damaged_w.png", "damaged", new int[]{3,6,9,12});

        anim.defaultAnim = "idle";
        anim.currentAnim = anim.defaultAnim;
        anim.onLoop = true;
    }

    @Override
    protected void initSkills() {
        this.skills = new Skill[]{
                new S_AwesomeAxe(this),
                new S_WideSwing(this),
                new S_Headsmash(this),
                new S_Kick(this),
                new S_Bloodlust(this)
        };
    }

    @Override
    protected void initStats() {
        //StatStats
        this.stats.put(Stat.MAGIC, 1);
        this.stats.put(Stat.FORCE, 17);
        this.stats.put(Stat.STAMINA, 12);
        this.stats.put(Stat.ENDURANCE, 11);
        this.stats.put(Stat.FINESSE, 2);
        this.stats.put(Stat.SPEED, 3);

        //ResourceStats
        this.stats.put(Stat.LIFE, 32);
        this.stats.put(Stat.CURRENT_LIFE, 32);
        this.stats.put(Stat.LIFE_REGAIN, 2);
    }
}
