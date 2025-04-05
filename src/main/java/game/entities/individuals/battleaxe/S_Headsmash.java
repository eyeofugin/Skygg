package game.entities.individuals.battleaxe;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.statusinflictions.Bleeding;
import game.skills.changeeffects.statusinflictions.Dazed;

import java.util.List;

public class S_Headsmash extends Skill {

    public S_Headsmash(Hero hero) {
        super(hero);
        this.iconPath = "entities/battleaxe/icons/headsmash.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.TACTICAL);
        this.aiTags = List.of(AiSkillTag.COMBO_ENABLED);
        this.dmgMultipliers = List.of(new Multiplier(Stat.STAMINA, 0.3));
        this.targetType = TargetType.SINGLE;
        this.possibleCastPositions = new int[]{3};
        this.possibleTargetPositions = new int[]{4};
        this.dmg = 1;
        this.cdMax = 2;
        this.damageMode = DamageMode.PHYSICAL;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        if (this.hero.hasPermanentEffect(Combo.class) > 0) {
            this.hero.removePermanentEffectOfClass(Combo.class);
            target.addEffect(new Bleeding(1), this.hero);
            target.addEffect(new Dazed(2), this.hero);
//            this.hero.arena.stun(target);
        } else {
            this.hero.addEffect(new Dazed(2), this.hero);
            this.hero.addEffect(new Combo(), this.hero);
        }
    }

    @Override
    public String getComboDescription(Hero hero) {
        return "Give " + Dazed.getStaticIconString() + "(2), " + Bleeding.getStaticIconString() + "(1).";
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Without combo: Get " + Dazed.getStaticIconString() + "(2), " + Combo.getStaticIconString() + "(~).";
    }


    @Override
    public String getName() {
        return "Headsmash";
    }
}
