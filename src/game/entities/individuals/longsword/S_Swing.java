package game.entities.individuals.longsword;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.AxeSwingCounter;
import game.skills.changeeffects.statusinflictions.Bleeding;
import utils.MyMaths;

import java.util.List;

public class S_Swing extends Skill {

    public S_Swing(Hero hero) {
        super(hero);
        this.iconPath = "/res/icons/swing.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE;
        this.distance = 2;
        this.damageType = DamageType.NORMAL;
        this.dmg = 5;
        this.dmgMultipliers = List.of(new Multiplier(Stat.FORCE, 0.1));

    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/res/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "10%+FOR chance to bleed";
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int success = this.hero.getStat(Stat.FORCE) + 10;
        if (MyMaths.success(success)) {
            target.addEffect(new Bleeding(2), this.hero);
        }
    }

    @Override
    public String getName() {
        return "Swing";
    }
}
