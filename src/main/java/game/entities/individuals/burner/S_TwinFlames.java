package game.entities.individuals.burner;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;
import utils.MyMaths;

import java.util.List;

public class S_TwinFlames extends Skill {

    public S_TwinFlames(Hero hero) {
        super(hero);
        this.iconPath = "/icons/twinflames.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.FAITH, 0.4));
        this.effects = List.of(new Burning(1));
        this.targetType = TargetType.TWO_RDM;
        this.dmg = 2;
        this.damageType = DamageType.HEAT;
        this.damageMode = DamageMode.MAGICAL;
        this.faithCost = 2;
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Burns";
    }


    @Override
    public String getName() {
        return "Twin Flames";
    }
}
