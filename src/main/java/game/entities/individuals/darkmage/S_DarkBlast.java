package game.entities.individuals.darkmage;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Blight;

import java.util.List;

public class S_DarkBlast extends Skill {

    public S_DarkBlast(Hero hero) {
        super(hero);
        this.iconPath = "/icons/darkblast.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.targetType = TargetType.SINGLE;
        this.effects = List.of(new Blight(1));
        this.distance = 3;
        this.primary = true;
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public int getAIRating(Hero target) {
        return 2;
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gives blight";
    }

    @Override
    public String getName() {
        return "Dark Blast";
    }
}
