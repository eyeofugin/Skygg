package game.objects;

import game.entities.Hero;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.Stat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Equipment {

    public enum EquipmentType {
        WEAPON,
        ARMOR,
        TRINKET;
    }
    protected EquipmentType type;
    protected int autoAttackPower;
    protected int autoAttackDistance;
    protected Stat damageType;


    protected Stat autoAttackMultiplier;


    protected String aaIcon;
    protected double autoAttackMultiplierAmount;
    protected int autoAttackActionCost;
    protected int autoAttackOverheatCost;
    protected int currentOverheat = 0;
    protected int maxOverheat = 0;
    protected boolean inOverheat;
    public int inventoryPosition = 0;
    public Hero Hero;

    protected Map<Stat,Integer> statBonus = new HashMap<>();
    protected List<Skill> skillList = new ArrayList<>();

    public void update() {

    }
    public void startOfTurn() {
    }
    public int getStat(Stat stat) {
        if(this.statBonus.get(stat)!=null) {
            return this.statBonus.get(stat);
        }
        return 0;
    }

    public void changeOverheat(int i) {
        if (this.currentOverheat != this.maxOverheat) {
            this.currentOverheat += i;
            this.currentOverheat = Math.max(this.currentOverheat, 0);
            this.currentOverheat = Math.min(this.currentOverheat, maxOverheat);
            if (this.currentOverheat == this.maxOverheat) {
                this.overHeat();
            }
        }
    }
    protected void overHeat(){}
    public boolean isOverheated() {
        return this.inOverheat;
    }
    public boolean canPerformCheck(Skill cast) {
        return true;
    }
    public int getTargetedStat(Stat stat, Skill targeted) {return 0;}
    public int getCastingStat(Stat stat, Skill cast) { return 0;}
    public int getAccuracyFor(Skill cast, int accuracy) { return accuracy; }
    public int getCurrentActionAmountChange() { return 0;}
    public void effectAddedTrigger(Effect effect, Hero target) {}
    public void replacementEffect(Skill cast){};
    public void changeEffects(Skill cast){};
    public int getAutoAttackPower() {
        return autoAttackPower;
    }
    public void setAutoAttackPower(int autoAttackPower) {
        this.autoAttackPower = autoAttackPower;
    }

    public int getCurrentOverheat() {
        return currentOverheat;
    }

    public void setCurrentOverheat(int currentOverheat) {
        this.currentOverheat = currentOverheat;
    }

    public int getMaxOverheat() {
        return maxOverheat;
    }

    public void setMaxOverheat(int maxOverheat) {
        this.maxOverheat = maxOverheat;
    }

    public int getAutoAttackDistance() {
        return autoAttackDistance;
    }

    public void setAutoAttackDistance(int autoAttackDistance) {
        this.autoAttackDistance = autoAttackDistance;
    }

    public Stat getDamageType() {
        return damageType;
    }

    public void setDamageType(Stat damageType) {
        this.damageType = damageType;
    }

    public int getAutoAttackActionCost() {
        return autoAttackActionCost;
    }
    public int getAutoOverheatCost() { return autoAttackOverheatCost; }

    public void setAutoAttackActionCost(int autoAttackActionCost) {
        this.autoAttackActionCost = autoAttackActionCost;
    }
    public int getAutoLifeCost() {
        return 0;
    }
    public int getAutoActionCost() {
        return 1;
    }

    public int getDamageChanges(Hero caster, Hero target, Skill damagingSkill, int result, Stat damageType, boolean simulated) {
        return result;
    }
    public int getHealChanges(Hero caster, Hero target, Skill damagingSkill, int result) {
        return result;
    }

    public Boolean performSuccessCheck(Skill cast) {
        return true;
    }

    public List<Skill> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<Skill> skillList) {
        this.skillList = skillList;
    }
    public Stat getAutoAttackMultiplier() {
        return autoAttackMultiplier;
    }
    public double getAutoAttackMultiplierAmount() {
        return autoAttackMultiplierAmount;
    }

    public void setAutoAttackMultiplierAmount(double autoAttackMultiplierAmount) {
        this.autoAttackMultiplierAmount = autoAttackMultiplierAmount;
    }
    public void setAutoAttackMultiplier(Stat autoAttackMultiplier) {
        this.autoAttackMultiplier = autoAttackMultiplier;
    }
    public EquipmentType getType() {
        return type;
    }
    public String getAaIcon() {
        return this.aaIcon;
    }
}
