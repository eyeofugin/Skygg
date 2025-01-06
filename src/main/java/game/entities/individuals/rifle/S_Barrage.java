package game.entities.individuals.rifle;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.statusinflictions.Injured;

import java.util.List;

public class S_Barrage extends Skill {

    public S_Barrage(Hero hero) {
        super(hero);
        this.iconPath = "/icons/barrage.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.2));
        this.effects = List.of(new Injured(1));
        this.targetType = TargetType.ENEMY_LINE;
        this.dmg = 3;
        this.distance = 3;
        this.damageType = DamageType.NORMAL;
        this.damageMode = DamageMode.PHYSICAL;
        this.cdMax = 3;
    }

    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Hits enemies on line, injures";
    }

    @Override
    public String getName() {
        return "Barrage";
    }
}
