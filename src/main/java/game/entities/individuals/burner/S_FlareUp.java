package game.entities.individuals.burner;

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
        initAnimation();
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
        this.dmg = 1;
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
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Deals an additional 2 dmg per burning stack.";
    }

//    @Override
//    public void addSubscriptions() {
//        Connector.addSubscription(Connector.BASE_DMG_CHANGES, new Connection(this, DmgChangesPayload.class, "dmgChanges"));
//    }
//
//    public void dmgChanges(DmgChangesPayload pl) {
//        if (pl.caster != null && pl.caster.equals(this.hero) && pl.skill != null && pl.skill.equals(this) && pl.target != null) {
//            int burnStack = pl.target.hasPermanentEffect(Burning.class);
//            pl.dmg *= burnStack;
//        }
//    }
}
