package game.entities.individuals.angelguy;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class S_PiercingLight extends Skill {

    public S_PiercingLight(Hero hero) {
        super(hero);
        this.iconPath = "/res/icons/piercinglight.png";
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.targetType = TargetType.LINE;
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.3));
        this.dmg = 3;
        this.distance = 4;
        this.cdMax = 2;
        this.faithCost = 6;
        this.damageType = DamageType.MAGIC;
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/res/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});

    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "";
    }

    @Override
    public void addSubscriptions() {

    }

    @Override
    public String getName() {
        return "Piercing Light";
    }
}
