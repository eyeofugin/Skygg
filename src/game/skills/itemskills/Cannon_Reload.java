package game.skills.itemskills;

import game.entities.Entity;
import game.objects.equipments.basic.BlasterCannon;
import game.skills.Skill;
import game.skills.TargetType;

import java.util.List;

public class Cannon_Reload extends Skill {

    public Cannon_Reload(Entity entity) {
        super(entity);
        this.name="cannon_reload";
        this.translation="Reload";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.SELF;
        this.actionCost = 1;
        this.distance = 2;
        this.tags = List.of(AiSkillTag.RESTOCK);
    }
    @Override
    public Skill getCast() {
        Cannon_Reload cast = new Cannon_Reload(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Entity e) {
        return "Cannon Reload1";
    }

    @Override
    protected void individualResolve(Entity target) {
        if (this.entity.getPrimary() instanceof BlasterCannon cannon) {
            cannon.reload();
        }
    }
}
