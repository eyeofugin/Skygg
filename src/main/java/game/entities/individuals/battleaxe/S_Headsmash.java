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
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.STAMINA, 0.3));
        this.targetType = TargetType.SINGLE;
        this.distance = 4;
        this.dmg = 1;
        this.cdMax = 2;
        this.damageMode = DamageMode.PHYSICAL;
        this.comboEnabled = true;
        this.abilityType = AbilityType.TACTICAL;
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
    public String getDescriptionFor(Hero hero) {
        return "combo: daze,bleed target; no combo: daze self and get combo";
    }


    @Override
    public String getName() {
        return "Headsmash";
    }
}
