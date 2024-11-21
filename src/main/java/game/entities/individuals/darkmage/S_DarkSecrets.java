package game.entities.individuals.darkmage;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.DarkSecrets;

import java.util.List;

public class S_DarkSecrets extends Skill {

    public S_DarkSecrets(Hero hero) {
        super(hero);
        this.iconPath = "/icons/darksecrets.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.BUFF);
        this.targetType = TargetType.SINGLE_ALLY;
        this.effects = List.of(new DarkSecrets());
        this.distance = 3;
        this.cdMax = 3;
        this.manaCost = 5;
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public int getAIRating(Hero target) {
        int highestATKStat = Math.max(Math.max(target.getStat(Stat.MAGIC), target.getStat(Stat.FINESSE)), target.getStat(Stat.FORCE));
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
