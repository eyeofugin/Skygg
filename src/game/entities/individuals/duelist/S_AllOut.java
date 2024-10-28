package game.entities.individuals.duelist;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class S_AllOut extends Skill {

    public S_AllOut(Hero hero) {
        super(hero);
        this.iconPath = "/res/icons/allout.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.BUFF);
        this.targetType = TargetType.SELF;
        this.cdMax = 5;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.addToStat(Stat.ENDURANCE, -3);
        this.hero.addToStat(Stat.FINESSE, 5);
        this.hero.addToStat(Stat.SPEED, 2);
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/res/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "5 FIN, 2 SPD, -3 END";
    }

    @Override
    public String getName() {
        return "All Out";
    }
}
