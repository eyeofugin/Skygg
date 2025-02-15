package game.entities.individuals.thehealer;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.TargetType;
import game.skills.changeeffects.statusinflictions.Blinded;

import java.util.List;

public class S_BlindingLight extends Skill {

    public S_BlindingLight(Hero hero) {
        super(hero);
        this.iconPath = "/icons/blindinglight.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE;
        this.distance = 4;
        this.manaCost = 5;
        this.effects = List.of(new Blinded(2));
    }

    @Override
    public int getAIRating(Hero target) {
        return 4;
    }



    @Override
    public String getDescriptionFor(Hero hero) {
        return "Blinds(2)";
    }


    @Override
    public String getName() {
        return "Blinding Light";
    }
}
