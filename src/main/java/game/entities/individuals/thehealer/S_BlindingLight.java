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
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE;
        this.distance = 3;
        this.cdMax = 3;
        this.manaCost = 5;
        this.effects = List.of(new Blinded(3));
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
        return "Blinding Light";
    }
}
