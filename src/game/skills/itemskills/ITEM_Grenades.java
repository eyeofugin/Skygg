package game.skills.itemskills;

import game.entities.Entity;
import game.entities.Multiplier;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.statusinflictions.Injured;
import game.skills.changeeffects.statusinflictions.Weakened;

import java.util.List;

public class ITEM_Grenades extends Skill {

    private final int INJURE_CHANCE = 10;
    private final int WEAKEN_CHANCE = 10;

    public ITEM_Grenades(Entity entity) {
        super(entity);
        this.name="item_grenades";
        this.translation="Grenades";
        this.description= "";
        this.animationR="grenadesR";
        this.animationL="grenadesR";
        this.targetType = TargetType.FIRST_TWO_ENEMIES;
        this.actionCost = 1;
        this.cdMax = 3;
        this.effects = List.of(new Injured(3, INJURE_CHANCE), new Weakened(3, WEAKEN_CHANCE));
        this.dmg = 10;
        this.tags = List.of(AiSkillTag.DMG);
        this.weaponSkill = true;
        this.damageType = Stat.RANGED;

    }
    @Override
    public Skill getCast() {
        ITEM_Grenades cast = new ITEM_Grenades(this.entity);
        cast.copyFrom(this);
        return cast;
    }

    @Override
    public String getDescriptionFor(Entity e) {
        return "Throws grenades at the first two enemies. Has a chance to injure and weaken.";
    }
}
