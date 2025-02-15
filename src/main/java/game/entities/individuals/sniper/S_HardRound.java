package game.entities.individuals.sniper;

import game.entities.Hero;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.TargetType;
import game.skills.changeeffects.effects.RegenBoost;
import game.skills.changeeffects.statusinflictions.Injured;

public class S_HardRound extends Skill {

    public S_HardRound(Hero hero) {
        super(hero);
        this.iconPath = "/icons/hardround.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE;
        this.dmg = 8;
        this.distance = 4;
        this.damageMode = DamageMode.PHYSICAL;
        this.damageType = DamageType.NORMAL;
        this.primary = true;
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
