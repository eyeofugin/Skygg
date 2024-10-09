package game.skills.backgroundskills;

import framework.Logger;
import game.entities.Entity;
import game.skills.Skill;
import game.skills.changeeffects.effects.Adaptability;

public class GUA_Adaptability extends Skill {
    public GUA_Adaptability(Entity e) {
        super(e);
        this.name="gua_adaptability";
        this.translation="Adaptability";
        this.description= "getDescription()";
        this.passive = true;
    }
    @Override
    public Skill getCast() {
        GUA_Adaptability cast = new GUA_Adaptability(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Entity e) {
        return "Gain an adaptability stack whenever receiving damage. Lose 1 stack at the start of your turn.";
    }

    @Override
    public void dmgTrigger(Entity target, Skill cast, int doneDamage) {
        if (this.entity == target) {
            Logger.logLn("GUA_Adaptability.DmgTrigger:" + this.name + " adds adaptability: " + target.name);
            this.entity.addEffect(new Adaptability(), null);
        }
    }
    @Override
    public void startOfTurn() {
        super.startOfTurn();
        Logger.log(this.entity.name + ".GUA_Adaptability.StartOfTurn: decrease adaptability");
        this.entity.decreaseEffectStack(Adaptability.class);
    }
}
