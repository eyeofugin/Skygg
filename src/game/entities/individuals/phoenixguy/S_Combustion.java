package game.entities.individuals.phoenixguy;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgChangesPayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;

import java.util.List;

public class S_Combustion extends Skill {


    public S_Combustion(Hero hero) {
        super(hero);
        this.name = "Combustion";
        this.iconPath = "/res/icons/combustion.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.targetType = TargetType.SINGLE;
        this.dmgMultipliers = List.of(new Multiplier(Stat.FAITH, 0.15));
        this.distance = 3;
        this.dmg = 4;
        this.damageType = DamageType.MAGIC;
        this.cdMax = 1;
        this.faithCost = 10;
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/res/sprites/action_w.png", this.name, new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Low dmg, + 20% for each burning stack";
    }
    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.BASE_DMG_CHANGES, new Connection(this, DmgChangesPayload.class,"dmgChanges"));
    }

    public void dmgChanges(DmgChangesPayload pl) {
        if (pl.caster != null && pl.target != null) {
            if (pl.caster == this.hero && pl.target.hasPermanentEffect(Burning.class) > 0) {
                pl.dmg += pl.dmg * (pl.target.hasPermanentEffect(Burning.class) * 20) / 100;
            }
        }
    }
}
