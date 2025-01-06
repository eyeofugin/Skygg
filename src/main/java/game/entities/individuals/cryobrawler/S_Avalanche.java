package game.entities.individuals.cryobrawler;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Frost;

import java.util.List;

public class S_Avalanche extends Skill {

    public S_Avalanche(Hero hero) {
        super(hero);
        this.iconPath = "/icons/avalanche.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.2));
        this.targetType = TargetType.ALL_ENEMY;
        this.dmg = 2;
        this.effects = List.of(new Frost(1));
        this.damageType = DamageType.FROST;
        this.damageMode = DamageMode.MAGICAL;
        this.manaCost = 6;
        this.ultimate = true;
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gives Frost.";
    }


    @Override
    public String getName() {
        return "Avalanche";
    }
}
