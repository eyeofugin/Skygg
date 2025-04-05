package game.entities.individuals.sniper;

import game.entities.Hero;
import game.skills.*;
import game.skills.changeeffects.effects.RegenBoost;
import game.skills.changeeffects.statusinflictions.Injured;

import java.util.List;

public class S_HardRound extends Skill {

    public S_HardRound(Hero hero) {
        super(hero);
        this.iconPath = "entities/sniper/icons/hardround.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.PRIMARY);
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{4,5};
        this.dmg = 8;
        this.damageMode = DamageMode.PHYSICAL;
    }




    @Override
    public String getDescriptionFor(Hero hero) {
        return " ";
    }

    @Override
    public String getName() {
        return "Hard Round";
    }
}
