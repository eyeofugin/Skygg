package game.entities.individuals.phoenixguy;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgChangesPayload;
import framework.connector.payloads.GlobalEffectChangePayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.globals.Heat;
import utils.MyMaths;

import java.util.List;

public class S_Fireblast extends Skill {

    public S_Fireblast(Hero hero) {
        super(hero);
        this.iconPath = "/icons/fireblast.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.FAITH, 0.6));
        this.targetType = TargetType.SINGLE;
        this.effects = List.of(new Burning(2));
        this.distance = 2;
        this.dmg = 7;
        this.damageType = DamageType.HEAT;
        this.damageMode = DamageMode.MAGICAL;
    }

    @Override
    public int getAIRating(Hero target) {
        return (target.getMissingLifePercentage() / 50) * 2;
    }

    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Double power if target under 50% life. Burns 2.";
    }
    @Override
    public String getName() {
        return "Fireblast";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.BASE_DMG_CHANGES, new Connection(this,  DmgChangesPayload.class, "dmgChanges"));
    }

    public void dmgChanges(DmgChangesPayload pl) {
        if (pl.caster != null && pl.target != null) {
            if (pl.caster == this.hero && (pl.target.getCurrentLifePercentage() < 50)) {
                pl.dmg*=2;
            }
        }
    }
}
