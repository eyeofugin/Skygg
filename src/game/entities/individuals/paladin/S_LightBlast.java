package game.entities.individuals.paladin;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.AxeSwingCounter;
import game.skills.changeeffects.statusinflictions.Bleeding;

import java.util.List;

public class S_LightBlast extends Skill {

    public S_LightBlast(Hero hero) {
        super(hero);
        this.iconPath = "/res/icons/lightblast.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG, SkillTag.PEEL);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.3));
        this.targetType = TargetType.SINGLE;
        this.distance = 2;
        this.dmg = 3;
        this.cdMax = 3;
        this.faithCost = 5;
        this.damageType = DamageType.MAGIC;
    }
    @Override
    protected void initAnimation() {

    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.arena.move(target, 1, target.isEnemy()?1:-1);
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Push 1 back";
    }

    @Override
    public void addSubscriptions() {

    }

    @Override
    public String getName() {
        return "Light Blast";
    }
}
