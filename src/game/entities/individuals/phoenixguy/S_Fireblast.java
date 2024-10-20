package game.entities.individuals.phoenixguy;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgChangesPayload;
import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class S_Fireblast extends Skill {

    public S_Fireblast(Hero hero) {
        super(hero);
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.name = "Fireblast";
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.FAITH, 0.15));
        this.targetType = TargetType.SINGLE;
        this.distance = 3;
        this.dmg = 8;
        this.damageType = Stat.HEAT;
    }

    protected void initAnimation() {
        this.hero.anim.setupAnimation("res/sprites/dev/action_w.png", this.name, new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Mid dmg, double power if target under 50% life. 10+ FAITH% to burn, get 3 favor";
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.addToStat(Stat.CURRENT_FAITH, 3);
    }
    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.BASE_DMG_CHANGES, new Connection(this, "dmgChanges"));
    }

    public void dmgChanges(DmgChangesPayload pl) {
        if (pl.caster != null && pl.target != null) {
            if (pl.caster == this.hero && (pl.target.getCurrentLifePercentage() < 50)) {
                pl.dmg*=2;
            }
        }
    }
}