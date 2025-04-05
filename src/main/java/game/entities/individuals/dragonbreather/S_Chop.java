package game.entities.individuals.dragonbreather;

import framework.graphics.text.Color;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.AxeSwingCounter;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.statusinflictions.Bleeding;
import utils.MyMaths;

import java.util.List;

public class S_Chop extends Skill {

    public S_Chop(Hero hero) {
        super(hero);
        this.iconPath = "entities/dragonbreather/icons/axeswing.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.3), new Multiplier(Stat.MAGIC, 0.2));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{2,3};
        this.possibleTargetPositions = new int[]{4};
        this.dmg = 10;
        this.damageMode = DamageMode.PHYSICAL;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int bleedingSuccess = this.hero.getStat(Stat.MAGIC) + 30;
        if (MyMaths.success(bleedingSuccess)) {
            target.addEffect(new Bleeding(1), this.hero);
        }
        int burningSuccess = this.hero.getStat(Stat.MAGIC) + 30;
        if (MyMaths.success(burningSuccess)) {
            target.addEffect(new Burning(1), this.hero);
        }
    }

    @Override
    public int getAIRating(Hero target) {
        if (this.hero.hasPermanentEffect(AxeSwingCounter.class) == 1) {
            return 2;
        }
        return 0;
    }



    @Override
    public String getDescriptionFor(Hero hero) {
        return (this.hero.getStat(Stat.MAGIC) +30) + "(30+"+Stat.MAGIC.getColorKey()+"100%"+Stat.MAGIC.getIconString()+ Color.WHITE.getCodeString()+")% Chance to give "+Bleeding.getStaticIconString()+"(1) and "+Burning.getStaticIconString()+"(1).";
    }

    @Override
    public String getName() {
        return "Chop";
    }
}
