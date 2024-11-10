package game.entities.individuals.darkmage;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;
import java.util.Map;

public class S_DarkSchemes extends Skill {

    public S_DarkSchemes(Hero hero) {
        super(hero);
        this.iconPath = "/icons/darkschemes.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PEEL);
        this.targetType = TargetType.SINGLE;
        this.distance = 4;
        this.cdMax = 5;
        this.manaCost = 10;
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});

    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        for (Map.Entry<Stat, Integer> entry : target.getStatChanges().entrySet()) {
            target.addToStat(entry.getKey(), -2*entry.getValue());
        }
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Reverts targets stat changes";
    }

    @Override
    public String getName() {
        return "Dark Schemes";
    }
}
