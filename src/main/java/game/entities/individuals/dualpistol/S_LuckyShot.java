package game.entities.individuals.dualpistol;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.statusinflictions.Bleeding;

import java.util.List;
import java.util.Random;

public class S_LuckyShot extends Skill {

    public S_LuckyShot(Hero hero) {
        super(hero);
        this.iconPath = "entities/dualpistol/icons/carefulshot.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.aiTags = List.of(AiSkillTag.COMBO_ENABLED);
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{0,1,2};
        this.possibleTargetPositions = new int[]{5,6,7};
        this.dmg = 3;
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.05));
        this.cdMax = 2;
        this.damageMode = DamageMode.PHYSICAL;
        this.critChance = 50;
    }



    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        Effect effectOne = Effect.getRdmDebuff();
        target.addEffect(effectOne, this.hero);
        if (this.hero.hasPermanentEffect(Combo.class) > 0) {
            this.hero.removePermanentEffectOfClass(Combo.class);
            boolean keepLooking = true;
            Effect effectTwo = null;
            while (keepLooking) {
                effectTwo = Effect.getRdmDebuff();
                keepLooking = !(effectTwo.getClass().equals(effectOne.getClass()));
            }
            target.addEffect(effectTwo, this.hero);
        }
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "50% Crit chance. Gives a random debuff. Combo: Gives another random debuff.";
    }
    @Override
    public String getName() {
        return "Lucky Shot";
    }
}
