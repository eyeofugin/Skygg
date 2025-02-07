package game.entities.individuals.battleaxe;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.statusinflictions.Bleeding;
import utils.MyMaths;

import java.util.ArrayList;
import java.util.List;

public class S_BloodCleave extends Skill {

    public S_BloodCleave(Hero hero) {
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
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.2));
        this.targetType = TargetType.SINGLE;
        this.distance = 1;
        this.dmg = 5;
        this.damageMode = DamageMode.PHYSICAL;
        this.damageType = DamageType.NORMAL;
        this.effects = List.of(new Bleeding(1));
        this.primary = true;
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
        return "bleeds";
    }

    @Override
    public String getName() {
        return "Blood Cleave";
    }
}
