package game.entities.individuals.battleaxe;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.effects.LifeSteal;
import game.skills.changeeffects.effects.Threatening;

import java.util.List;

public class S_BerserkerRage extends Skill {

    public S_BerserkerRage(Hero hero) {
        super(hero);
        this.iconPath = "/icons/berserkerrage.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.BUFF);
        this.targetType = TargetType.SELF;
        this.lifeCost = 15;
        this.cdMax = 3;
        this.comboEnabled = true;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.addEffect(new LifeSteal(2), this.hero);
        this.hero.addEffect(new Threatening(2), this.hero);
        if (this.hero.hasPermanentEffect(Combo.class) > 0) {
            this.hero.removePermanentEffectOfClass(Combo.class);
            this.hero.getEnemies().forEach(e->e.changeStatTo(Stat.SHIELD, 0));
        }
    }

    @Override
    public int getAIRating(Hero target) {
        if (this.hero.getCurrentLifePercentage() < 50) {
            return -1;
        }
        return 1;
    }
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Lose 15 life. Gain lifesteal for 2 turns. Gain threatening. Combo: Enemies lose all shields.";
    }


    @Override
    public String getName() {
        return "Berserker Rage";
    }
}
