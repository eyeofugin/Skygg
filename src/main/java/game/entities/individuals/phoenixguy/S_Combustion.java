package game.entities.individuals.phoenixguy;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgChangesPayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Burning;

import java.util.List;

public class S_Combustion extends Skill {


    public S_Combustion(Hero hero) {
        super(hero);
        this.iconPath = "entities/phoenixguy/icons/combustion.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{1,2,3};
        this.possibleTargetPositions = new int[]{4,5,6};
        this.dmgMultipliers = List.of(new Multiplier(Stat.FAITH, 0.2));
        this.dmg = 1;
        this.damageMode = DamageMode.MAGICAL;
        this.faithCost = 4;
    }

    @Override
    public int getAIRating(Hero target) {
        return target.getPermanentEffectStacks(Burning.class);
    }



    @Override
    public String getDescriptionFor(Hero hero) {
        return "+20% damage for each burning stack";
    }
    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.BASE_DMG_CHANGES, new Connection(this, DmgChangesPayload.class,"dmgChanges"));
    }

    @Override
    public String getName() {
        return "Combustion";
    }

    public void dmgChanges(DmgChangesPayload pl) {
        if (pl.caster != null && pl.target != null) {
            if (pl.caster == this.hero && pl.target.hasPermanentEffect(Burning.class) > 0) {
                pl.dmg += pl.dmg * (pl.target.hasPermanentEffect(Burning.class) * 20) / 100;
            }
        }
    }
}
