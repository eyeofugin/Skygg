package game.entities.individuals.cryobrawler;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.effects.Frost;

import java.util.List;

public class S_FrostBreath extends Skill {

    public S_FrostBreath(Hero hero) {
        super(hero);
        this.iconPath = "/icons/frostbreath.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.effects = List.of(new Frost(1));
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.2),
                new Multiplier(Stat.MANA, 0.3));
        this.targetType = TargetType.LINE;
        this.distance = 2;
        this.dmg = 5;
        this.damageType = DamageType.FROST;
        this.damageMode = DamageMode.MAGICAL;
        this.manaCost = 4;
    }

    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.addToStat(Stat.SPEED, -1);
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "-1 Speed. Give Frost Stack.";
    }

    @Override
    public String getName() {
        return "Frost Breath";
    }
}
