package game.entities.individuals.longsword;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgChangesPayload;
import framework.connector.payloads.DmgTriggerPayload;
import game.entities.Hero;
import game.skills.AbilityType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.globals.Heat;

public class S_SupremeDefense extends Skill {

    public S_SupremeDefense(Hero hero) {
        super(hero);
        this.iconPath = "entities/longsword/icons/supremedefense.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SELF;
        this.cdMax = 6;
        this.ultimate = true;
        this.abilityType = AbilityType.ULT;
    }




    @Override
    public String getDescriptionFor(Hero hero) {
        return "Passive: Reduces dmg that is less than 10% of max life to 0. Active: Double Endurance and Stamina for 3 Turns.";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_CHANGES, new Connection(this, DmgChangesPayload.class, "dmgChanges"));
    }
    public void dmgChanges(DmgChangesPayload pl) {
        if (this.hero.equals(pl.target)) {
            int tenPercent = this.hero.getStat(Stat.LIFE) / 10;
            if (pl.dmg < tenPercent) {
                pl.dmg *= 0;
            }
        }
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.addToStat(Stat.ENDURANCE, target.getStat(Stat.ENDURANCE));
        target.addToStat(Stat.STAMINA, target.getStat(Stat.STAMINA));
    }
    @Override
    public String getName() {
        return "Supreme Defense";
    }
}
