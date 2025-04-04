package game.entities.individuals.paladin;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.StartOfMatchPayload;
import framework.connector.payloads.StartOfTurnPayload;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class S_HoliestShield extends Skill {

    public S_HoliestShield(Hero hero) {
        super(hero);
        this.iconPath = "entities/paladin/icons/holiestshield.png";
        addSubscriptions();
        setToInitial();
        this.hero.shield(this.hero.getStat(Stat.LIFE) * 10 / 100, this.hero);
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.ULT);
        this.faithCost = 6;
        this.targetType = TargetType.SELF;
        this.possibleCastPositions = new int[]{0,1,2,3};
    }



    @Override
    public String getDescriptionFor(Hero hero) {
        return "Passive: Get a 5% Life shield that regenerates completely at the start of your turn. Active: +20%(Current life) Endurance";
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.addToStat(Stat.ENDURANCE, target.getStat(Stat.CURRENT_LIFE)/2);
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(this.hero.getId() + Connector.START_OF_TURN, new Connection(this, StartOfTurnPayload.class, "startOfTurn"));
        Connector.addSubscription(Connector.START_OF_MATCH, new Connection(this, StartOfMatchPayload.class, "startOfMatch"));
    }
    public void startOfTurn(StartOfTurnPayload pl) {
        int shield = this.hero.getStat(Stat.SHIELD);
        int shiningShield = this.hero.getStat(Stat.LIFE) * 5 / 100;
        if (shield < shiningShield) {
            this.hero.changeStatTo(Stat.SHIELD, shiningShield);
        }
    }
    public void startOfMatch(StartOfMatchPayload pl) {
        this.startOfTurn(null);
    }

    @Override
    public String getName() {
        return "Holy Shield";
    }
}
