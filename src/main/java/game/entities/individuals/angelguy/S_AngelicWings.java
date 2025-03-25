package game.entities.individuals.angelguy;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.effects.Cover;
import game.skills.changeeffects.statusinflictions.Bleeding;
import utils.MyMaths;

import java.util.List;

public class S_AngelicWings extends Skill {

    public S_AngelicWings(Hero hero) {
        super(hero);
        this.iconPath = "entities/angelguy/icons/angelicwings.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.BUFF);
        this.targetType = TargetType.SINGLE_OTHER;
        this.possibleTargetPositions = new int[]{0,1,2,3};
        this.possibleCastPositions = new int[]{0,1,2,3};
        this.cdMax = 3;
        this.abilityType = AbilityType.TACTICAL;
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.addEffect(new Cover(2), this.hero);
        target.addEffect(new Combo(), this.hero);
        this.hero.heal(this.hero, 5 + (int)(this.hero.getStat(Stat.ENDURANCE)*0.1), this, false);
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gives cover and combo. Heals self for 5 + 10% END";
    }


    @Override
    public String getName() {
        return "Angelic Wings";
    }
}
