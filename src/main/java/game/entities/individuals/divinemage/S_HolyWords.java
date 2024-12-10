package game.entities.individuals.divinemage;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.Resource;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class S_HolyWords extends Skill {

    public S_HolyWords(Hero hero) {
        super(hero);
        this.iconPath = "/icons/holywords.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE_ALLY;
        this.heal = 0;
        this.healMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.5));
        this.cdMax = 1;
        this.faithCost = 6;
        this.distance = 2;
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "";
    }

    @Override
    public String getName() {
        return "Holy Words";
    }
}
