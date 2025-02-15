package game.entities.individuals.thewizard;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.statusinflictions.Injured;
import utils.MyMaths;

import java.util.List;

public class S_LightningBolt extends Skill {

    public S_LightningBolt(Hero hero) {
        super(hero);
        this.iconPath = "/icons/lightningbolt.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.7));
        this.targetType = TargetType.SINGLE;
        this.distance = 2;
        this.damageType = DamageType.HEAT;
        this.damageMode = DamageMode.MAGICAL;
        this.primary = true;
    }


    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        if (MyMaths.success(50)) {
            target.addEffect(new Burning(1), this.hero);
        }
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Can burn";
    }


    @Override
    public String getName() {
        return "Lightning Bolt";
    }
}
