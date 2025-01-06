package game.entities.individuals.eldritchguy;

import game.entities.Hero;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.statusinflictions.Dazed;

public class S_TentacleGrab extends Skill {

    public S_TentacleGrab(Hero hero) {
        super(hero);
        this.iconPath = "/icons/tentaclegrab.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE;
        this.distance = 3;
        this.damageMode = DamageMode.PHYSICAL;
        this.damageType = DamageType.DARK;
        this.manaCost = 6;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.arena.move(target, 1, target.isTeam2()?-1:1);
    }

    @Override
    public int getDmg(Hero target) {
        return target.getStat(Stat.LIFE) * 10 / 100;
    }
    @Override
    public String getDmgOrHealString() {
        return "10% Target Max Life";
    }
    @Override
    public int getAIRating(Hero target) {
        int rating = 0;
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
        return "Pull 1.";
    }

    @Override
    public String getName() {
        return "Tentacle Grab";
    }
}
