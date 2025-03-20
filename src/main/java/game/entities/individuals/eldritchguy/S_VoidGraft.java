package game.entities.individuals.eldritchguy;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;

import java.util.List;

public class S_VoidGraft extends Skill {

    public S_VoidGraft(Hero hero) {
        super(hero);
        this.iconPath = "entities/eldritchguy/icons/voidgraft.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.HEAL);
        this.healMultipliers = List.of(new Multiplier(Stat.ENDURANCE, 0.8));
        this.targetType = TargetType.SELF;
        this.manaCost = 2;
        this.abilityType = AbilityType.TACTICAL;
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return " ";
    }


    @Override
    public String getName() {
        return "Void Graft";
    }
}
