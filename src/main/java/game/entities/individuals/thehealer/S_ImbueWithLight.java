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
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.ALL_ALLY;
        this.manaCost = 6;
        this.healMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.2),
                new Multiplier(Stat.MANA, 0.2),
                new Multiplier(Stat.LIFE, 0.2));
    }
    @Override
    public int getAIRating(Hero target) {
        return target.getMissingLifePercentage() / 50;
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
