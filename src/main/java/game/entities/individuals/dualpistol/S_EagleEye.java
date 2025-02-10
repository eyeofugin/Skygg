package game.entities.individuals.dualpistol;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CriticalTriggerPayload;
import framework.connector.payloads.GlobalEffectChangePayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.effects.Lucky;
import game.skills.changeeffects.globals.Heat;

import java.util.List;

public class S_EagleEye extends Skill {

    public S_EagleEye(Hero hero) {
        super(hero);
        this.iconPath = "/icons/eagleeye.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.BUFF);
        this.targetType = TargetType.SELF;
        this.effects = List.of(new Lucky(3));
        this.cdMax = 5;
    }

    @Override
    public int getAIRating(Hero target) {
        return this.hero.getCurrentLifePercentage()/20;
    }
    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.CRITICAL_TRIGGER, new Connection(this, CriticalTriggerPayload.class, "critTrigger"));
    }

    public void critTrigger(CriticalTriggerPayload pl) {
        if (pl.cast.hero.equals(this.hero)) {
            this.hero.addEffect(new Combo(), this.hero);
        }
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gain Lucky. Whenever you crit, gain combo.";
    }
    @Override
    public String getName() {
        return "Eagle Eye";
    }
}
