package game.entities.individuals.cryobrawler;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class S_IceClaw extends Skill {

    public S_IceClaw(Hero hero) {
        super(hero);
        this.iconPath = "/icons/iceclaw.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.1),
                new Multiplier(Stat.MANA, 0.1),
                new Multiplier(Stat.MAGIC, 0.1));
        this.targetType = TargetType.SINGLE;
        this.distance = 2;
        this.dmg = 1;
        this.damageType = DamageType.FROST;
        this.damageMode = DamageMode.PHYSICAL;
        this.primary = true;
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_TRIGGER, new Connection(this, DmgTriggerPayload.class, "dmgTrigger"));
    }

    public void dmgTrigger(DmgTriggerPayload pl) {
        if (pl.cast.hero.equals(this.hero) && pl.cast instanceof S_IceClaw) {
            this.hero.addResource(Stat.CURRENT_MANA, Stat.MANA, pl.dmgDone / 2, this.hero);
        }
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Get 50% of damage done as mana.";
    }


    @Override
    public String getName() {
        return "Ice Claw";
    }
}
