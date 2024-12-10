package game.entities.individuals.phoenixguy;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgChangesPayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;
import utils.MyMaths;

import java.util.List;

public class S_Fireblast extends Skill {

    public S_Fireblast(Hero hero) {
        super(hero);
        this.iconPath = "/icons/fireblast.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.FAITH, 0.15));
        this.targetType = TargetType.SINGLE;
        this.distance = 2;
        this.dmg = 8;
        this.damageType = DamageType.MAGIC;
        this.faithGain = true;
    }

    @Override
    public int getAIRating(Hero target) {
        return (target.getMissingLifePercentage() / 50) * 2;
    }

    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Mid dmg, double power if target under 50% life. 20+ Magic% to burn, get 3 favor";
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int magic = this.hero.getStat(Stat.MAGIC);
        if (MyMaths.success(magic + 20)) {
            target.addEffect(new Burning( 1), this.hero);
        }
        this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, 3);
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
