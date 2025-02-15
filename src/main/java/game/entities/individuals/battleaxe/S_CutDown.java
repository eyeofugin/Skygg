package game.entities.individuals.battleaxe;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgChangesPayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.globals.Heat;
import game.skills.changeeffects.statusinflictions.Bleeding;

import java.util.List;

public class S_CutDown extends Skill {

    public S_CutDown(Hero hero) {
        super(hero);
        this.iconPath = "/icons/cutdown.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.2));
        this.targetType = TargetType.SINGLE;
        this.distance = 1;
        this.dmg = 9;
        this.damageMode = DamageMode.PHYSICAL;
        this.damageType = DamageType.NORMAL;
        this.primary = true;
    }

    @Override
    public int getAIRating(Hero target) {
        return 1;
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_CHANGES, new Connection(this, DmgChangesPayload.class, "dmgChanges"));
    }

    public void dmgChanges(DmgChangesPayload pl) {
        if (pl.target != null && pl.caster != null
            && pl.caster.equals(this.hero) && pl.skill instanceof S_CutDown &&
                pl.target.getStat(Stat.SHIELD) > 0) {
            pl.dmg *= 2;
        }
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Double damage if the target has a shield";
    }

    @Override
    public String getName() {
        return "Cut Down";
    }
}
