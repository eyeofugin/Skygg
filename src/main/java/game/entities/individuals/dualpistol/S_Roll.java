package game.entities.individuals.dualpistol;

import com.sun.source.tree.Scope;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.effects.Scoped;
import game.skills.changeeffects.statusinflictions.Bleeding;
import game.skills.changeeffects.statusinflictions.Rooted;

public class S_Roll extends Skill {

    public S_Roll(Hero hero) {
        super(hero);
        this.iconPath = "/icons/roll.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE_ALLY;
        this.distance = 1;
        this.cdMax = 3;
        this.comboEnabled = true;
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "Move 1, Combo: +2 Speed";
    }

    @Override
    public int getAIRating(Hero target) {
        return getRollRating(target);
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int targetPosition = target.getPosition();
        this.hero.arena.moveTo(this.hero, targetPosition);
        if (this.hero.hasPermanentEffect(Combo.class)> 0) {
            this.hero.removePermanentEffectOfClass(Combo.class);
            this.hero.addToStat(Stat.SPEED, 2);
        }
    }
    @Override
    public String getName() {
        return "Roll";
    }
}
