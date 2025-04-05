package game.entities.individuals.darkmage;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DeathTriggerPayload;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;

import java.util.List;

public class S_Deathpact extends Skill {

    public S_Deathpact(Hero hero) {
        super(hero);
        this.iconPath = "entities/darkmage/icons/deathpact.png";
        addSubscriptions();
        setToInitial();
    }
    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.ULT, SkillTag.PASSIVE);
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "Whenever an enemy dies, the ally on the mirrored position gains all stats of the dead enemy.";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DEATH_TRIGGER, new Connection(this, DeathTriggerPayload.class, "deathTrigger"));
    }

    public void deathTrigger(DeathTriggerPayload pl) {
        if (pl.dead.isTeam2() != this.hero.isTeam2()) {
            int targetPosition = switch (pl.dead.getPosition()) {
                case 0 -> 7;
                case 1 -> 6;
                case 2 -> 5;
                case 3 -> 4;
                case 4 -> 3;
                case 5 -> 2;
                case 6 -> 1;
                default -> 0;
            };
            Hero target = this.hero.arena.getAtPosition(targetPosition);
            target.addToStats(pl.dead.getStats());
        }
    }

    @Override
    public String getName() {
        return "Death Pact";
    }
}
