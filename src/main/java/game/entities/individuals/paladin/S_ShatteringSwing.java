package game.entities.individuals.paladin;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.statusinflictions.Injured;
import utils.MyMaths;

import java.util.List;

public class S_ShatteringSwing extends Skill {

    public S_ShatteringSwing(Hero hero) {
        super(hero);
        this.iconPath = "entities/paladin/icons/shatteringswing.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.1));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{2,3};
        this.possibleTargetPositions = new int[]{4};
        this.dmg = 5;
        this.faithGain = true;
        this.damageMode = DamageMode.PHYSICAL;
        this.primary = true;
        this.abilityType = AbilityType.PRIMARY;
    }



    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        if (MyMaths.success(this.hero.getStat(Stat.CURRENT_FAITH) *2)) {
            target.arena.stun(target);
        }
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "2*"+Stat.FAITH.getIconString()+" chance to stun";
    }


    @Override
    public String getName() {
        return "Shattering Swing";
    }
}
