package game.skills.backgroundskills;

import framework.Logger;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Advantage;

import java.util.ArrayList;
import java.util.List;

public class LEA_Mobilize extends Skill {
    private static final int ACTION_COST = 1;
    private static final int CD_TOTAL = 2;
    private static final int MOVE_MAX = 2;

    public LEA_Mobilize(Hero e) {
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
        LEA_Mobilize cast = new LEA_Mobilize(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Hero e) {
        return "Moves up to " + MOVE_MAX + " fields forward. Each passed ally gains advantage.";
    }
    @Override
    public void individualResolve(Hero target) {
        int dist = target.position - this.Hero.position;
        int dir = this.Hero.enemy ? -1 : 1;
        this.Hero.arena.move(this.Hero, dist, dir);
        for (int i = 1; i < 1 + dist; i++) {
            this.Hero.arena.getAtPosition(this.Hero.position + (i*dir))
                    .addEffect(new Advantage(1), this.Hero);
        }
    }
    @Override
    public int[] setupTargetMatrix() {
        List<Integer> resultList = new ArrayList<>();
        if (this.Hero.enemy) {
            int start = this.Hero.position-1;
            for (int i = start; i > 3 && (start-i)<=MOVE_MAX; i--) {
                resultList.add(i);
            }
            return resultList.stream().mapToInt(i->i).toArray();
        } else {
            int start = this.Hero.position+1;
            for (int i = start; i < 4 && (i-start)<=MOVE_MAX; i++) {
                resultList.add(i);
            }
            return resultList.stream().mapToInt(i->i).toArray();
        }
    }
}
