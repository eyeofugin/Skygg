package game.entities.individuals.battleaxe;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.statusinflictions.Bleeding;
import utils.MyMaths;

import java.util.List;

public class S_AwesomeAxe extends Skill {

    public S_AwesomeAxe(Hero hero) {
        super(hero);
        this.iconPath = "/icons/awesomeaxe.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.FORCE, 0.2), new Multiplier(Stat.FINESSE, 0.1));
        this.targetType = TargetType.SINGLE;
        this.distance = 2;
        this.dmg = 5;
        this.damageType = DamageType.NORMAL;
        this.primary = true;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int success = this.hero.getStat(Stat.FORCE) + 50;
        if (MyMaths.success(success)) {
            target.addEffect(new Bleeding(1), this.hero);
        }
    }

    @Override
    public int getAIRating(Hero target) {
        return 1;
    }

    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "50+"+Stat.FORCE.getIconString()+" Chance to bleed";
    }

    @Override
    public String getName() {
        return "Awesome Axe";
    }
}
