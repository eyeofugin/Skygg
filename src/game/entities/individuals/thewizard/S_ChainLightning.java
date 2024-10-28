package game.entities.individuals.thewizard;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class S_ChainLightning extends Skill {

    public S_ChainLightning(Hero hero) {
        super(hero);
        this.iconPath = "/res/icons/chainlightning.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.4));
        this.targetType = TargetType.LINE;
        this.distance = 4;
        this.dmg = 3;
        this.cdMax = 2;
        this.manaCost = 5;
        this.damageType = DamageType.MAGIC;
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/res/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});

    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "";
    }

    @Override
    public String getName() {
        return "Chain Lightning";
    }
}
