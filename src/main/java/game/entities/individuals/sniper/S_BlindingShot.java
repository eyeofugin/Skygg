package game.entities.individuals.sniper;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.statusinflictions.Blinded;

import java.util.List;

public class S_BlindingShot extends Skill {

    public S_BlindingShot(Hero hero) {
        super(hero);
        this.iconPath = "/icons/blindingshot.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE;
        this.distance = 4;
        this.cdMax = 3;
        this.damageType = DamageType.NORMAL;
        this.dmg = 1;
        this.dmgMultipliers = List.of(new Multiplier(Stat.FINESSE, 0.1));
        this.effects = List.of(new Blinded(2));
    }

    @Override
    public int getAIRating(Hero target) {
        return 4;
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "Blinds";
    }

    @Override
    public String getName() {
        return "Blinding Shot";
    }
}
