package game.entities.individuals.thehealer;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class S_DivineRay extends Skill {

    public S_DivineRay(Hero hero) {
        super(hero);
        this.iconPath = "/res/icons/divineray.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE;
        this.distance = 2;
    }
    @Override
    public void individualResolve(Hero target) {
        int amnt = this.hero.getStat(Stat.MAGIC) * 20 / 100;
        if (target.isEnemy() == this.hero.isEnemy()) {
            this.heal = amnt;
            this.baseHealChanges(target, this.hero);
            target.heal(this.hero, amnt, this);
        } else {
            this.dmg = amnt;
            this.baseDamageChanges(target, this.hero);
            int doneDmg = target.damage(this.hero, amnt, DamageType.MAGIC, 0, this);
            this.fireDmgTrigger(target, this, doneDmg);
        }
        this.applySkillEffects(target);
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/res/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});

    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "ally? heal 20%mag: 20%mag divine dmg";
    }

    @Override
    public void addSubscriptions() {

    }

    @Override
    public String getName() {
        return "Divine Ray";
    }
}
