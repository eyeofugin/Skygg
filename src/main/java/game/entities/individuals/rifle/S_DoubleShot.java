package game.entities.individuals.rifle;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CastChangePayload;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.SkillTag;
import game.skills.TargetType;
import game.skills.changeeffects.effects.DoubleShot;

import java.util.List;

public class S_DoubleShot extends Skill {


    public S_DoubleShot(Hero hero) {
        super(hero);
        this.iconPath = "entities/rifle/icons/doubleshot.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.targetType = TargetType.SELF;
        this.possibleCastPositions = new int[]{0,1,2,3};
        this.cdMax = 2;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.addEffect(new DoubleShot(3), this.hero);
    }

    @Override
    public int getAIRating(Hero target) {
        return 3;
    }
    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.CAST_CHANGE, new Connection(this, CastChangePayload.class,"castChange"));
    }

    public void castChange(CastChangePayload castChangePayload) {
        Skill skill = castChangePayload.skill;
        if (skill != null && skill.hero.equals(this.hero) && skill instanceof S_Barrage) {
            skill.setCdMax(skill.getCdMax() -1);
        }
    }


    @Override
    public String getDescriptionFor(Hero hero) {
        return "Passive: -1 CD for Barrage. Active: Primary Skills count as two hits. For 3 turns";
    }

    @Override
    public String getName() {
        return "Weapon Upgrade: Double Shot";
    }
}
