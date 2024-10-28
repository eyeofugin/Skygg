package game.entities.individuals.sniper;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;

import java.util.List;

public class S_GotYourBack extends Skill {

    public S_GotYourBack(Hero hero) {
        super(hero);
        this.iconPath = "/res/icons/gotyourback.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE_ALLY;
        this.distance = 2;
        this.cdMax = 3;
        this.effects = List.of(new Combo());
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/res/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.addToStat(Stat.SPEED, 3);
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gives Combo, +3SPD";
    }


    @Override
    public String getName() {
        return "Got Your Back";
    }
}
