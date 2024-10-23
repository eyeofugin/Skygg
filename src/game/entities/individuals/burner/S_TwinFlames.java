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

public class S_TwinFlames extends Skill {

    public S_TwinFlames(Hero hero) {
        super(hero);
        this.name = "Twin Flames";
        this.iconPath = "/res/icons/twinflames.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.FAITH, 0.4));
        this.targetType = TargetType.TWO_RDM;
        this.dmg = 2;
        this.damageType = DamageType.MAGIC;
        this.faithCost = 4;
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/res/sprites/action_w.png", this.name, new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "(30+Mag)% chance to burn";
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int magic = this.hero.getStat(Stat.MAGIC);
        if (MyMaths.success(magic + 30)) {
            target.addEffect(new Burning(-1, 1), this.hero);
        }
    }
    @Override
    public void addSubscriptions() {

    }
}
