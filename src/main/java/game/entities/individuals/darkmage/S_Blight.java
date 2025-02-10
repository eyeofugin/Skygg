package game.entities.individuals.darkmage;

import game.entities.Hero;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Blight;

import java.util.List;

public class S_Blight extends Skill {

    public S_Blight(Hero hero) {
        super(hero);
        this.iconPath = "/icons/darkblast.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.targetType = TargetType.SINGLE;
        this.damageMode = DamageMode.MAGICAL;
        this.damageType = DamageType.TRUE;
        this.distance = 3;
        this.primary = true;
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public int getDmg(Hero target) {
        return target.getStat(Stat.ENDURANCE) / 2;
    }

    @Override
    public String getDmgOrHealString() {
        return "True damage equal to 50% of targets endurance";
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return " ";
    }

    @Override
    public String getName() {
        return "Dark Blast";
    }
}
