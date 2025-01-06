package game.entities.individuals.longsword;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.effects.Cover;

import java.util.List;

public class S_Cover extends Skill {

    public S_Cover(Hero hero) {
        super(hero);
        this.iconPath = "/icons/cover.png";
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.BUFF);
        this.targetType = TargetType.SINGLE_ALLY;
        this.distance = 1;
        this.cdMax = 4;
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.addEffect(new Cover(3), this.hero);
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gives Cover for 3 turns";
    }


    @Override
    public String getName() {
        return "Cover";
    }
}
