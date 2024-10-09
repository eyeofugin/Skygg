package game.skills.backgroundskills;

import framework.Logger;
import game.entities.Entity;
import game.skills.Skill;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Advantage;

import java.util.ArrayList;
import java.util.List;

public class LEA_Mobilize extends Skill {
    private static final int ACTION_COST = 1;
    private static final int CD_TOTAL = 2;
    private static final int MOVE_MAX = 2;

    public LEA_Mobilize(Entity e) {
        super(e);
        this.name="lea_mobilize";
        this.translation="Mobilize";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.isMove = true;
        this.canMiss = false;
        this.targetType= TargetType.SINGLE_ALLY;
        this.distance = MOVE_MAX;
        this.actionCost = ACTION_COST;
        this.cdMax = CD_TOTAL;
        this.tags = List.of(AiSkillTag.REGROUP);
    }
    @Override
    public Skill getCast() {
        LEA_Mobilize cast = new LEA_Mobilize(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Entity e) {
        return "Moves up to " + MOVE_MAX + " fields forward. Each passed ally gains advantage.";
    }
    @Override
    public void individualResolve(Entity target) {
        int dist = target.position - this.entity.position;
        int dir = this.entity.enemy ? -1 : 1;
        this.entity.arena.move(this.entity, dist, dir);
        for (int i = 1; i < 1 + dist; i++) {
            this.entity.arena.getAtPosition(this.entity.position + (i*dir))
                    .addEffect(new Advantage(1), this.entity);
        }
    }
    @Override
    public int[] setupTargetMatrix() {
        List<Integer> resultList = new ArrayList<>();
        if (this.entity.enemy) {
            int start = this.entity.position-1;
            for (int i = start; i > 3 && (start-i)<=MOVE_MAX; i--) {
                resultList.add(i);
            }
            return resultList.stream().mapToInt(i->i).toArray();
        } else {
            int start = this.entity.position+1;
            for (int i = start; i < 4 && (i-start)<=MOVE_MAX; i++) {
                resultList.add(i);
            }
            return resultList.stream().mapToInt(i->i).toArray();
        }
    }
}
