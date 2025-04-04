package game.entities.individuals.phoenixguy;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Combo;

import java.util.List;

public class S_Spark extends Skill {

    public S_Spark(Hero hero) {
        super(hero);
        this.iconPath = "entities/phoenixguy/icons/spark.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.1));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{1,2,3};
        this.possibleTargetPositions = new int[]{4,5,6};
        this.dmg = 12;
        this.damageMode = DamageMode.MAGICAL;
        this.aiTags = List.of(AiSkillTag.COMBO_ENABLED, AiSkillTag.FAITH_GAIN);
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, 3, this.hero);
        if (this.hero.hasPermanentEffect(Combo.class) > 0) {
            this.hero.removePermanentEffectOfClass(Combo.class);
            this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, 1, this.hero);
        }
    }

    @Override
    public String getName() {
        return "Spark";
    }



    @Override
    public String getDescriptionFor(Hero hero) {
        return "+3 Favor. Combo: +1 Favor.";
    }

}
