package game.entities.individuals.firedancer;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;
import utils.MyMaths;

import java.util.List;

public class S_FlameLasso extends Skill {

    public S_FlameLasso(Hero hero) {
        super(hero);
        this.iconPath = "/res/icons/flamelasso.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG, SkillTag.PEEL);
        this.dmgMultipliers = List.of(new Multiplier(Stat.FAITH, 0.1),
                new Multiplier(Stat.FAITH, 0.1));
        this.targetType = TargetType.SINGLE;
        this.distance = 4;
        this.dmg = 1;
        this.damageType = DamageType.MAGIC;
        this.faithCost = 10;
        this.cdMax = 5;
    }

    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/res/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Pull 2";
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.arena.move(target, 2, target.isEnemy()?-1:1);
    }

    @Override
    public String getName() {
        return "Flame Lasso";
    }

    @Override
    public void addSubscriptions() {

    }
}
