package game.skills.backgroundskills;

import framework.Logger;
import game.entities.Entity;
import game.skills.Skill;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Cover;
import game.skills.changeeffects.effects.Shielded;

import java.util.ArrayList;
import java.util.List;

public class MAR_Retreat extends Skill {
    private static final int ACTION_COST = 1;
    private static final int CD_TOTAL = 3;
    private static final int DISTANCE = 1;
    private static final int TURNS = 2;

    public MAR_Retreat(Entity e) {
        super(e);
        this.name="mar_retreat";
        this.translation="Retreat";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.SINGLE_ALLY;
        this.actionCost = ACTION_COST;
        this.cdMax = CD_TOTAL;
        this.distance = DISTANCE;
        this.effects = List.of(new Shielded());
        this.tags = List.of(AiSkillTag.REGROUP);
    }
    @Override
    public Skill getCast() {
        MAR_Retreat cast = new MAR_Retreat(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Entity e) {
        return "~ moves up to " + DISTANCE + " fields and gains shielded";
    }
    @Override
    public void individualResolve(Entity target) {
        int dist = Math.abs(target.position - this.entity.position);
        int dir = this.entity.enemy ? 1 : -1;
        this.entity.arena.move(this.entity, dist, dir);
        this.applySkillEffects(target);
    }
    @Override
    public int[] setupTargetMatrix() {
        List<Integer> resultList = new ArrayList<>();
        if (this.entity.enemy) {
            int start = this.entity.position+1;
            for (int i = start; i > 3 && i <= 7 && Math.abs(start-i)<=this.distance; i--) {
                if (this.entity.arena.getAtPosition(i)!=null)
                    resultList.add(i);
            }
            return resultList.stream().mapToInt(i->i).toArray();
        } else {
            int start = this.entity.position-1;
            for (int i = start; i < 4 && i>=0 && Math.abs(i-start)<=this.distance; i++) {
                if (this.entity.arena.getAtPosition(i)!=null)
                    resultList.add(i);
            }
            return resultList.stream().mapToInt(i->i).toArray();
        }
    }
}
