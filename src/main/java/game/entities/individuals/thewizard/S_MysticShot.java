package game.entities.individuals.thewizard;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class S_MysticShot extends Skill {

    public S_MysticShot(Hero hero) {
        super(hero);
        this.iconPath = "/icons/mysticshot.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.3));
        this.targetType = TargetType.SINGLE;
        this.distance = 3;
        this.dmg = 2;
        this.damageType = DamageType.MAGIC;
        this.canMiss = false;
        this.primary = true;
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});

    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Never misses";
    }


    @Override
    public String getName() {
        return "Mystic Shot";
    }
}
