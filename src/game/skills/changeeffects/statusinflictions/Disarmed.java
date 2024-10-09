package game.skills.changeeffects.statusinflictions;

import framework.Logger;
import game.skills.Effect;
import game.skills.Skill;

public class Disarmed extends Effect {

    public Disarmed(int turns) {
        this.turns = turns;
        this.name = "Disarmed";
        this.stackable = false;
        this.description = "Can't use Weapon Skills";
        this.type = ChangeEffectType.STATUS_INFLICTION;
    }
    public Disarmed(int turns,int chance) {
        this(turns);
        this.successChance = chance;
    }
    @Override
    public Disarmed getNew() {
        return new Disarmed(this.turns);
    }
    @Override
    public boolean canPerformCheck(Skill cast) {
        if (cast.entity == this.entity && cast.isWeaponSkill()) {
            Logger.logLn(this.entity.name + ".Disarmed.canPerformCheck: false");
            return false;
        }
        return true;
    }
}
