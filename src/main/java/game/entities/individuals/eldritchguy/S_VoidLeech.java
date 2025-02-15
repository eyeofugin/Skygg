package game.entities.individuals.eldritchguy;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.statusinflictions.Injured;

import java.util.List;

public class S_VoidLeech extends Skill {

    public S_VoidLeech(Hero hero) {
        super(hero);
        this.iconPath = "/icons/voidleech.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.LIFE, 0.05));
        this.targetType = TargetType.SINGLE;
        this.distance = 2;
        this.dmg = 5;
        this.damageType = DamageType.DARK;
        this.damageMode = DamageMode.PHYSICAL;
        this.primary = true;
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
