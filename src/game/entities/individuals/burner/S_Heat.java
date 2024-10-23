package game.entities.individuals.burner;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;
import game.skills.changeeffects.globals.Heat;
import utils.MyMaths;

import java.util.List;

public class S_Heat extends Skill {

    public S_Heat(Hero hero) {
        super(hero);
        this.name = "Heat";
        this.iconPath = "/res/icons/heat.png";
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
        this.hero.anim.setupAnimation(this.hero.basePath + "/res/sprites/action_w.png", this.name, new int[]{15, 30, 45});
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.arena.setGlobalEffect(new Heat());
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Summon Heat Effect";
    }

    @Override
    public void addSubscriptions() {

    }
}
