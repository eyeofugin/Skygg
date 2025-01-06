package game.entities.individuals.rifle;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;

import java.util.List;

public class S_FieldRations extends Skill {

    public S_FieldRations(Hero hero) {
        super(hero);
        this.iconPath = "/icons/fieldrations.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.HEAL);
        this.targetType = TargetType.SINGLE_ALLY;
        this.distance = 1;
        this.cdMax = 3;
        this.comboEnabled = true;
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int heal = target.getStat(Stat.LIFE) / 4;
        if (this.hero.hasPermanentEffect(Combo.class) > 0) {
            this.hero.removePermanentEffectOfClass(Combo.class);
            heal*=2;
        }
        target.heal(this.hero, heal, this, false);
    }

    @Override
    public int getAIRating(Hero target) {
        if (target.getResourcePercentage(Stat.CURRENT_LIFE) < 25) {
            return 4;
        }
        if (target.getResourcePercentage(Stat.CURRENT_LIFE) < 50) {
            return 2;
        }
        return 0;
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Heals target for 1/4 max life. Combo: 1/2 max life instead.";
    }


    @Override
    public String getName() {
        return "Field Rations";
    }
}
