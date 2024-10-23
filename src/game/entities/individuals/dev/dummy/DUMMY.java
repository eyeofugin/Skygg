package game.entities.individuals.dev.dummy;

import game.entities.Animator;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.backgroundskills.AllyHeal;

public class DUMMY extends Hero {
    public int colorIndex = 0;

    public DUMMY(int colorIndex) {
        super("DUMMY");
        this.initBasePath("dev/dummy");
        this.colorIndex = colorIndex;
        initAnimator();
        initStats();
        initSkills();
    }

    protected void initStats() {
        this.stats.put(Stat.LIFE, 20);
        this.stats.put(Stat.CURRENT_LIFE, 20);
        this.stats.put(Stat.LIFE_REGAIN, 2);

        this.stats.put(Stat.MANA, 40);
        this.stats.put(Stat.CURRENT_MANA, 40);
        this.stats.put(Stat.MANA_REGAIN, 2);

        this.stats.put(Stat.FAITH, 40);
        this.stats.put(Stat.CURRENT_FAITH, 40);

        this.stats.put(Stat.MAX_ACTION, 1);
        this.stats.put(Stat.CURRENT_ACTION, 1);

        //
        this.stats.put(Stat.MAGIC, 10);
        this.stats.put(Stat.FORCE, 10);
        this.stats.put(Stat.STAMINA, 10);
        this.stats.put(Stat.ENDURANCE, 10);
        this.stats.put(Stat.FINESSE, 10);
        this.stats.put(Stat.ACCURACY, 100);
        this.stats.put(Stat.EVASION, 0);
        this.stats.put(Stat.SPEED, 1);

        this.stats.put(Stat.CRIT_CHANCE, 0);

    }

    @Override
    protected void initAnimator() {
        this.anim = new Animator();
        anim.width = 64;
        anim.height = 64;

        String idlePath = "";
        String damagedPath = "";
        switch (colorIndex) {
            case 1:
                idlePath = "res/sprites/enemies/blorgon/blorgon_idle.png";
                damagedPath = "res/sprites/enemies/blorgon/blorgon_damaged.png";
                break;
            case 2:
                idlePath = "res/sprites/enemies/blorgon_red/blorgon_idle.png";
                damagedPath = "res/sprites/enemies/blorgon_red/blorgon_damaged.png";
                break;
            case 3:
                idlePath = "res/sprites/enemies/blorgon_blue/blorgon_idle.png";
                damagedPath = "res/sprites/enemies/blorgon_blue/blorgon_damaged.png";
                break;
            case 4:
                idlePath = "res/sprites/enemies/blorgon_green/blorgon_idle.png";
                damagedPath = "res/sprites/enemies/blorgon_green/blorgon_damaged.png";
                break;
        }
        anim.setupAnimation(idlePath, "idle", new int[]{50,100,150,200});
        anim.setupAnimation(damagedPath, "damaged", new int[]{5,10,15,20});

        anim.defaultAnim = "idle";
        anim.currentAnim = anim.defaultAnim;
        anim.onLoop = true;
    }

    protected void initSkills() {
        this.skills = new Skill[] {
                new AllyHeal(this)
        };
    }
    @Override
    public void update(int frame) {
        this.anim.animate();
    }
}
