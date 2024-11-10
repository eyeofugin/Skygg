package game.entities.individuals.dragonbreather;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;
import utils.MyMaths;

import java.util.List;

public class S_DragonBreath extends Skill {

    public S_DragonBreath(Hero hero) {
        super(hero);
        this.iconPath = "/icons/dragonbreath.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.2));
        this.targetType = TargetType.LINE;
        this.distance = 2;
        this.dmg = 7;
        this.damageType = DamageType.MAGIC;
        this.manaCost =6;
        this.cdMax = 2;
    }

    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int magic = this.hero.getStat(Stat.MAGIC);
        if (MyMaths.success(magic + 50)) {
            target.addEffect(new Burning(1), this.hero);
        }
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "(50+"+Stat.MAGIC.getIconString()+")% Chance to burn";
    }

    @Override
    public String getName() {
        return "Dragon Breath";
    }
}
