package game.entities.individuals.thewizard;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Blight;

import java.util.List;

public class S_Dispel extends Skill {

    public S_Dispel(Hero hero) {
        super(hero);
        this.iconPath = "/icons/dispel.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.CC);
        this.targetType = TargetType.SINGLE;
        this.distance = 3;
        this.cdMax = 3;
        this.manaCost = 3;
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});

    }

    @Override
    public int getAIRating(Hero target) {
        if (target.getSecondaryResource() == null) {
            return -2;
        } else if (target.getSecondaryResource() == Stat.MANA) {
            return 5;
        } else {
            return 3;
        }
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "Target loses 25% of their max secondary resource (Faith/Mana)";
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        if (target.getSecondaryResource().equals(Stat.MANA)) {
            int resourceLoss = target.getStat(Stat.MANA)/4;
            target.addResource(Stat.CURRENT_MANA, Stat.MANA, -1*resourceLoss);
        } else if (target.getSecondaryResource().equals(Stat.FAITH)) {
            int resourceLoss = target.getStat(Stat.FAITH)/4;
            target.addResource(Stat.CURRENT_FAITH, Stat.FAITH, -1*resourceLoss);
        }
    }

    @Override
    public String getName() {
        return "Dispel";
    }
}
