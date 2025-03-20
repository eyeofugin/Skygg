package game.entities.individuals.divinemage;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.ExcessResourcePayload;
import framework.connector.payloads.GlobalEffectChangePayload;
import game.entities.Hero;
import game.skills.AbilityType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Invincible;
import game.skills.changeeffects.globals.Heat;
import game.skills.changeeffects.globals.HolyLight;

import java.util.List;

public class S_SummonTheLight extends Skill {

    public S_SummonTheLight(Hero hero) {
        super(hero);
        this.iconPath = "entities/divinemage/icons/summonthelight.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.ARENA;
        this.faithCost = 5;
        this.abilityType = AbilityType.TACTICAL;
    }
    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.EXCESS_RESOURCE, new Connection(this, ExcessResourcePayload.class, "excess"));
    }

    public void excess(ExcessResourcePayload pl) {
        if (pl.source != null && pl.source.equals(this.hero) && this.hero.arena.globalEffect instanceof HolyLight) {
            pl.target.shield(pl.excess, this.hero);
        }
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.arena.setGlobalEffect(new HolyLight());
    }
    @Override
    public int getAIRating(Hero target) {
        return 2;
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Passive: During holy light, excess healing you do is converted to shield. Active: Summon holy light";
    }


    @Override
    public String getName() {
        return "Summon the Light";
    }
}
