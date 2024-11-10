package game.entities.individuals.rifle;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Scoped;

public class S_UseTheScope extends Skill {

    private boolean active = false;

    public S_UseTheScope(Hero hero) {
        super(hero);
        this.iconPath = "/icons/usethescope.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SELF;
        this.cdMax =1;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        if (this.active) {
            this.hero.removePermanentEffectOfClass(Scoped.class);
        } else {
            this.hero.addEffect(new Scoped(), this.hero);
        }
    }

    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Get scoped effect. activating again ends effect. Scoped: (+1 Range but rooted)";
    }

    @Override
    public String getName() {
        return "Scoped";
    }
}
