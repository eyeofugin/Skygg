package game.entities.individuals.paladin;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.AxeSwingCounter;
import game.skills.changeeffects.statusinflictions.Bleeding;

import java.util.List;

public class S_DivineArmor extends Skill {

    public S_DivineArmor(Hero hero) {
        super(hero);
        this.iconPath = "/res/icons/divinearmor.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.BUFF);
        this.targetType = TargetType.SELF;
        this.cdMax = 1;
        this.faithCost = 6;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.addToStat(Stat.ENDURANCE, 3);
        this.hero.addToStat(Stat.STAMINA, 1);
    }

    @Override
    protected void initAnimation() {
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "+3END +1STA";
    }

    @Override
    public void addSubscriptions() {

    }

    @Override
    public String getName() {
        return "Divine Armor";
    }
}
