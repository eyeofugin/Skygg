package game.entities.individuals.thewizard;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CastChangePayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.statusinflictions.Dazed;

import java.util.List;

public class S_ArcaneBombardment extends Skill {

    public S_ArcaneBombardment(Hero hero) {
        super(hero);
        this.iconPath = "/icons/arcanebombardment.png";
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
        this.cdMax = 10;
        this.manaCost = 25;
        this.damageType = DamageType.MAGIC;
        this.comboEnabled = true;
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});

    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "combo: daze enemy";
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        if (this.hero.hasPermanentEffect(Combo.class) > 0) {
            this.hero.removePermanentEffectOfClass(Combo.class);
        }
    }
    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.CAST_CHANGE, new Connection(this, CastChangePayload.class, "castChange"));
    }

    public void castChange(CastChangePayload pl) {
        if (pl.skill.equals(this)) {
            if (this.hero.hasPermanentEffect(Combo.class) > 0) {
                this.effects.add(new Dazed(2));
            }
        }
    }

    @Override
    public String getName() {
        return "Arcane Bombardment";
    }
}
