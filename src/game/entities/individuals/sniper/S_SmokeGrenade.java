package game.entities.individuals.sniper;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.effects.SmokeScreen;

public class S_SmokeGrenade extends Skill {

    public S_SmokeGrenade(Hero hero) {
        super(hero);
        this.iconPath = "/res/icons/smokegrenade.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.ALL_ALLY;
        this.cdMax = 4;
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/res/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        if (this.hero.hasPermanentEffect(Combo.class) > 0) {
            this.hero.removePermanentEffectOfClass(Combo.class);
            this.hero.addEffect(new SmokeScreen(3), this.hero);
        } else {
            this.hero.addEffect(new SmokeScreen(2), this.hero);
        }
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "Get +20 Evasion for 2 turns; if combo: 3 turns";
    }


    @Override
    public String getName() {
        return "Smoke Grenade";
    }
}
