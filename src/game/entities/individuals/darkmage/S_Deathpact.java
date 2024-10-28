package game.entities.individuals.darkmage;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DeathTriggerPayload;
import game.entities.Hero;
import game.skills.Skill;

import java.util.List;

public class S_Deathpact extends Skill {

    public S_Deathpact(Hero hero) {
        super(hero);
        this.iconPath = "/res/icons/deathpact.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }
    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.passive = true;
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/res/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Whenever an opponent dies, the ally on that position gains all stats of the dead opponent";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DEATH_TRIGGER, new Connection(this, DeathTriggerPayload.class, "deathTrigger"));
    }

    public void deathTrigger(DeathTriggerPayload pl) {
        if (pl.dead.isEnemy() != this.hero.isEnemy()) {
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
            target.applyStatChanges(pl.dead.getStats());
        }
    }

    @Override
    public String getName() {
        return "Death Pact";
    }
}
