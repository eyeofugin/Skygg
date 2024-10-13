package game.skills.backgroundskills;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.TargetType;

public class TEC_Weapon_Enhancement extends Skill {
    private static final int ACTION_COST = 1;
    private static final int CD_TOTAL = 1;
    private static final int SPEED_MULTIPLIER = 20;
    private static final int ACC_UP_TURNS = 2;

    public TEC_Weapon_Enhancement(Hero e) {
        super(e);
        this.name="tec_weapon_enhancement";
        this.translation="Weapon Enhancement";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.SINGLE;
        this.actionCost = ACTION_COST;
        this.cdMax = CD_TOTAL;
//        this.effects = List.of(new Effect( -1, new Burning()));
//        this.casterEffects = List.of(new Effect( ACC_UP_TURNS, new Precise()),
//                new Effect(Effect.EffectType.RDM_CD_DOWN));

    }
    @Override
    public Skill getCast() {
        TEC_Weapon_Enhancement cast = new TEC_Weapon_Enhancement(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Hero e) {
        return "Auto attack that has a choosable effect.";
    }

    public int distance(Hero e) {
        //e.getPrimaryWeapon().getRange();
        return 1;
    }
    public int damage(Hero e) {
         return 10;//e.getPrimaryWeapon().getDamage() 0
    }
}
