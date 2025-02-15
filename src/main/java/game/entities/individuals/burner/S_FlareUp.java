package game.entities.individuals.burner;

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
import game.skills.changeeffects.effects.Burning;

import java.util.List;

public class S_FlareUp extends Skill {

    public S_FlareUp(Hero hero) {
        super(hero);
        this.iconPath = "/icons/flareup.png";
        addSubscriptions();
        setToInitial();
    }
    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.FAITH, 0.2));
        this.targetType = TargetType.SINGLE;
        this.damageType = DamageType.HEAT;
        this.damageMode = DamageMode.MAGICAL;
        this.distance = 2;
        this.dmg = 3;
        this.faithCost = 4;
    }

    @Override
    public int getAIRating(Hero target) {
        return target.getPermanentEffectStacks(Burning.class) / 3;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.removePermanentEffectOfClass(Burning.class);
    }

    @Override
    public String getName() {
        return "Flare Up";
    }



    @Override
    public String getDescriptionFor(Hero hero) {
        return "Deals an additional 3 dmg per burning stack.";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.BASE_DMG_CHANGES, new Connection(this, DmgChangesPayload.class, "dmgChanges"));
    }

    public void dmgChanges(DmgChangesPayload pl) {
        if (pl.caster != null && pl.caster.equals(this.hero) && pl.skill != null && pl.skill.equals(this) && pl.target != null) {
            int burnStack = pl.target.hasPermanentEffect(Burning.class);
            pl.dmg *= burnStack;
        }
    }
}
