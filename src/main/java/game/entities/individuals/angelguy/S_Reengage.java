package game.entities.individuals.angelguy;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.*;

import java.util.List;

public class S_Reengage extends Skill {

    public S_Reengage(Hero hero) {
        super(hero);
        this.iconPath = "entities/angelguy/icons/reengage.png";
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG, SkillTag.MOVE);
        this.dmgMultipliers = List.of(new Multiplier(Stat.POWER, 0.1), new Multiplier(Stat.ENDURANCE, 0.1));
        this.targetType = TargetType.SINGLE_ALLY;
        this.distance = 1;
        this.dmg = 4;
        this.damageMode = DamageMode.PHYSICAL;
        this.cdMax = 4;
        this.abilityType = AbilityType.TACTICAL;
    }

    @Override
    protected void individualResolve(Hero target) {
        this.hero.arena.moveTo(this.hero, target.getPosition());
        this.baseDamageChanges(target, this.hero);
        int dmg = getDmgWithMulti(target);
        int position = this.hero.getPosition();
        int ownPosition = this.hero.isTeam2()?3:2;
        int enemyPosition = this.hero.isTeam2()?2:3;
        if (position == ownPosition) {
            Hero enemy = this.hero.arena.getAtPosition(enemyPosition);
            if (enemy!=null) {
                enemy.damage(this.hero,dmg,this.damageMode, 0,this);
            }
        }
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Move 1 pos forward. Damages first opponent when moved to front.";
    }

    @Override
    public int getAIRating(Hero target) {
        int rating = 0;
        if (this.hero.getPosition() == this.hero.team.getFirstPosition()) {
            return 1;
        }
        if (target.getPosition() == this.hero.team.getFirstPosition()) {
            rating++;
        }
        rating += target.getMissingLifePercentage() / 25;
        rating -= this.hero.getMissingLifePercentage() / 20;
        if (this.hero.getLastEffectivePosition() < this.hero.getPosition()) {
            rating += 10;
        }
        return rating;
    }

    @Override
    public String getName() {
        return "Reengage";
    }
}
