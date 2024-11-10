package game.entities.individuals.burner;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.TargetType;
import game.skills.changeeffects.globals.Heat;

import java.util.List;

public class S_Heat extends Skill {

    public S_Heat(Hero hero) {
        super(hero);
        this.iconPath = "/icons/heat.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.SETUP);
        this.targetType = TargetType.ARENA;
        this.faithCost = 5;
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public void applySkillEffects(Hero target) {
        this.hero.arena.setGlobalEffect(new Heat());
    }

    @Override
    public String getName() {
        return "Heat";
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Summon Heat Effect. (During heat all Burning damage is doubled)";
    }
}