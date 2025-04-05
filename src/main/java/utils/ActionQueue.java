package utils;

import game.skills.Stat;

import java.util.ArrayList;
import java.util.List;

public class ActionQueue {
    private List<Action> actionList;

    public ActionQueue() {
        this.actionList = new ArrayList<>();
    }

    public void addAction(Action action) {
        this.actionList.add(action);
    }

    public Action getNextAction() {
        actionList.sort((o1, o2) -> {
            if (o1.skill.priority > o2.skill.priority) {
                return -1;
            } else {
                return o2.skill.hero.getStat(Stat.SPEED) - o1.skill.hero.getStat(Stat.SPEED);
            }
        });
        return actionList.get(0);
    }

    public boolean hasAction() {
        return !this.actionList.isEmpty();
    }

    public void remove(Action action) {
        this.actionList.remove(action);
    }
    public void updateActions() {
        for (Action action : this.actionList) {
            action.skill.getCurrentVersion();
        }
    }
}
