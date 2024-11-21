package game.entities.individuals.dualpistol;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.statusinflictions.Rooted;

public class S_Outmaneuver extends Skill {

    public S_Outmaneuver(Hero hero) {
        super(hero);
        this.iconPath = "/icons/outmaneuver.png";
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
        this.hero.arena.move(target, 1, target.isTeam2()?-1:1);
        if (this.hero.hasPermanentEffect(Combo.class) > 0) {
            this.hero.removePermanentEffectOfClass(Combo.class);
            target.addEffect(new Rooted(3), this.hero);
        }
    }

    @Override
    public int getAIRating(Hero target) {
        int rating = 0;
        if (this.hero.hasPermanentEffect(Combo.class) > 0) {
            rating++;
        }
        if (target.getPosition() == target.team.getFirstPosition()) {
            return --rating;
        }
        if (target.getCurrentLifePercentage() < 50) {
            rating += 2;
        }
        if (this.hero.arena.getAtPosition(target.team.getFirstPosition()).getCurrentLifePercentage() < 50) {
            rating -= 2;
        }
        return rating;
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "pull 1. if combo: root";
    }

    @Override
    public String getName() {
        return "Outmaneuver";
    }
}
