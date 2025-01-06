package game.entities.individuals.duelist;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgTriggerPayload;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.TargetType;
import game.skills.changeeffects.statusinflictions.Taunted;

import java.util.List;

public class S_GiveMeYourWorst extends Skill {

    public S_GiveMeYourWorst(Hero hero) {
        super(hero);
        this.iconPath = "/icons/givemeyourworst.png";
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
        this.effects = List.of(new Taunted(2));
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Taunt(2)";
    }


    @Override
    public String getName() {
        return "Give me your worst";
    }
}
