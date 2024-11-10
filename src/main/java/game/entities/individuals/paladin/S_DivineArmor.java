package game.entities.individuals.paladin;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class S_DivineArmor extends Skill {

    public S_DivineArmor(Hero hero) {
        super(hero);
        this.iconPath = "/icons/divinearmor.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.BUFF);
        this.targetType = TargetType.SELF;
        this.cdMax = 1;
        this.faithCost = 6;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.addToStat(Stat.ENDURANCE, 3);
        this.hero.addToStat(Stat.STAMINA, 1);
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "+3"+Stat.ENDURANCE.getIconString()+" +1"+Stat.STAMINA.getIconString();
    }
    @Override
    public String getName() {
        return "Divine Armor";
    }
}
