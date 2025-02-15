package game.entities.individuals.firedancer;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.GlobalEffectChangePayload;
import framework.connector.payloads.OnPerformPayload;
import framework.connector.payloads.UpdatePayload;
import game.entities.Hero;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.effects.Gifted;
import game.skills.changeeffects.globals.Heat;

import java.util.Iterator;
import java.util.List;

public class S_GiftOfTheFirstFlame extends Skill {

    public S_GiftOfTheFirstFlame(Hero hero) {
        super(hero);
        this.iconPath = "/icons/giftofthefirstflame.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.BUFF);
        this.targetType = TargetType.SELF;
        this.faithCost = 7;
    }



    @Override
    public int getAIRating(Hero target) {
        if (this.hero.getCurrentLifePercentage() > 75) {
            return 5;
        }
        if (this.hero.getCurrentLifePercentage() < 25) {
            return -1;
        }
        return 2;
    }
    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.UPDATE, new Connection(this, UpdatePayload.class, "update"));

    }

    public void update(UpdatePayload pl) {
        if (this.hero.getPermanentEffectStacks(Burning.class) < 5 && this.hero.getPermanentEffectStacks(Gifted.class) > 0) {
            this.hero.removePermanentEffectOfClass(Gifted.class);
        }
        if (this.hero.getPermanentEffectStacks(Burning.class) > 4 && this.hero.getPermanentEffectStacks(Gifted.class) < 1) {
            this.hero.addEffect(new Gifted(), this.hero);
        }
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Passive: As long as you have at least 5 burn stacks, primary skills count as 2 hits. Active: Get 2 Burn stacks, remove all other debuffs.";
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.addEffect(new Burning(2), this.hero);
        Iterator<Effect> iter = this.hero.getEffects().stream()
                .filter(effect->effect.type.equals(Effect.ChangeEffectType.DEBUFF) && !(effect instanceof Burning))
                .toList().iterator();
        while(iter.hasNext()) {
            this.hero.removePermanentEffectOfClass(iter.next().getClass());
            iter.remove();
        }
    }

    @Override
    public String getName() {
        return "Gift of the first Flame";
    }
}
