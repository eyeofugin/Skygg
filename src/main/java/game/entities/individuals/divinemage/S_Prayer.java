package game.entities.individuals.divinemage;

import game.entities.Hero;
import game.skills.Resource;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class S_Prayer extends Skill {

    public S_Prayer(Hero hero) {
        super(hero);
        this.iconPath = "/icons/prayer.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SELF;
        this.targetResources = List.of(new Resource(Stat.CURRENT_FAITH, Stat.FAITH, 9));
        this.primary = true;
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});

    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gain 9" + Stat.FAITH.getIconString();
    }
    @Override
    public String getName() {
        return "Prayer";
    }
}
