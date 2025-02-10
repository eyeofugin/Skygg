package game.entities.individuals.angelguy;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.statusinflictions.Bleeding;
import utils.MyMaths;

import java.util.List;

public class S_LightJavelin extends Skill {

    public S_LightJavelin(Hero hero) {
        super(hero);
        this.iconPath = "/icons/lightjavelin.png";
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.targetType = TargetType.SINGLE;
        this.distance = 3;
        this.damageMode = DamageMode.PHYSICAL;
        this.damageType = DamageType.LIGHT;
        this.dmg = 6;
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.3));
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.addResource(Stat.CURRENT_HALO, Stat.HALO, 1, this.hero);
        target.addToStat(Stat.EVASION, -10);
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "magical/light, gain 1 Favor, give -10 Evasion";
    }


    @Override
    public String getName() {
        return "Light Javelin";
    }
}
