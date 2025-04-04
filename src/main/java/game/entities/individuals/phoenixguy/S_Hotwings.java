package game.entities.individuals.phoenixguy;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.effects.Combo;

import java.util.List;

public class S_Hotwings extends Skill {

    public S_Hotwings(Hero hero) {
        super(hero);
        this.iconPath = "entities/phoenixguy/icons/hotwings.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.5));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{2,3};
        this.possibleTargetPositions = new int[]{4,5};
        this.effects = List.of(new Burning(1));
        this.dmg = 4;
        this.damageMode = DamageMode.MAGICAL;
        this.aiTags = List.of(AiSkillTag.FAITH_GAIN);
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, 1, this.hero);
    }

    @Override
    public String getName() {
        return "Hot Wings";
    }



    @Override
    public String getDescriptionFor(Hero hero) {
        return "+1 Favor. Burns.";
    }

}
