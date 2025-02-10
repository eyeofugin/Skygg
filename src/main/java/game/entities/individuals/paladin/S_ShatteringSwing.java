package game.entities.individuals.paladin;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.statusinflictions.Injured;
import utils.MyMaths;

import java.util.List;

public class S_ShatteringSwing extends Skill {

    public S_ShatteringSwing(Hero hero) {
        super(hero);
        this.iconPath = "/icons/shatteringswing.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.1));
        this.targetType = TargetType.SINGLE;
        this.distance = 1;
        this.dmg = 5;
        this.faithGain = true;
        this.damageType = DamageType.NORMAL;
        this.damageMode = DamageMode.PHYSICAL;
        this.primary = true;
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});

    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        if (MyMaths.success(this.hero.getStat(Stat.CURRENT_FAITH) *2)) {
            target.arena.stun(target);
        }
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "2*"+Stat.FAITH.getIconString()+" chance to stun";
    }


    @Override
    public String getName() {
        return "Shattering Swing";
    }
}
