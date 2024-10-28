package game.entities.individuals.rifle;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;

public class S_Retreat extends Skill {

    public S_Retreat(Hero hero) {
        super(hero);
        this.iconPath = "/res/icons/retreat.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE_ALLY_BEHIND;
        this.distance = 1;
        this.cdMax = 2;
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/res/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int targetPosition = target.getPosition();
        this.hero.arena.moveTo(this.hero, targetPosition);
        if (this.hero.hasPermanentEffect(Combo.class)>0) {
            this.hero.removePermanentEffectOfClass(Combo.class);
            this.hero.addToStat(Stat.EVASION, 5);
        }
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Move 1 back If combo: +10 Evasion";
    }


    @Override
    public String getName() {
        return "Retreat";
    }
}