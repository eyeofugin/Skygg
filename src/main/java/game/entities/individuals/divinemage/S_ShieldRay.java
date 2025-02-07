package game.entities.individuals.divinemage;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

public class S_ShieldRay extends Skill {

    public S_ShieldRay(Hero hero) {
        super(hero);
        this.iconPath = "/icons/shieldray.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.LINE;
        this.distance = 2;
        this.faithCost = 3;
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.shield(getShield(), this.hero);
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public int getShield() {
        return this.hero.getStat(Stat.CURRENT_FAITH) * 50 / 100;
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Allies get a shield equal to your 50%" + Stat.FAITH.getIconString();
    }

    @Override
    public String getName() {
        return "Shield Ray";
    }
}
