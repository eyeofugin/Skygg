package game.entities.individuals.eldritchguy;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class S_VoidGraft extends Skill {

    public S_VoidGraft(Hero hero) {
        super(hero);
        this.iconPath = "/icons/voidgraft.png";
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
