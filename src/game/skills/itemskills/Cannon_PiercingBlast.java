package game.skills.itemskills;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.backgroundskills.GUA_Adaptability;

import java.util.List;

public class Cannon_PiercingBlast extends Skill {
    private static final int DMG_BONUS=10;

    public Cannon_PiercingBlast(Hero Hero) {
        super(Hero);
        this.name="cannon_piercingblast";
        this.translation="Piercing Blast";
        this.description= "";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.LINE;
        this.dmgMultipliers = List.of(new Multiplier(Stat.PRECISION,0.1),
                new Multiplier(Stat.STRENGTH,0.3));
        this.actionCost = 1;
        this.distance = 2;
        this.dmg = Hero.getPrimary().getAutoAttackPower();
        this.tags = List.of(AiSkillTag.DMG);
        this.weaponSkill = true;
    }
    @Override
    public Skill getCast() {
        Cannon_PiercingBlast cast = new Cannon_PiercingBlast(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public void baseDamageChanges() {
        this.dmg += overheatCost*DMG_BONUS;
    }
    @Override
    public void postInit() {
        this.overheatCost = this.Hero.getPrimary().getMaxOverheat()- this.Hero.getPrimary().getCurrentOverheat();
    }
}
