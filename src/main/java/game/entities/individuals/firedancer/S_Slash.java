package game.entities.individuals.firedancer;

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
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.statusinflictions.Bleeding;
import utils.MyMaths;

import java.util.List;

public class S_Slash extends Skill {

    public S_Slash(Hero hero) {
        super(hero);
        this.iconPath = "/icons/slash.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.45));
        this.targetType = TargetType.SINGLE;
        this.distance = 2;
        this.dmg = 9;
        this.damageType = DamageType.NORMAL;
        this.damageMode = DamageMode.PHYSICAL;
        this.comboEnabled = true;
        this.primary = true;
        this.faithGain = true;
    }


    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int magic = this.hero.getStat(Stat.MAGIC);
        int power = this.hero.getStat(Stat.POWER);
        target.addEffect(new Bleeding(1), this.hero);
        this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, 2, this.hero);
        if (this.hero.hasPermanentEffect(Combo.class)> 0) {
            this.hero.removePermanentEffectOfClass(Combo.class);
            this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, 2, this.hero);
        }
    }

    @Override
    public String getName() {
        return "Slash";
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Bleeds. +2 Favor per hit. Combo: +2 Favor.";
    }

}
