package game.skills.backgroundskills;

import framework.Logger;
import game.entities.Entity;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.Stat;

public class SEN_Sith_Rage extends Skill {
    public SEN_Sith_Rage(Entity e) {
        super(e);
        this.name="sen_sith_rage";
        this.translation="Sith Rage";
        this.description= "getDescription()";
        this.passive = true;
    }
    @Override
    public Skill getCast() {
        SEN_Sith_Rage cast = new SEN_Sith_Rage(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Entity e) {
        return "Gain an rage stack whenever receiving damage.";
    }


    @Override
    public void dmgTrigger(Entity target, Skill cast, int doneDamage) {
        if (this.entity == target) {
            Logger.logLn(this.entity.name + ".sen_sithrage.dmgTrigger");
            this.entity.addEffect(Effect.newStatChange(1, Stat.STRENGTH), null);
            this.entity.addEffect(Effect.newStatChange(1, Stat.WILLPOWER), null);
            this.entity.addEffect(Effect.newStatChange(-1, Stat.ACCURACY), null);
        }
    }

}
