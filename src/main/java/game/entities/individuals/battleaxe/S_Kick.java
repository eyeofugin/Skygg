package game.entities.individuals.battleaxe;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class S_Kick extends Skill {

    public S_Kick(Hero hero) {
        super(hero);
        this.iconPath = "/icons/kick.png";
        addSubscriptions();
        setToInitial();

    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG, SkillTag.PEEL);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.1));
        this.targetType = TargetType.SINGLE;
        this.distance = 1;
        this.dmg = 3;
        this.cdMax = 4;
        this.damageType = DamageType.NORMAL;
        this.damageMode = DamageMode.PHYSICAL;
    }

    @Override
    public int getAIRating(Hero target) {
        int rating = 0;
        if (target.getCurrentLifePercentage() > 75) {
            rating++;
        }
        Hero targetBehind = this.hero.arena.getAtPosition(target.getPosition()-1);
        if (targetBehind != null) {
            rating += targetBehind.getMissingLifePercentage() / 25;
        }
        if (target.getPosition() == target.getLastEffectivePosition()) {
            rating +=5;
        }
        return rating;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.arena.move(target, 1, target.isTeam2()?1:-1);
        target.changeStatTo(Stat.SHIELD, 0);
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Push 1, target loses shield;";
    }

    @Override
    public String getName() {
        return "Kick";
    }
}
