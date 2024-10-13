package game.skills.backgroundskills;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.TargetType;

import java.util.ArrayList;
import java.util.List;

public class SimpleMove extends Skill {

    private static final int DISTANCE = 1;
    private static final int ACTION_COST = 0;
    private static final int CD_TOTAL = 5;

    public SimpleMove(Hero e) {
        super(e);
        this.name="simple_move";
        this.translation="Simple Move";
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
        SimpleMove cast = new SimpleMove(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Hero e) {
        return "Moves " + DISTANCE + " fields." ;
    }

    @Override
    public void individualResolve(Hero target) {
        int dist = target.position - this.Hero.position;
        int dir = this.Hero.enemy ? -1 : 1;
        this.Hero.arena.move(this.Hero, dist, dir);
    }
    @Override
    public int[] setupTargetMatrix() {
        List<Integer> resultList = new ArrayList<>();
        if (this.Hero.enemy) {
            int start = Math.max(4,this.Hero.position-DISTANCE);
            int end = Math.min(7,this.Hero.position+DISTANCE);
            for (int i = start; i <= end; i++) {
                if (this.Hero.arena.getAtPosition(i)!=null)
                    resultList.add(i);
            }
        } else {
            int start = Math.max(0,this.Hero.position-DISTANCE);
            int end = Math.min(3,this.Hero.position+DISTANCE);
            for (int i = start; i <= end; i++) {
                if (this.Hero.arena.getAtPosition(i)!=null)
                    resultList.add(i);
            }
        }
        return resultList.stream().mapToInt(i->i).filter(i->i!=this.Hero.position).toArray();
    }
}
