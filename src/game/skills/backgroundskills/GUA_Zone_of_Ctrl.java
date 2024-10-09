package game.skills.backgroundskills;

import framework.Logger;
import game.entities.Entity;
import game.skills.Skill;
import game.skills.TargetMode;

public class GUA_Zone_of_Ctrl extends Skill {

    public GUA_Zone_of_Ctrl(Entity e) {
        super(e);
        this.name="gua_zone_of_ctrl";
        this.translation="Zone of Control";
        this.description= "getDescription()";
        this.passive = true;
    }
    @Override
    public Skill getCast() {
        GUA_Zone_of_Ctrl cast = new GUA_Zone_of_Ctrl(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Entity e) {
        return "Enemies up to 2 fields away can only target ~.";
    }

    @Override
    public TargetMode getTargetMode(Skill targetingSkill) {
        Entity caster = targetingSkill.entity;
        if (caster.enemy == this.entity.enemy) {
            return TargetMode.NORMAL;
        }
        int dist = Math.abs(caster.position-this.entity.position);
        Logger.log(this.entity.name + ".GUA_ZoneOfCtrl.getTargetMode: " );
        if (dist<=2) {
            Logger.logLn("MUST");
            return TargetMode.MUST;
        } else {
            Logger.logLn("NORMAL");
            return TargetMode.NORMAL;
        }
    }
}
