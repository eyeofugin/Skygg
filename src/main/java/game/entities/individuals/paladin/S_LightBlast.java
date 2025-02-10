package game.entities.individuals.paladin;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class S_LightBlast extends Skill {

    public S_LightBlast(Hero hero) {
        super(hero);
        this.iconPath = "/icons/lightblast.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG, SkillTag.PEEL);
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.4));
        this.targetType = TargetType.SINGLE;
        this.distance = 1;
        this.dmg = 2;
        this.faithCost = 5;
        this.damageType = DamageType.LIGHT;
        this.damageMode = DamageMode.MAGICAL;
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});

    }

    @Override
    public int getAIRating(Hero target) {
        int rating = 0;
        if (target.getCurrentLifePercentage() > 75) {
            rating++;
        }
        Hero targetBehind = this.hero.arena.getAtPosition(target.getPosition()-1);
        if (targetBehind != null) {
            rating += targetBehind.getMissingLifePercentage() / 25;
        }
        if (target.getPosition() == target.getLastEffectivePosition()) {
            rating +=5;
        }
        return rating;
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.arena.move(target, 1, target.isTeam2()?1:-1);
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Push 1 back";
    }


    @Override
    public String getName() {
        return "Light Blast";
    }
}
