package game.entities.individuals.longsword;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.statusinflictions.Injured;
import utils.MyMaths;

import java.util.List;

public class S_Stab extends Skill {

    public S_Stab(Hero hero) {
        super(hero);
        this.iconPath = "/icons/stab.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.LINE;
        this.distance = 2;
        this.damageType = DamageType.NORMAL;
        this.dmg = 5;
        this.cdMax = 3;
        this.dmgMultipliers = List.of(new Multiplier(Stat.FORCE, 0.1));

    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "30%+"+Stat.FORCE.getIconString()+" Chance to injure";
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int success = this.hero.getStat(Stat.FORCE) + 30;
        if (MyMaths.success(success)) {
            target.addEffect(new Injured(3), this.hero);
        }
    }

    @Override
    public String getName() {
        return "Stab";
    }
}
