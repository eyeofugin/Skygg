package game.entities.individuals.burner;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.EndOfRoundPayload;
import framework.states.Arena;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.globals.Heat;

import java.util.List;

public class S_Heat extends Skill {

    public S_Heat(Hero hero) {
        super(hero);
        this.iconPath = "entities/burner/icons/heat.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.ARENA;
        this.possibleCastPositions = new int[]{0,1,2,3};
        this.faithCost = 5;
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
        Connector.addSubscription(Connector.END_OF_ROUND, new Connection(this, EndOfRoundPayload.class, "endOfRound"));
    }

    public void endOfRound(EndOfRoundPayload pl) {
        if (pl.arena.globalEffect instanceof Heat) {
            this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, 2, this.hero);
        }
    }
    @Override
    public void applySkillEffects(Hero target) {
        this.hero.arena.setGlobalEffect(new Heat());
    }

    @Override
    public String getName() {
        return "Heat";
    }

    @Override
    public String getUpperDescriptionFor(Hero hero) {
        return "Active: Summon the global Heat effect.";
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Passive: +2"+Stat.FAITH.getIconString()+" per turn during Heat.";
    }
}
