package game.entities.individuals.angelguy;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class S_PiercingLight extends Skill {

    public S_PiercingLight(Hero hero) {
        super(hero);
        this.iconPath = "/icons/piercinglight.png";
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.targetType = TargetType.SINGLE;
        this.dmg = 3;
        this.distance = 1;
        this.cdMax = 2;
        this.damageType = DamageType.LIGHT;
        this.damageMode = DamageMode.MAGICAL;
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public int getLethality() {
        return 20 * this.hero.getStat(Stat.CURRENT_HALO);
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "";
    }

    @Override
    public String getName() {
        return "Piercing Light";
    }
}
