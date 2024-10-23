package game.entities.individuals.phoenixguy;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.changeeffects.effects.Burning;

import java.util.List;

public class S_HonorTheFirstFlame extends Skill {

    public S_HonorTheFirstFlame(Hero hero) {
        super(hero);
        this.name = "Honor the Flames";
        this.iconPath = "/res/icons/honorthefirstflame.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.RESTOCK);
        this.passive = true;
    }
    @Override
    protected void initAnimation() {}

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Whenever an enemy is dealt dmg by burning, get that much favor";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.EFFECT_DMG_TRIGGER, new Connection(this, DmgTriggerPayload.class, "dmgTrigger"));
    }

    public void dmgTrigger(DmgTriggerPayload pl) {
        if (pl.target != null && pl.effect != null && pl.effect.getClass().equals(Burning.class)) {
            if (pl.target.isEnemy() != this.hero.isEnemy()) {
                this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, pl.dmgDone);
            }
        }
    }
}
