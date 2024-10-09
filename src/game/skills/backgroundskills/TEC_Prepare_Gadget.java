package game.skills.backgroundskills;

import game.entities.Entity;
import game.skills.Skill;

public class TEC_Prepare_Gadget extends Skill {
    public TEC_Prepare_Gadget(Entity e) {
        super(e);
        this.name="tec_prepare_gadget";
        this.translation="Prepare Gadget";
        this.description= "getDescription()";
        this.passive = true;
        //TODO impl
    }
    @Override
    public Skill getCast() {
        TEC_Prepare_Gadget cast = new TEC_Prepare_Gadget(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Entity e) {
        return "At the start of battle switch this ability with an unused one.";
    }
}
