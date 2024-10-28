package game.entities.individuals.duelist;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.effects.SwiftStrikeCounter;

import java.util.List;

public class S_SwiftStrike extends Skill {

    public S_SwiftStrike(Hero hero) {
        super(hero);
        this.iconPath = "/res/icons/axeswing.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.FINESSE, 0.2),
                new Multiplier(Stat.FORCE, 0.3));
        this.targetType = TargetType.SINGLE;
        this.distance = 2;
        this.dmg = 3;
        this.damageType = DamageType.NORMAL;
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/res/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        if (this.hero.hasPermanentEffect(SwiftStrikeCounter.class) == 2) {
            this.hero.removePermanentEffectOfClass(SwiftStrikeCounter.class);
            this.hero.addEffect(new Combo(), this.hero);
        } else {
            this.hero.addEffect(new SwiftStrikeCounter(1), this.hero);
        }
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Every second attack, gain combo";
    }


    @Override
    public String getName() {
        return "Swift Strike";
    }
}
