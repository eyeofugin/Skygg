package game.entities.individuals.eldritchguy;

import game.entities.Hero;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.statusinflictions.Dazed;

import java.util.List;

public class S_HorrificGlare extends Skill {

    public S_HorrificGlare(Hero hero) {
        super(hero);
        this.iconPath = "/icons/horrificglare.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE;
        this.tags = List.of(SkillTag.PEEL);
        this.effects = List.of(new Dazed(1));
        this.distance = 3;
        this.manaCost = 3;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.addToStat(Stat.ENDURANCE, -1);
    }

    @Override
    public int getAIRating(Hero target) {
        return 2;
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "-1 Endurance, Daze(1).";
    }

    @Override
    public String getName() {
        return "Horrific Glare";
    }
}
