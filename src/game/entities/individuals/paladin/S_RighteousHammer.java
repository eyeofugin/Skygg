package game.entities.individuals.paladin;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.AxeSwingCounter;
import game.skills.changeeffects.effects.Exalted;
import game.skills.changeeffects.effects.RighteousHammerCounter;
import game.skills.changeeffects.statusinflictions.Bleeding;
import game.skills.changeeffects.statusinflictions.Dazed;

import java.util.List;

public class S_RighteousHammer extends Skill {

    public S_RighteousHammer(Hero hero) {
        super(hero);
        this.iconPath = "/res/icons/righteoushammer.png";
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.FORCE, 0.15));
        this.targetType = TargetType.SINGLE;
        this.distance = 2;
        this.dmg = 3;
        this.damageType = DamageType.NORMAL;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        if (this.hero.hasPermanentEffect(RighteousHammerCounter.class) == 2) {
            this.hero.removePermanentEffectOfClass(RighteousHammerCounter.class);
            target.addEffect(new Dazed(3), this.hero);
        } else {
            this.hero.addEffect(new RighteousHammerCounter(1), this.hero);
        }
        this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, 5);
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/res/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});

    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Every second attack dazes target, get 5 favor";
    }

    @Override
    public void addSubscriptions() {

    }

    @Override
    public String getName() {
        return "Righteous Hammer";
    }
}
