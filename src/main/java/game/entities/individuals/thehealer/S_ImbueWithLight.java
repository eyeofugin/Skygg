package game.entities.individuals.thehealer;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class S_ImbueWithLight extends Skill {

    public S_ImbueWithLight(Hero hero) {
        super(hero);
        this.iconPath = "/icons/imbuewithlight.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.ALL_ALLY;
        this.cdMax = 2;
        this.manaCost = 10;
        
        this.heal = 0;
        this.healMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.3));
    }
    @Override
    public int getAIRating(Hero target) {
        return target.getMissingLifePercentage() / 50;
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
        return "Imbue with light";
    }
}
