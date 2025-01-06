package game.entities.individuals.dualpistol;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class S_DoubleShot extends Skill {

    public S_DoubleShot(Hero hero) {
        super(hero);
        this.iconPath = "/icons/doubleshot.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE;
        this.distance = 2;
        this.primary = true;
        this.countAsHits = 2;
        this.dmg = 3;
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.5));
        this.damageType = DamageType.NORMAL;
        this.damageMode = DamageMode.PHYSICAL;
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "2 Hits.";
    }
    @Override
    public String getName() {
        return "Double Shot";
    }
}
