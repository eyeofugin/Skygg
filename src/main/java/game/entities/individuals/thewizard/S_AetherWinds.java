package game.entities.individuals.thewizard;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.GlobalEffectChangePayload;
import game.entities.Hero;
import game.skills.AbilityType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.globals.AetherWinds;
import game.skills.changeeffects.globals.Heat;

import java.util.List;

public class S_AetherWinds extends Skill {

    public S_AetherWinds(Hero hero) {
        super(hero);
        this.iconPath = "entities/thewizard/icons/aetherwinds.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.SETUP);
        this.targetType = TargetType.ARENA;
        this.possibleCastPositions = new int[]{0,1,2,3};
        this.manaCost = 4;
        this.abilityType = AbilityType.TACTICAL;
    }


    @Override
    public int getAIRating(Hero target) {
        return 3;
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.GLOBAL_EFFECT_CHANGE, new Connection(this, GlobalEffectChangePayload.class, "globalEffectChange"));
    }

    public void globalEffectChange(GlobalEffectChangePayload pl) {
        if (pl.oldEffect instanceof AetherWinds) {
            this.hero.addToStat(Stat.EVASION, -10);
        } else if (pl.effect instanceof AetherWinds) {
            this.hero.addToStat(Stat.EVASION, 10);
        }
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Summon Aether Winds. +10 Evasion during Aether Winds";
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.arena.setGlobalEffect(new AetherWinds());
    }

    @Override
    public String getName() {
        return "Aether Winds";
    }
}
