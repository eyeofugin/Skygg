package game.entities.individuals.burner;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Burning;
import utils.MyMaths;

import java.util.List;

public class S_Burn extends Skill {

    public S_Burn(Hero hero) {
        super(hero);
        this.iconPath ="entities/burner/icons/burn.png";
        addSubscriptions();
        setToInitial();

    }
    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.CC);
        this.targetType = TargetType.SINGLE;
        this.effects = List.of(new Burning(4));
        this.distance = 3;
        this.primary = true;
        this.faithGain = true;
        this.abilityType = AbilityType.PRIMARY;
    }



    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gets 2"+Stat.FAITH.getIconString()+". Give 4 Burn stacks.";
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, 2, this.hero);
    }

    @Override
    public String getName() {
        return "Burn";
    }
}
