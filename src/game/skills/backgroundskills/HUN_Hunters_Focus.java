package game.skills.backgroundskills;

import framework.Logger;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.changeeffects.effects.BountyTarget;

public class HUN_Hunters_Focus extends Skill {
    public static final int DMG_BONUS_PERCENTAGE = 30;

    public HUN_Hunters_Focus(Hero e) {
        super(e);
        this.name="hun_hunters_focus";
        this.translation="Hunters Focus";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.passive = true;
        this.powerPercentage = 20;
    }
    @Override
    public Skill getCast() {
        HUN_Hunters_Focus cast = new HUN_Hunters_Focus(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Hero e) {
        return "Deal " + DMG_BONUS_PERCENTAGE + "% more dmg to units with bounty.";
    }
    @Override
    public int getDamageChanges(Hero caster, Hero target, Skill cast, int result, Stat damageType, boolean simulated) {
        int bonus = 0;
        if (caster == this.Hero &&
            target.hasPermanentEffect(BountyTarget.class)>0) {
            Logger.logLn(this.Hero.name + ".H" +
                    "UN_HuntersFocus.damageChange");
            bonus = result * DMG_BONUS_PERCENTAGE / 100;
        }
        return result + bonus;
    }
}
