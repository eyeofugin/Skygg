package game.entities.individuals.duelist;

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

public class S_SwirlingBlades extends Skill {

    public S_SwirlingBlades(Hero hero) {
        super(hero);
        this.iconPath = "/icons/swirlingblades.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.5));
        this.targetType = TargetType.SINGLE;
        this.distance = 2;
        this.dmg = 2;
        this.cdMax = 2;
        this.damageType = DamageType.NORMAL;
        this.damageMode = DamageMode.PHYSICAL;
        this.comboEnabled = true;
        this.countAsHits = 2;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        if (this.hero.hasPermanentEffect(Combo.class) > 0) {
            this.hero.removePermanentEffectOfClass(Combo.class);
            target.addEffect(new Dazed(2), this.hero);
        }
    }

    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Combo: Daze(2)";
    }

    @Override
    public String getName() {
        return "Swirling Blades";
    }
}
