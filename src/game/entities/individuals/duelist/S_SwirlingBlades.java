package game.entities.individuals.duelist;

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

import java.util.List;

public class S_SwirlingBlades extends Skill {

    public S_SwirlingBlades(Hero hero) {
        super(hero);
        this.iconPath = "/res/icons/swirlingblades.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.FINESSE, 0.5));
        this.targetType = TargetType.SINGLE;
        this.distance = 2;
        this.dmg = 2;
        this.cdMax = 2;
        this.damageType = DamageType.NORMAL;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        if (this.hero.hasPermanentEffect(Combo.class) > 0) {
            this.hero.removePermanentEffectOfClass(Combo.class);
        }
    }

    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/res/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "if combo: counts as 2 hits";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.CAST_CHANGE, new Connection(this, CastChangePayload.class, "castChange"));
    }

    public void castChange(CastChangePayload pl) {
        if (pl.skill.equals(this)) {
            if (this.hero.hasPermanentEffect(Combo.class) > 0) {
                this.countAsHits = 2;
            }
        }
    }

    @Override
    public String getName() {
        return "Swirling Blades";
    }
}
