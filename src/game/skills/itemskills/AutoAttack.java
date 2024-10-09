package game.skills.itemskills;

import framework.Logger;
import framework.resources.SpriteLibrary;
import game.entities.Entity;
import game.entities.Multiplier;
import game.objects.Equipment;
import game.skills.Skill;
import game.skills.Stat;

import java.util.List;

public class AutoAttack extends Skill {
    public AutoAttack(Entity e) {
        this(e, null);
    }
    public AutoAttack(Entity e, Equipment equipment) {
        super(e);
        this.name = "auto_attack";
        this.translation = "Auto Attack";   
        this.animationR = "aaR";
        this.animationL = "aaL";
        this.autoAttack = true;
        this.tags = List.of(AiSkillTag.DMG);
        this.setEquipment(equipment);
    }

    @Override
    public Skill getCast() {
        AutoAttack cast = new AutoAttack(this.entity);
        cast.copyFrom(this);
        cast.setEquipment(this.entity.getPrimary());
        return cast;
    }
    @Override
    public String getDescriptionFor(Entity e) {
        return "Uses the primary weapon if available to perform a basic attack.";
    }

    public void setEquipment(Equipment equipment) {
        Logger.logLn(this.entity.name + ".AutoAttack.setEquipment:" + equipment);
        if (equipment!=null) {
            this.setDamageType(equipment.getDamageType());
            this.setDistance(equipment.getAutoAttackDistance());
            this.setPower(equipment.getAutoAttackPower());
            this.setActionCost(equipment.getAutoActionCost());
            this.setLifeCost(equipment.getAutoLifeCost());
            this.setOverheatCost(equipment.getAutoOverheatCost());
            this.setPrimaryMultiplier(equipment.getAutoAttackMultiplier());
            this.dmgMultipliers = List.of(new Multiplier(this.getPrimaryMultiplier(),
                    equipment.getAutoAttackMultiplierAmount()));
            this.weaponSkill = true;
            this.icon = equipment.getAaIcon();
        } else {
            primaryMultiplier = Stat.STRENGTH;
            this.weaponSkill = false;
        }

    }
}
