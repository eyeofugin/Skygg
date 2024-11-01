package game.entities.individuals.angelguy;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class S_Reengage extends Skill {

    public S_Reengage(Hero hero) {
        super(hero);
        this.iconPath = "/res/icons/reengage.png";
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG, SkillTag.MOVE);
        this.dmgMultipliers = List.of(new Multiplier(Stat.FORCE, 0.1));
        this.targetType = TargetType.SINGLE_ALLY;
        this.distance = 1;
        this.dmg = 1;
        this.damageType = DamageType.NORMAL;
    }

    @Override
    protected void individualResolve(Hero target) {
        this.hero.arena.moveTo(this.hero, target.getPosition());
        this.baseDamageChanges(target, this.hero);
        int dmg = getDmgWithMulti();
        int position = this.hero.getPosition();
        int ownPosition = this.hero.isEnemy()?4:3;
        int enemyPosition = this.hero.isEnemy()?3:4;
        if (position == ownPosition) {
            Hero enemy = this.hero.arena.getAtPosition(enemyPosition);
            if (enemy!=null) {
                enemy.damage(this.hero,dmg,this.damageType,0,this);
            }
        }
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/res/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Move 1 pos forward. low (for) damage when on pos 4";
    }

    @Override
    public String getName() {
        return "Reengage";
    }
}
