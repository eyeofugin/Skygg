package game.entities.individuals.angelguy;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import java.util.List;

public class S_LightSpikes extends Skill {

    public S_LightSpikes(Hero hero) {
        super(hero);
        this.iconPath = "entities/angelguy/icons/lightspikes.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.ALL_TARGETS;
        this.tags = List.of(SkillTag.TACTICAL);
        this.possibleTargetPositions = new int[]{4,5,6,7};
        this.possibleCastPositions = new int[]{0,1,2,3};
        this.dmg = 5;
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.5));
        this.cdMax = 2;
        this.damageMode = DamageMode.MAGICAL;
    }

    @Override
    public int getAIRating(Hero target) {
        if (this.hero.getCurrentLifePercentage() < 25) {
            return 2;
        }
        if (this.hero.getCurrentLifePercentage() < 50) {
            return 1;
        }
        return 0;
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Heal for each " + Stat.HALO.getIconString() + " stack 10% of the damage dealt";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_TRIGGER, new Connection(this, DmgTriggerPayload.class, "dmgTrigger"));
    }
    public void dmgTrigger(DmgTriggerPayload pl) {
        if (this.equals(pl.cast)) {
            int halo = this.hero.getStat(Stat.CURRENT_HALO);
            if (halo > 0) {
                this.hero.heal(pl.cast.hero, pl.dmgDone*10*halo/100, this, false);
            }
        }
    }

    @Override
    public String getName() {
        return "Light Spikes";
    }
}
