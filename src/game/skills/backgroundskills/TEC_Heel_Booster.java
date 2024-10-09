package game.skills.backgroundskills;

import game.entities.Entity;
import game.skills.Skill;
import game.skills.TargetType;

import java.util.ArrayList;
import java.util.List;

public class TEC_Heel_Booster extends Skill {

    private static final int DISTANCE = 2;
    private static final int ACTION_COST = 1;
    private static final int CD_TOTAL = 2;

    public TEC_Heel_Booster(Entity e) {
        super(e);
        this.name="tec_heel_booster";
        this.translation="Heel Booster";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.SINGLE_ALLY;
        this.actionCost = ACTION_COST;
        this.cdMax = CD_TOTAL;
        this.distance = DISTANCE;
        this.tags = List.of(AiSkillTag.REGROUP);
    }
    @Override
    public Skill getCast() {
        TEC_Heel_Booster cast = new TEC_Heel_Booster(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Entity e) {
        return "Moves " + DISTANCE + " fields." ;
    }

    @Override
    public void individualResolve(Entity target) {
        int dist = target.position - this.entity.position;
        int dir = this.entity.enemy ? -1 : 1;
        this.entity.arena.move(this.entity, dist, dir);
    }
    @Override
    public int[] setupTargetMatrix() {
        List<Integer> resultList = new ArrayList<>();
        if (this.entity.enemy) {
            int start = Math.max(4,this.entity.position-DISTANCE);
            int end = Math.min(7,this.entity.position+DISTANCE);
            for (int i = start; i <= end; i++) {
                if (this.entity.arena.getAtPosition(i)!=null)
                    resultList.add(i);
            }
        } else {
            int start = Math.max(0,this.entity.position-DISTANCE);
            int end = Math.min(3,this.entity.position+DISTANCE);
            for (int i = start; i <= end; i++) {
                if (this.entity.arena.getAtPosition(i)!=null)
                    resultList.add(i);
            }
        }
        return resultList.stream().mapToInt(i->i).filter(i->i!=this.entity.position).toArray();
    }
}
