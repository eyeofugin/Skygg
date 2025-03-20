package game.objects.equipments.skills;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.StartOfMatchPayload;
import game.entities.Hero;
import game.objects.Equipment;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

public class S_PocketDarkness extends Skill {

    private boolean isUsedUp = false;

    public S_PocketDarkness(Equipment equipment) {
        super(null);
        this.equipment = equipment;
        this.iconPath = "icons/skills/pocketdarkness.png";
        setToInitial();
    }
    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE;
        this.distance = 5;
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.START_OF_MATCH, new Connection(this, StartOfMatchPayload.class, "start"));
    }
    public void start(StartOfMatchPayload pl) {
        this.isUsedUp = false;
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.addToStat(Stat.ACCURACY, -30);
        this.equipment.loseTempStat();
        this.isUsedUp = true;
    }

    @Override
    public boolean performCheck(Hero hero) {
        return this.equipment.isActive() && !this.isUsedUp;
    }

    public int getAIRating(Hero target) {
        return 2;
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Target -30 Accuracy.";
    }

    @Override
    public String getName() {
        return "Pocket Darkness";
    }
}
