package game.entities;

import game.skills.Stat;

public class Multiplier {
    public Stat prof;
    public double percentage;
    public Multiplier(Stat prof, double percentage) {
        this.prof = prof;
        this.percentage = percentage;
    }
}
