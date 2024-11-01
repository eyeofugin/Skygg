package game.entities.individuals.dualpistol;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;

import java.util.List;

public class S_DoubleShot extends Skill {

    private int shot = 0;
    public S_DoubleShot(Hero hero) {
        super(hero);
        this.iconPath = "/res/icons/doubleshot.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE;
        this.distance = 3;
        this.cdMax = 2;
        this.countAsHits = 2;
        this.dmg = 3;
        this.dmgMultipliers = List.of(new Multiplier(Stat.FINESSE, 0.5));
        this.shot = 0;
        this.damageType = DamageType.NORMAL;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        shot++;
        if (this.hero.hasPermanentEffect(Combo.class) > 0) {
            target.addToStat(Stat.STAMINA, -1);
            if (shot==2) {
                this.hero.removePermanentEffectOfClass(Combo.class);
            }
        }
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/res/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "2 Hits; if combo: each hit reduces target normal armor by 1";
    }
    @Override
    public String getName() {
        return "Double Shot";
    }
}
