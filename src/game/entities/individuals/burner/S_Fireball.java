package game.entities.individuals.burner;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;
import utils.MyMaths;

import java.util.List;

public class S_Fireball extends Skill {

    public S_Fireball(Hero hero) {
        super(hero);
        this.name = "Fireball";
        this.iconPath ="/res/icons/fireball.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }
    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.FAITH, 0.1));
        this.targetType = TargetType.SINGLE;
        this.distance = 3;
        this.dmg = 5;
        this.damageType = DamageType.MAGIC;
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/res/sprites/action_w.png", this.name, new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gets 3 Favor, (10+Favor) chance to burn";
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int magic = this.hero.getStat(Stat.CURRENT_FAITH);
        if (MyMaths.success(magic + 10)) {
            target.addEffect(new Burning(-1, 1), this.hero);
        }
        this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, 3);
    }
    @Override
    public void addSubscriptions() {

    }
}
