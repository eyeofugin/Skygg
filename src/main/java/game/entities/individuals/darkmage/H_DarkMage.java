package game.entities.individuals.darkmage;

import game.entities.Animator;
import game.entities.Hero;
import game.entities.individuals.paladin.S_DivineArmor;
import game.entities.individuals.paladin.S_LightBlast;
import game.entities.individuals.paladin.S_RighteousHammer;
import game.entities.individuals.paladin.S_ShatteringSwing;
import game.entities.individuals.paladin.S_ShiningShield;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.changeeffects.effects.DarkSecrets;

public class H_DarkMage extends Hero {

    public H_DarkMage() {
        super("Dark Mage");
        this.initBasePath("darkmage");
        this.secondaryResource = Stat.MANA;
        initAnimator();
        initSkills();
        initStats();
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
                new S_DarkBlast(this),
                new S_DarkSecrets(this),
                new S_DarkSchemes(this),
                new S_LifeForceSharing(this),
                new S_Deathpact(this)
        };
    }

    @Override
    protected void initStats() {
        //StatStats
        this.stats.put(Stat.MAGIC, 13);
        this.stats.put(Stat.FORCE, 2);
        this.stats.put(Stat.STAMINA, 2);
        this.stats.put(Stat.ENDURANCE, 4);
        this.stats.put(Stat.FINESSE, 1);
        this.stats.put(Stat.ACCURACY, 100);
        this.stats.put(Stat.SPEED, 11);

        //ResourceStats
        this.stats.put(Stat.LIFE, 12);
        this.stats.put(Stat.CURRENT_LIFE, 12);
        this.stats.put(Stat.LIFE_REGAIN, 1);

        this.stats.put(Stat.MANA, 13);
        this.stats.put(Stat.CURRENT_MANA, 13);
        this.stats.put(Stat.MANA_REGAIN, 2);
    }
}
