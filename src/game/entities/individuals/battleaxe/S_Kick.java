package game.entities.individuals.battleaxe;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class S_Kick extends Skill {

    public S_Kick(Hero hero) {
        super(hero);
        this.iconPath = "/res/icons/kick.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG, SkillTag.PEEL);
        this.dmgMultipliers = List.of(new Multiplier(Stat.FORCE, 0.1));
        this.targetType = TargetType.SINGLE;
        this.distance = 2;
        this.dmg = 3;
        this.cdMax = 4;
        this.damageType = DamageType.NORMAL;
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/res/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.arena.move(target, 1, target.isEnemy()?1:-1);
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Push 1;";
    }

    @Override
    public String getName() {
        return "Kick";
    }
}
