package game.controllers;

import game.entities.Entity;
import game.skills.Skill;

public class TurnChoice {
    public Skill s;
    public Entity[] targets;
    public int rating;

    public TurnChoice(Skill s, Entity[] targets, int rating) {
        this.s = s;
        this.targets = targets;
        this.rating = rating;
    }
}
