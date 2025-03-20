package game.entities.individuals.darkmage;

import game.entities.Hero;
import game.skills.AbilityType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.DarkSecrets;

import java.util.List;

public class S_DarkSecrets extends Skill {

    public S_DarkSecrets(Hero hero) {
        super(hero);
        this.iconPath = "entities/darkmage/icons/darksecrets.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.BUFF);
        this.targetType = TargetType.SINGLE_ALLY;
        this.effects = List.of(new DarkSecrets());
        this.distance = 2;
        this.manaCost = 4;
        this.abilityType = AbilityType.TACTICAL;
    }


    @Override
    public int getAIRating(Hero target) {
        int highestATKStat = Math.max(target.getStat(Stat.MAGIC), target.getStat(Stat.POWER));
        return highestATKStat / 4;
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gives Dark Secrets";
    }
    @Override
    public String getName() {
        return "Dark Secrets";
    }
}
