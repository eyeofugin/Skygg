package game.entities.individuals.thehealer;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.EndOfRoundPayload;
import framework.connector.payloads.ExcessResourcePayload;
import framework.states.Arena;
import game.entities.Hero;
import game.skills.AbilityType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.globals.HolyLight;

import java.util.List;

public class S_HolyLight extends Skill {

    public S_HolyLight(Hero hero) {
        super(hero);
        this.iconPath = "entities/thehealer/icons/holylight.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.SETUP);
        this.targetType = TargetType.ARENA;
        this.possibleCastPositions = new int[]{0,1,2,3};
        this.manaCost = 5;
        this.abilityType = AbilityType.TACTICAL;
    }
    @Override
    public int getAIArenaRating(Arena arena) {
        return 2;
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.arena.setGlobalEffect(new HolyLight());
    }
    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.END_OF_ROUND, new Connection(this, EndOfRoundPayload.class, "endOfRound"));
    }

    public void endOfRound(EndOfRoundPayload pl) {
        if (pl.arena.globalEffect instanceof HolyLight) {
            this.hero.addResource(Stat.CURRENT_MANA, Stat.MANA, 1, this.hero);
        }
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "Passive: +1 Mana per Round during Holy Light. Active: Summon Holy Light";
    }


    @Override
    public String getName() {
        return "Holy Light";
    }
}
