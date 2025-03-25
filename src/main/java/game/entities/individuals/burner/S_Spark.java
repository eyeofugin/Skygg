package game.entities.individuals.burner;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Burning;
import utils.MyMaths;

import java.util.List;

public class S_Spark extends Skill {

    public S_Spark(Hero hero) {
        super(hero);
        this.iconPath ="entities/burner/icons/spark.png";
        addSubscriptions();
        setToInitial();
    }
    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.FAITH, 0.3));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{4,5,6};
        this.dmg = 7;
        this.damageMode = DamageMode.MAGICAL;
        this.primary = true;
        this.faithGain = true;
        this.abilityType = AbilityType.PRIMARY;
    }



    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gets 3"+Stat.FAITH.getIconString()+". (2 * "+Stat.MAGIC.getIconString()+")% chance to burn";
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int magic = this.hero.getStat(Stat.MAGIC);
        if (MyMaths.success(magic * 2)) {
            target.addEffect(new Burning(1), this.hero);
        }
        this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, 3, this.hero);
    }

    @Override
    public String getName() {
        return "Spark";
    }
}
