package game.skills.itemskills;

import framework.Logger;
import game.entities.Entity;
import game.entities.Multiplier;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.backgroundskills.GUA_Adaptability;
import game.skills.changeeffects.statusinflictions.Paralysed;
import utils.MyMaths;

import java.util.List;

public class Rifle_Barrage extends Skill {
    public int barrageStack = 0;
    private static final double DMG = 0.5;
    private static final int DMG_BONUS = 10;

    public Rifle_Barrage(Entity entity) {
        super(entity);
        this.name="rifle_barrage";
        this.translation="Shock Ammo";
        this.description= "Deals damage that gets stronger over time";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.SINGLE;
        this.dmgMultipliers = List.of(new Multiplier(Stat.PRECISION,0.5));
        this.actionCost = 1;
        this.distance = 2;
        this.overheatCost = 2;
        this.dmg = (int)(entity.getPrimary().getAutoAttackPower()*DMG);
        this.tags = List.of(AiSkillTag.DMG);
        this.weaponSkill = true;
    }
    @Override
    public Skill getCast() {
        Rifle_Barrage cast = new Rifle_Barrage(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public void postInit() {
        this.barrageStack = ((Rifle_Barrage) this.ogSkill).barrageStack;
    }
    @Override
    public void baseDamageChanges() {
        this.dmg += barrageStack*DMG_BONUS;
    }
    @Override
    public void dmgTrigger(Entity target, Skill cast, int doneDamage) {
        if (cast.entity == this.entity && cast.getClass().equals(this.getClass())) {
            Logger.logLn(this.entity.name + ".Rifle_barrage.dmgTrigger:");
            this.barrageStack++;
        }
    }
}
