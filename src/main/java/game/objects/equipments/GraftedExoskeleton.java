package game.objects.equipments;

import game.objects.Equipment;
import game.objects.equipments.skills.S_GraftedExoskeleton;
import game.skills.Stat;

import java.util.List;

//TODO: DT Changes
public class GraftedExoskeleton extends Equipment {

    public GraftedExoskeleton() {
        super("graftedexoskeleton", "Grafted Exoskeleton");
        this.statBonus = this.loadBaseStatBonus();
        this.adaptiveStats = List.of();
        this.skill = new S_GraftedExoskeleton(this);
    }

    @Override
    public String getDescription() {
        return "+2 Adaptive stat on each def. +15 Life. [BRL]Active: Remove all debuffs. Cooldown 4.";
    }
}
