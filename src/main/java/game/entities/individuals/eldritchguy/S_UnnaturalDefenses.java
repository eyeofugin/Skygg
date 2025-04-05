package game.entities.individuals.eldritchguy;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.Stat;

import java.util.List;

//TODO: DT Changes
public class S_UnnaturalDefenses extends Skill {

    public S_UnnaturalDefenses(Hero hero) {
        super(hero);
        this.iconPath = "entities/eldritchguy/icons/unnaturaldefenses.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL, SkillTag.PASSIVE);
    }




    @Override
    public String getDescriptionFor(Hero hero) {
        return "+2 Endurance when receiving non-normal damage";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_TRIGGER, new Connection(this, DmgTriggerPayload.class, "dmgTrigger"));
    }

    public void dmgTrigger(DmgTriggerPayload pl) {
        if (this.hero.equals(pl.target)) {
            this.hero.addToStat(Stat.ENDURANCE, 2);
        }
    }

    @Override
    public String getName() {
        return "Unnatural Defenses";
    }
}
