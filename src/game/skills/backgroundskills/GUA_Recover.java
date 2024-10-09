package game.skills.backgroundskills;

import framework.Logger;
import game.entities.Entity;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Timeout;

import java.util.List;

public class GUA_Recover extends Skill {
    private static final int ACTION_COST = 1;
    private static final int CD_TOTAL = 2;
    private static final int HEAL_PERCENTAGE = 50;

    public GUA_Recover(Entity e) {
        super(e);
        this.name="gua_recover";
        this.translation="Recover";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.SELF;
        this.effects = List.of(new Timeout());
        this.actionCost = ACTION_COST;
        this.cdMax = CD_TOTAL;
        this.tags = List.of(AiSkillTag.HEAL);
    }
    @Override
    public Skill getCast() {
        GUA_Recover cast = new GUA_Recover(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Entity e) {
        return "Cleanses all debuffs and heals for " + HEAL_PERCENTAGE + "%. Skips the next turn.";
    }
    @Override
    public int[] setupTargetMatrix() {
        return new int[]{this.entity.position};
    }
    @Override
    protected void individualResolve(Entity target) {
        int heal = this.getHeal();
        target.heal(this.entity, heal, this);
        target.cleanse();
        this.applySkillEffects(target);
    }
    @Override
    public int getHeal() {
        int baseHeal = (int)((double)this.entity.getStat(Stat.MAX_LIFE) * HEAL_PERCENTAGE / 100);
        int multBonus = getHealMultiBonus();
        return baseHeal + multBonus;
    }
}
