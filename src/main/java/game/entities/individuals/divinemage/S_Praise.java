package game.entities.individuals.divinemage;

import game.entities.Hero;
import game.skills.Resource;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;
import java.util.Optional;

public class S_Praise extends Skill {

    public S_Praise(Hero hero) {
        super(hero);
        this.iconPath = "/icons/praise.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SELF;
        this.primary = true;
        this.targetResources = List.of(new Resource(Stat.CURRENT_FAITH, Stat.FAITH, 1));
        this.faithGain = true;
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});

    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        List<Hero> over50 = this.hero.team.getHeroesAsList().stream().filter(e->e.getCurrentLifePercentage() > 50).toList();
        this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, 2*over50.size(), this.hero);
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gain 1 Faith. Gain 2 Faith for each ally over 50% life";
    }
    @Override
    public String getName() {
        return "Praise";
    }
}
