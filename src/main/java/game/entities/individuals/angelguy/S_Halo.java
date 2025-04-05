package game.entities.individuals.angelguy;

import game.entities.Hero;
import game.skills.*;
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
        this.tags = List.of(SkillTag.ULT);
        this.targetType = TargetType.ALL_TARGETS;
        this.possibleTargetPositions = new int[]{0,1,2,3};
        this.possibleCastPositions = new int[]{0,1,2,3};
        this.cdMax = 5;
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
    public String getUpperDescriptionFor(Hero hero) {
        return "Passive: " + this.hero.getName() + " uses " + Stat.HALO.getIconString() + " as resource instead of "+Stat.FAITH.getIconString()+".";
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Active: Uses all " + Stat.HALO.getIconString() + " stacks. All allies get Invincible(X), where X is the number of "+ Stat.HALO.getIconString()+" stacks used.";
    }

    @Override
    public String getName() {
        return "Halo";
    }
}
