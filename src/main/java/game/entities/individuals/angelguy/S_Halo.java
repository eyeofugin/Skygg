package game.entities.individuals.angelguy;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.AbilityType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.effects.Invincible;

import java.util.List;

public class S_Halo extends Skill {

    public S_Halo(Hero hero) {
        super(hero);
        this.iconPath = "entities/angelguy/icons/halo.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.BUFF);
        this.targetType = TargetType.ALL_ALLY;
        this.cdMax = 5;
        this.abilityType = AbilityType.ULT;
    }

    @Override
    public void resolve() {
        int halo = this.hero.getStat(Stat.CURRENT_HALO);
        this.hero.changeStatTo(Stat.CURRENT_HALO, 0);
        for (Hero target : targets) {
            target.addEffect(new Invincible(halo), this.hero);
        }
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Uses all halo. All allies get Invincible for X turns, where X is the halo stacks.";
    }

    @Override
    public String getName() {
        return "Halo";
    }
}
