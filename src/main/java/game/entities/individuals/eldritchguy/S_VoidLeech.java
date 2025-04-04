package game.entities.individuals.eldritchguy;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.statusinflictions.Injured;

import java.util.List;

public class S_VoidLeech extends Skill {

    public S_VoidLeech(Hero hero) {
        super(hero);
        this.iconPath = "entities/eldritchguy/icons/voidleech.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.dmgMultipliers = List.of(new Multiplier(Stat.LIFE, 0.05));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{2,3};
        this.possibleTargetPositions = new int[]{4,5};
        this.dmg = 5;
        this.damageMode = DamageMode.PHYSICAL;
    }



    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        Stat resource = target.getSecondaryResource();
        if (resource != null) {
            switch (resource) {
                case MANA -> target.addResource(Stat.CURRENT_MANA, Stat.MANA, -2, this.hero);
                case FAITH -> target.addResource(Stat.CURRENT_FAITH, Stat.FAITH, -2, this.hero);
                case HALO -> target.addResource(Stat.CURRENT_HALO, Stat.HALO, -2, this.hero);
            }
        }
        this.hero.addResource(Stat.CURRENT_MANA, Stat.MANA, 2, this.hero);
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Target loses 2 of secondary resource. +2 Mana.";
    }


    @Override
    public String getName() {
        return "Void Leech";
    }
}
