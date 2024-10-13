package game.skills.backgroundskills;

import framework.Logger;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.changeeffects.effects.Adaptability;

public class GUA_Adaptability extends Skill {
    public GUA_Adaptability(Hero e) {
        super(e);
        this.name="gua_adaptability";
        this.translation="Adaptability";
        this.description= "getDescription()";
        this.passive = true;
    }
    @Override
    public Skill getCast() {
        GUA_Adaptability cast = new GUA_Adaptability(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Hero e) {
        return "Gain an adaptability stack whenever receiving damage. Lose 1 stack at the start of your turn.";
    }

    @Override
    public void dmgTrigger(Hero target, Skill cast, int doneDamage) {
        if (this.Hero == target) {
            Logger.logLn("GUA_Adaptability.DmgTrigger:" + this.name + " adds adaptability: " + target.name);
            this.Hero.addEffect(new Adaptability(), null);
        }
    }
    @Override
    public void startOfTurn() {
        super.startOfTurn();
        Logger.log(this.Hero.name + ".GUA_Adaptability.StartOfTurn: decrease adaptability");
        this.Hero.decreaseEffectStack(Adaptability.class);
    }
}
