package game.entities.individuals.duelist;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.effects.SwiftStrikeCounter;
import game.skills.changeeffects.statusinflictions.Injured;

import java.util.List;

public class S_Slash extends Skill {

    public S_Slash(Hero hero) {
        super(hero);
        this.iconPath = "entities/duelist/icons/slash.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.2), new Multiplier(Stat.SPEED, 0.5));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{1,2,3};
        this.possibleTargetPositions = new int[]{4,5};
        this.dmg = 7;
        this.damageMode = DamageMode.PHYSICAL;
    }



    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        if (this.hero.hasPermanentEffect(Combo.class) > 0) {
            this.hero.removePermanentEffectOfClass(Combo.class);
            target.addEffect(new Injured(1), this.hero);
        } else {
            this.hero.addEffect(new Combo(), this.hero);
        }
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Combo: Injure. Else gain combo.";
    }


    @Override
    public String getName() {
        return "Slash";
    }
}
