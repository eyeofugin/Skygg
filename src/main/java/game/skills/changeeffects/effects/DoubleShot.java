package game.skills.changeeffects.effects;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.CastChangePayload;
import game.entities.individuals.rifle.S_Mobilize;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.SkillTag;

public class DoubleShot extends Effect {
    public static String ICON_STRING = "DBL";
    public DoubleShot(int turns) {
        this.turns = turns;
        this.iconString = ICON_STRING;
        this.name = "Double Shot";
        this.stackable = false;
        this.description = "Primary Skills count as 2 hits.";
        this.type = ChangeEffectType.BUFF;
    }

    public static String getStaticIconString() {
        return "[" + ICON_STRING + "]";
    }
    @Override
    public Effect getNew() {
        return new DoubleShot(this.turns);
    }
    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.CAST_CHANGE, new Connection(this, CastChangePayload.class,"castChange"));
    }

    public void castChange(CastChangePayload castChangePayload) {
        Skill skill = castChangePayload.skill;
        if (skill != null && skill.hero.equals(this.hero) && skill.tags.contains(SkillTag.PRIMARY)) {
            skill.setCountsAsHits(2);
        }
    }
}
