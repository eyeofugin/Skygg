package game.objects.equipments;

import game.objects.Equipment;
import game.objects.equipments.skills.S_GraftedExoskeleton;
import game.skills.Stat;

import java.util.List;

public class GraftedExoskeleton extends Equipment {

    public GraftedExoskeleton() {
        super("graftedexoskeleton", "Grafted Exoskeleton");
        this.statBonus = this.loadStatBonus();
        this.adaptiveStats = List.of(Stat.NORMAL_DEF, Stat.LIGHT_DEF, Stat.DARK_DEF, Stat.HEAT_DEF, Stat.FROST_DEF);
        this.skill = new S_GraftedExoskeleton(this);
    }

    @Override
    public String getDescription() {
        return "+2 Adaptive stat on each def. +15 Life.[br]Active: Remove all debuffs. Cooldown 4.";
    }
}
