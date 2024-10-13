package game.skills.backgroundskills;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Advantage;

import java.util.ArrayList;
import java.util.List;

public class MAR_Advance extends Skill {
    private static final int ACTION_COST = 1;
    private static final int CD_TOTAL = 3;
    private static final int DISTANCE = 1;
    private static final int TURNS = 2;

    public MAR_Advance(Hero e) {
        super(e);
        this.name="mar_advance";
        this.translation="Advance";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType= TargetType.SINGLE_ALLY;
        this.actionCost = ACTION_COST;
        this.cdMax = CD_TOTAL;
        this.distance = DISTANCE;
        this.casterEffects = List.of(new Advantage(TURNS));
        this.tags = List.of(AiSkillTag.REGROUP);
    }

    @Override
    public Skill getCast() {
        MAR_Advance cast = new MAR_Advance(this.Hero);
        cast.copyFrom(this);
        return cast;
    }

    @Override
    public String getDescriptionFor(Hero e) {
        return "~ moves up to " + DISTANCE + " fields and gains advantage for " + TURNS +" turns.";
    }
    @Override
    protected void individualResolve(Hero target) {
        int dist = Math.abs(target.position - this.Hero.position);
        int dir = this.Hero.enemy ? -1 : 1;
        this.Hero.arena.move(this.Hero, dist, dir);
        this.applySkillEffects(target);
    }
    @Override
    public int[] setupTargetMatrix() {
        List<Integer> resultList = new ArrayList<>();
        if (this.Hero.enemy) {
            int start = this.Hero.position-1;
            for (int i = start; i > 3 && Math.abs(start-i)<=DISTANCE; i--) {
                if (this.Hero.arena.getAtPosition(i)!=null)
                    resultList.add(i);
            }
            return resultList.stream().mapToInt(i->i).toArray();
        } else {
            int start = this.Hero.position+1;
            for (int i = start; i < 4 && Math.abs(i-start)<=DISTANCE; i++) {
                if (this.Hero.arena.getAtPosition(i)!=null)
                    resultList.add(i);
            }
            return resultList.stream().mapToInt(i->i).toArray();
        }
    }
}
