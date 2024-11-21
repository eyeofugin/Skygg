package game.entities.individuals.dragonbreather;

import framework.states.Arena;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;
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
        this.manaCost = 7;
        this.cdMax = 5;
    }

    @Override
    public int getAIArenaRating(Arena arena) {
        int rating = 0;
        for (Hero hero : arena.getAllLivingEntities().stream().filter(e->!e.isTeam2()).toList()) {
            rating += hero.getPermanentEffectStacks(Burning.class) / 2;
        }
        return rating;
    }

    @Override
    public String getName() {
        return "Heat";
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
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

}
