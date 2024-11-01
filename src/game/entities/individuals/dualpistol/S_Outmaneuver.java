package game.entities.individuals.dualpistol;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.statusinflictions.Rooted;

import java.util.List;

public class S_Outmaneuver extends Skill {

    public S_Outmaneuver(Hero hero) {
        super(hero);
        this.iconPath = "/res/icons/outmanouver.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE;
        this.distance = 4;
        this.cdMax = 4;
    }

    @Override
    public void individualResolve(Hero target) {
        this.hero.arena.move(target, 1, target.isEnemy()?-1:1);
        if (this.hero.hasPermanentEffect(Combo.class) > 0) {
            this.hero.removePermanentEffectOfClass(Combo.class);
            target.addEffect(new Rooted(3), this.hero);
        }
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/res/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "pull 1; if combo: root";
    }

    @Override
    public String getName() {
        return "Outmaneuver";
    }
}
