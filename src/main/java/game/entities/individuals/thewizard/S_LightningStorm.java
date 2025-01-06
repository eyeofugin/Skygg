package game.entities.individuals.thewizard;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CastChangePayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.statusinflictions.Dazed;

import java.util.List;

public class S_LightningStorm extends Skill {

    public S_LightningStorm(Hero hero) {
        super(hero);
        this.iconPath = "/icons/lightningstorm.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.5));
        this.targetType = TargetType.ALL_ENEMY;
        this.dmg = 2;
        this.manaCost = 8;
        this.damageType = DamageType.HEAT;
        this.damageMode = DamageMode.MAGICAL;
        this.comboEnabled = true;
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});

    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Deals heat damage to all enemies";
    }

    @Override
    public String getName() {
        return "Lightning Storm";
    }
}
