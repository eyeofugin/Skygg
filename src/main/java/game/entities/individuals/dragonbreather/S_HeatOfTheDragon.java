package game.entities.individuals.dragonbreather;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CastChangePayload;
import framework.connector.payloads.GlobalEffectChangePayload;
import framework.states.Arena;
import game.entities.Hero;
import game.skills.AbilityType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.globals.Heat;
import game.skills.changeeffects.statusinflictions.Dazed;

import java.util.List;

public class S_HeatOfTheDragon extends Skill {

    public S_HeatOfTheDragon(Hero hero) {
        super(hero);
        this.iconPath = "entities/dragonbreather/icons/heat.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.SETUP);
        this.targetType = TargetType.ARENA;
        this.manaCost = 6;
        this.abilityType = AbilityType.TACTICAL;
    }

    @Override
    public int getAIArenaRating(Arena arena) {
        int rating = 0;
        for (Hero hero : arena.getAllLivingEntities().stream().filter(e->!e.isTeam2()).toList()) {
            rating += hero.getPermanentEffectStacks(Burning.class) / 2;
        }
        return rating;
    }
    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.GLOBAL_EFFECT_CHANGE, new Connection(this, GlobalEffectChangePayload.class, "globalEffectChange"));
    }

    public void globalEffectChange(GlobalEffectChangePayload pl) {
        if (pl.oldEffect instanceof Heat) {
            this.hero.addToStat(Stat.ENDURANCE, -10);
        } else if (pl.effect instanceof Heat) {
            this.hero.addToStat(Stat.ENDURANCE, 10);
        }
    }

    @Override
    public String getName() {
        return "Heat";
    }


    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.arena.setGlobalEffect(new Heat());
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Summon Heat Effect. +10 Endurance in heat.";
    }

}
