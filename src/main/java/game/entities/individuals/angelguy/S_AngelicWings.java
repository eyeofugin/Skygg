package game.entities.individuals.angelguy;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.effects.Cover;

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
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE_OTHER;
        this.possibleTargetPositions = new int[]{0,1,2,3};
        this.possibleCastPositions = new int[]{0,1,2,3};
        this.effects = List.of(new Cover(2), new Combo());
        this.heal = 5;
        this.healMultipliers = List.of(new Multiplier(Stat.ENDURANCE, 0.1));
        this.cdMax = 3;
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.heal(this.hero, getHealWithMulti(this.hero), this, false);
    }

    @Override
    public String getDmgOrHealString() {
        return "Heal self: "+ getHealString();//getHealWithMulti(this.hero)+" (5+" + Stat.ENDURANCE.getColorKey() + "10%" + Stat.ENDURANCE.getIconString() +")";
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "";
    }


    @Override
    public String getName() {
        return "Angelic Wings";
    }
}
