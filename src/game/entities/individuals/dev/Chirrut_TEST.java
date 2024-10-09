package game.entities.individuals.dev;

import framework.resources.SpriteLibrary;
import framework.states.Arena;
import game.entities.Animation;
import game.entities.Animator;
import game.entities.Entity;
import game.objects.equipments.basic.BlasterPistol;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.backgroundskills.GUA_Cover;
import game.skills.backgroundskills.GUA_Steadfast;
import game.skills.itemskills.AutoAttack;
import game.skills.itemskills.Cannon_Unload;
import game.skills.itemskills.ITEM_Grenades;

import java.awt.*;

public class Chirrut_TEST extends Entity {
    public static int counter = 0;

    static int[][] idleR = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0)},
            64,64,"res/sprites/dev/idle_w.png",false);
    static int[][] idleL = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0)},
            64,64,"res/sprites/characters/chirrut/chirrut_idle.png",true);
    static int[][] aaR = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0), new Point(2,0)},
            64,64,"res/sprites/dev/action_w.png",false);
    static int[][] aaL = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0), new Point(2,0), new Point(3,0),
                    new Point(4,0),new Point(5,0), new Point(6,0), new Point(7,0),new Point(8,0)},
            64,64,"res/sprites/characters/baze/baze_aa.png",true);
    static int[][] grenadeR = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0), new Point(2,0), new Point(3,0),
                    new Point(4,0),new Point(5,0), new Point(6,0)},
            64,64,"res/sprites/characters/baze/baze_grenade.png",false);
    static int[][] grenadeL = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0), new Point(2,0), new Point(3,0),
                    new Point(4,0),new Point(5,0), new Point(6,0)},
            64,64,"res/sprites/characters/baze/baze_grenade.png",true);

    static int[][] damagedR = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0), new Point(2,0), new Point(3,0)},
            32,32,"res/sprites/dev/gua/gua_w_dmg.png",false);
    static int[][] damagedL = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0), new Point(2,0), new Point(3,0)},
            32,32,"res/sprites/dev/gua/gua_w_dmg.png",true);


    public Chirrut_TEST(Arena arena, boolean enemy, int position) {
        super(arena, enemy, position);
        this.anim = getAnimator();
        this.id = counter++;
        this.name = "Chirrut_TEST";
        this.appearanceName = "Chirrut";
        BlasterPistol primary = new BlasterPistol();
        primary.inventoryPosition = 0;
        primary.entity = this;
        this.equipments.add(0, primary);

        this.skills = new Skill[] {
                new AutoAttack(this, primary),
                new Cannon_Unload(this),
                new GUA_Cover(this),
                new GUA_Steadfast(this),
                new ITEM_Grenades(this)};
        this.stats.put(Stat.MAX_LIFE, 45);
        this.stats.put(Stat.CURRENT_LIFE, 45);
        this.stats.put(Stat.LIFE_REGAIN, 2);

        this.stats.put(Stat.TURN_START_ACTION, 1);
        this.stats.put(Stat.CURRENT_ACTION, 1);

        this.stats.put(Stat.STRENGTH, 12);
        this.stats.put(Stat.PRECISION, 5);
        this.stats.put(Stat.WILLPOWER, 4);

        this.stats.put(Stat.VITALITY, 13);
        this.stats.put(Stat.REFLEXES, 4);
        this.stats.put(Stat.HARMONY, 2);

        this.stats.put(Stat.SPEED, 7);
        this.stats.put(Stat.ACCURACY, 100);
        this.stats.put(Stat.EVASION, 0);
        this.stats.put(Stat.LETHALITY, 0);
        this.stats.put(Stat.CRIT_CHANCE, 10);
    }
    @Override
    public void update(int frame) {
        this.anim.animate();
    }

    @Override
    public int[] render() {

        if (this.anim.image == null) {
        }

        return this.anim.image;
    }
    private Animator getAnimator() {

        Animator anim = new Animator();
        anim.width = 64;
        anim.height = 64;

        Animation idleLAnimation = new Animation();
        idleLAnimation.images.put(60,idleL[0]);
        idleLAnimation.images.put(120,idleL[1]);
        idleLAnimation.length = 120;
        Animation idleRAnimation = new Animation();
        idleRAnimation.images.put(60,idleR[0]);
        idleRAnimation.images.put(120,idleR[1]);
        idleRAnimation.length = 120;

        Animation runLAnimation = new Animation();
        runLAnimation.images.put(10,aaL[0]);
        runLAnimation.images.put(20,aaL[1]);
        runLAnimation.images.put(30,aaL[2]);
        runLAnimation.images.put(34,aaL[3]);
        runLAnimation.images.put(38,aaL[4]);
        runLAnimation.images.put(42,aaL[5]);
        runLAnimation.images.put(46,aaL[6]);
        runLAnimation.images.put(58,aaL[7]);
        runLAnimation.images.put(68,aaL[8]);
        runLAnimation.length = 68;
        Animation runRAnimation = new Animation();
        runRAnimation.images.put(20,aaR[0]);
        runRAnimation.images.put(40,aaR[1]);
        runRAnimation.images.put(60,aaR[2]);
        runRAnimation.length = 60;

        Animation grenadeLAnimation = new Animation();
        grenadeLAnimation.images.put(10,grenadeL[0]);
        grenadeLAnimation.images.put(20,grenadeL[1]);
        grenadeLAnimation.images.put(30,grenadeL[2]);
        grenadeLAnimation.images.put(40,grenadeL[3]);
        grenadeLAnimation.images.put(45,grenadeL[4]);
        grenadeLAnimation.images.put(50,grenadeL[5]);
        grenadeLAnimation.images.put(55,grenadeL[6]);
        grenadeLAnimation.length = 55;
        Animation grenadeRAnimation = new Animation();
        grenadeRAnimation.images.put(10,grenadeR[0]);
        grenadeRAnimation.images.put(20,grenadeR[1]);
        grenadeRAnimation.images.put(30,grenadeR[2]);
        grenadeRAnimation.images.put(40,grenadeR[3]);
        grenadeRAnimation.images.put(45,grenadeR[4]);
        grenadeRAnimation.images.put(50,grenadeR[5]);
        grenadeRAnimation.images.put(55,grenadeR[6]);
        grenadeRAnimation.length = 55;


        Animation damagedRAnimation = new Animation();
        damagedRAnimation.images.put(5,damagedR[0]);
        damagedRAnimation.images.put(10,damagedR[1]);
        damagedRAnimation.images.put(15,damagedR[2]);
        damagedRAnimation.images.put(20,damagedR[3]);
        damagedRAnimation.length = 20;
        Animation damagedLAnimation = new Animation();
        damagedLAnimation.images.put(5,damagedL[0]);
        damagedLAnimation.images.put(10,damagedL[1]);
        damagedLAnimation.images.put(15,damagedL[2]);
        damagedLAnimation.images.put(20,damagedL[3]);
        damagedLAnimation.length = 20;

        anim.animations.put("idleL", idleLAnimation);
        anim.animations.put("idleR", idleRAnimation);
        anim.animations.put("aaR", runRAnimation);
        anim.animations.put("aaL", runLAnimation);
        anim.animations.put("grenadesR", grenadeRAnimation);
        anim.animations.put("grenadesL", grenadeLAnimation);
        anim.animations.put("damagedR", damagedRAnimation);
        anim.animations.put("damagedL", damagedLAnimation);

        anim.defaultAnim = enemy ? "idleL" : "idleR";
        anim.currentAnim = anim.defaultAnim;
        anim.onLoop = true;
        return anim;
    }
}