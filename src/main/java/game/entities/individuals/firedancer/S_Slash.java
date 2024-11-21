package game.entities.individuals.firedancer;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;
import utils.MyMaths;

import java.util.List;

public class S_Slash extends Skill {

    public S_Slash(Hero hero) {
        super(hero);
        this.iconPath = "/icons/slash.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.FAITH, 0.1),
                new Multiplier(Stat.FINESSE, 0.1));
        this.targetType = TargetType.SINGLE;
        this.distance = 2;
        this.dmg = 3;
        this.damageType = DamageType.NORMAL;
        this.primary = true;
        this.faithGain = true;
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int magic = this.hero.getStat(Stat.MAGIC);
        int finesse = this.hero.getStat(Stat.FINESSE);
        if (MyMaths.success(magic + finesse)) {
            target.addEffect(new Burning(1), this.hero);
        }
    }

    @Override
    public String getName() {
        return "Slash";
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "("+Stat.FINESSE.getIconString()+"+"+Stat.MAGIC.getIconString()+")% chance to burn. Gain 50% damage as "+Stat.FAITH.getIconString();
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_TRIGGER, new Connection(this, DmgTriggerPayload.class, "dmgTrigger"));
    }

    public void dmgTrigger(DmgTriggerPayload pl) {
        if (pl.cast != null &&
                pl.cast.hero != null &&
                pl.cast.hero.equals(this.hero) &&
                pl.cast.equals(this)) {
            this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, (int) (0.5 * pl.dmgDone));
        }
    }
}
