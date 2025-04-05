package game.entities.individuals.phoenixguy;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgChangesPayload;
import framework.connector.payloads.GlobalEffectChangePayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.globals.Heat;
import utils.MyMaths;

import java.util.List;

public class S_Fireblast extends Skill {

    public S_Fireblast(Hero hero) {
        super(hero);
        this.iconPath = "entities/phoenixguy/icons/fireblast.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.dmgMultipliers = List.of(new Multiplier(Stat.FAITH, 0.6));
        this.targetType = TargetType.SINGLE;
        this.effects = List.of(new Burning(2));
        this.possibleCastPositions = new int[]{1,2};
        this.possibleTargetPositions = new int[]{4,5};
        this.dmg = 7;
        this.damageMode = DamageMode.MAGICAL;
    }

    @Override
    public int getAIRating(Hero target) {
        return (target.getMissingLifePercentage() / 50) * 2;
    }



    @Override
    public String getDescriptionFor(Hero hero) {
        return "Double power if target under 50% life. Burns 2.";
    }
    @Override
    public String getName() {
        return "Fireblast";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.BASE_DMG_CHANGES, new Connection(this,  DmgChangesPayload.class, "dmgChanges"));
    }

    public void dmgChanges(DmgChangesPayload pl) {
        if (pl.caster != null && pl.target != null) {
            if (pl.caster == this.hero && (pl.target.getCurrentLifePercentage() < 50)) {
                pl.dmg*=2;
            }
        }
    }
}
