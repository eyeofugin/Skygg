package game.entities.individuals.eldritchguy;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Blight;
import game.skills.changeeffects.effects.Combo;

import java.util.List;

public class S_GraspOfTheAbyss extends Skill {

    public S_GraspOfTheAbyss(Hero hero) {
        super(hero);
        this.iconPath = "entities/eldritchguy/icons/graspoftheabyss.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.2));
        this.targetType = TargetType.SINGLE;
        this.distance = 1;
        this.dmg = 8;
        this.damageMode = DamageMode.PHYSICAL;
        this.comboEnabled = true;
        this.primary = true;
        this.abilityType = AbilityType.PRIMARY;
    }



    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        if (this.hero.hasPermanentEffect(Combo.class) > 0) {
            this.hero.removePermanentEffectOfClass(Combo.class);
            target.addEffect(new Blight(1), this.hero);
        }
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Deals true damage. Combo: Apply Blight";
    }


    @Override
    public String getName() {
        return "Grasp of the Abyss";
    }
}
