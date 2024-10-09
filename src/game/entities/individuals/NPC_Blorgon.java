package game.entities.individuals;

import framework.resources.SpriteLibrary;
import framework.states.Arena;
import game.entities.Animation;
import game.entities.Animator;
import game.entities.Entity;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.backgroundskills.AllyHeal;

import java.awt.Point;

public class NPC_Blorgon extends Entity {

    public static int counter = 1;

    static int[][] meleeR = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0), new Point(0,1), new Point(1,1)},
            32,32,"res/sprites/enemies/blorgon/blorgon_melee.png",false);
    static int[][] meleeL = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0), new Point(0,1), new Point(1,1)},
            32,32,"res/sprites/enemies/blorgon/blorgon_melee.png",true);
    static int[][] idleR = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0), new Point(1,1)},
             32,32,"res/sprites/enemies/blorgon/blorgon_idle.png",false);
    static int[][] idleL = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0), new Point(1,1)},
            32,32,"res/sprites/enemies/blorgon/blorgon_idle.png",true);
    static int[][] damagedR = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0), new Point(0,1), new Point(1,1)},
            32,32,"res/sprites/enemies/blorgon/blorgon_damaged.png",false);
    static int[][] damagedL = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0), new Point(0,1), new Point(1,1)},
             32,32,"res/sprites/enemies/blorgon/blorgon_damaged.png",true);

    static int[][] meleeR_r = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0), new Point(0,1), new Point(1,1)},
            32,32,"res/sprites/enemies/blorgon_red/blorgon_melee.png",false);
    static int[][] meleeL_r = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0), new Point(0,1), new Point(1,1)},
            32,32,"res/sprites/enemies/blorgon_red/blorgon_melee.png",true);
    static int[][] idleR_r = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0), new Point(1,1)},
            32,32,"res/sprites/enemies/blorgon_red/blorgon_idle.png",false);
    static int[][] idleL_r = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0), new Point(1,1)},
            32,32,"res/sprites/enemies/blorgon_red/blorgon_idle.png",true);
    static int[][] damagedR_r = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0), new Point(0,1), new Point(1,1)},
            32,32,"res/sprites/enemies/blorgon_red/blorgon_damaged.png",false);
    static int[][] damagedL_r = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0), new Point(0,1), new Point(1,1)},
            32,32,"res/sprites/enemies/blorgon_red/blorgon_damaged.png",true);

    static int[][] meleeR_b = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0), new Point(0,1), new Point(1,1)},
            32,32,"res/sprites/enemies/blorgon_blue/blorgon_melee.png",false);
    static int[][] meleeL_b = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0), new Point(0,1), new Point(1,1)},
            32,32,"res/sprites/enemies/blorgon_blue/blorgon_melee.png",true);
    static int[][] idleR_b = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0), new Point(1,1)},
            32,32,"res/sprites/enemies/blorgon_blue/blorgon_idle.png",false);
    static int[][] idleL_b = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0), new Point(1,1)},
            32,32,"res/sprites/enemies/blorgon_blue/blorgon_idle.png",true);
    static int[][] damagedR_b = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0), new Point(0,1), new Point(1,1)},
            32,32,"res/sprites/enemies/blorgon_blue/blorgon_damaged.png",false);
    static int[][] damagedL_b = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0), new Point(0,1), new Point(1,1)},
            32,32,"res/sprites/enemies/blorgon_blue/blorgon_damaged.png",true);

    static int[][] meleeR_g = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0), new Point(0,1), new Point(1,1)},
            32,32,"res/sprites/enemies/blorgon_green/blorgon_melee.png",false);
    static int[][] meleeL_g = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0), new Point(0,1), new Point(1,1)},
           32,32,"res/sprites/enemies/blorgon_green/blorgon_melee.png",true);
    static int[][] idleR_g = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0), new Point(1,1)},
            32,32,"res/sprites/enemies/blorgon_green/blorgon_idle.png",false);
    static int[][] idleL_g = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0), new Point(1,1)},
            32,32,"res/sprites/enemies/blorgon_green/blorgon_idle.png",true);
    static int[][] damagedR_g = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0), new Point(0,1), new Point(1,1)},
             32,32,"res/sprites/enemies/blorgon_green/blorgon_damaged.png",false);
    static int[][] damagedL_g = SpriteLibrary.setupSprites(new Point[]{new Point(0,0),new Point(1,0), new Point(0,1), new Point(1,1)},
            32,32,"res/sprites/enemies/blorgon_green/blorgon_damaged.png",true);

    public NPC_Blorgon(Arena arena, boolean enemy, int position, int colorIndex) {
        super(arena, enemy, position);
        this.anim = getAnimator(colorIndex);
        this.id = counter++;
        this.name = "Blorgon";
        this.appearanceName = "Blorgon";
        this.skills = new Skill[] {new AllyHeal(this)};
        this.stats.put(Stat.MAX_LIFE, 30);
        this.stats.put(Stat.CURRENT_LIFE, 30);
        this.stats.put(Stat.LIFE_REGAIN, 1);
        this.stats.put(Stat.TURN_START_ACTION, 1);
        this.stats.put(Stat.CURRENT_ACTION, 1);
        this.stats.put(Stat.MELEE, 1);
        this.stats.put(Stat.RANGED, 1);
        this.stats.put(Stat.FORCE, 1);
        this.stats.put(Stat.STRENGTH, 1);
        this.stats.put(Stat.PRECISION, 1);
        this.stats.put(Stat.WILLPOWER, 1);
        this.stats.put(Stat.VITALITY, 1);
        this.stats.put(Stat.REFLEXES, 1);
        this.stats.put(Stat.HARMONY, 1);
        this.stats.put(Stat.SPEED, 1);
        this.stats.put(Stat.EVASION, 1);
        this.stats.put(Stat.ACCURACY, 1);
        this.stats.put(Stat.LETHALITY, 1);
        this.stats.put(Stat.CRIT_CHANCE, 1);
    }

    @Override
    public void update(int frame) {

        this.anim.animate();

    }

    @Override
    public int[] render() {
        return this.anim.image;
    }

    private Animator getAnimator(int colorIndex) {

        Animator anim = new Animator();
        anim.width = 64;
        anim.height = 64;

        int[][] idleL = null;
        int[][] idleR = null;
        int[][] meleeL = null;
        int[][] meleeR = null;
        int[][] damagedL = null;
        int[][] damagedR = null;
        switch(colorIndex) {
            case 1:
                idleL = NPC_Blorgon.idleL;
                idleR = NPC_Blorgon.idleR;
                meleeL = NPC_Blorgon.meleeL;
                meleeR = NPC_Blorgon.meleeR;
                damagedL = NPC_Blorgon.damagedL;
                damagedR = NPC_Blorgon.damagedR;
                break;
            case 2:
                idleL = NPC_Blorgon.idleL_b;
                idleR = NPC_Blorgon.idleR_b;
                meleeL = NPC_Blorgon.meleeL_b;
                meleeR = NPC_Blorgon.meleeR_b;
                damagedL = NPC_Blorgon.damagedL_b;
                damagedR = NPC_Blorgon.damagedR_b;
                break;
            case 3:
                idleL = NPC_Blorgon.idleL_g;
                idleR = NPC_Blorgon.idleR_g;
                meleeL = NPC_Blorgon.meleeL_g;
                meleeR = NPC_Blorgon.meleeR_g;
                damagedL = NPC_Blorgon.damagedL_g;
                damagedR = NPC_Blorgon.damagedR_g;
                break;
            case 4:
                idleL = NPC_Blorgon.idleL_r;
                idleR = NPC_Blorgon.idleR_r;
                meleeL = NPC_Blorgon.meleeL_r;
                meleeR = NPC_Blorgon.meleeR_r;
                damagedL = NPC_Blorgon.damagedL_r;
                damagedR = NPC_Blorgon.damagedR_r;
                break;
        }

        Animation idleLAnimation = new Animation();
        idleLAnimation.images.put(20,idleL[0]);
        idleLAnimation.images.put(40,idleL[1]);
        idleLAnimation.images.put(60,idleL[2]);
        idleLAnimation.length = 60;
        Animation idleRAnimation = new Animation();
        idleRAnimation.images.put(20,idleR[0]);
        idleRAnimation.images.put(40,idleR[1]);
        idleRAnimation.images.put(60,idleR[2]);
        idleRAnimation.length = 60;

        Animation meleeRAnimation = new Animation();
        meleeRAnimation.images.put(20,meleeR[0]);
        meleeRAnimation.images.put(40,meleeR[1]);
        meleeRAnimation.images.put(75,meleeR[2]);
        meleeRAnimation.images.put(80,meleeR[3]);
        meleeRAnimation.length = 80;
        Animation meleeLAnimation = new Animation();
        meleeLAnimation.images.put(20,meleeL[0]);
        meleeLAnimation.images.put(40,meleeL[1]);
        meleeLAnimation.images.put(75,meleeL[2]);
        meleeLAnimation.images.put(80,meleeL[3]);
        meleeLAnimation.length = 80;

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
        anim.animations.put("meleeL", meleeLAnimation);
        anim.animations.put("meleeR", meleeRAnimation);
        anim.animations.put("damagedR", damagedRAnimation);
        anim.animations.put("damagedL", damagedLAnimation);

        anim.defaultAnim = enemy ? "idleL" : "idleR";
        anim.currentAnim = anim.defaultAnim;
        anim.onLoop = true;
        return anim;
    }
}
