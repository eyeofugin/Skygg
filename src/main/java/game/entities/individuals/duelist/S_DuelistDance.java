package game.entities.individuals.duelist;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CastChangePayload;
import framework.connector.payloads.GlobalEffectChangePayload;
import framework.connector.payloads.OnPerformPayload;
import framework.connector.payloads.StartOfTurnPayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.globals.Heat;
import game.skills.changeeffects.statusinflictions.Dazed;

import java.util.List;

public class S_DuelistDance extends Skill {

    private boolean active = false;
    private boolean used = false;
    public S_DuelistDance(Hero hero) {
        super(hero);
        this.iconPath = "entities/duelist/icons/duelistdance.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.passive = true;
        this.active = false;
        this.used = false;
        this.ultimate = true;
        this.abilityType = AbilityType.ULT;
    }
    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.START_OF_TURN, new Connection(this, StartOfTurnPayload.class, "startOfTurn"));
        Connector.addSubscription(Connector.ON_PERFORM, new Connection(this, OnPerformPayload.class, "onPerform"));
        Connector.addSubscription(Connector.CAST_CHANGE, new Connection(this, CastChangePayload.class, "castChange"));
    }

    public void castChange(CastChangePayload pl) {
        if (this.active && pl.skill.hero.equals(this.hero) && pl.skill.getActionCost() > 0) {
            pl.skill.setActionCost(0);
            this.used = true;
        }
    }

    public void onPerform(OnPerformPayload pl) {
        if (this.active && pl.skill.hero.equals(this.hero) && this.used) {
            this.active = false;
        }
    }
    public void startOfTurn(StartOfTurnPayload pl) {
        this.active = true;
    }



    @Override
    public String getDescriptionFor(Hero hero) {
        return "The first action each turn is free.";
    }

    @Override
    public String getName() {
        return "Duelist Dance";
    }
}
