package game.entities.individuals.burner;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;
import utils.MyMaths;

import java.util.List;

public class S_Spark extends Skill {

    public S_Spark(Hero hero) {
        super(hero);
        this.iconPath ="/icons/spark.png";
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
        this.distance = 2;
        this.dmg = 5;
        this.damageType = DamageType.HEAT;
        this.damageMode = DamageMode.MAGICAL;
        this.primary = true;
        this.faithGain = true;
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gets 3"+Stat.FAITH.getIconString()+". (2 * "+Stat.MAGIC.getIconString()+")% chance to burn";
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int magic = this.hero.getStat(Stat.MAGIC);
        if (MyMaths.success(magic * 2)) {
            target.addEffect(new Burning(1), this.hero);
        }
        this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, 3, this.hero);
    }

    @Override
    public String getName() {
        return "Spark";
    }
}
