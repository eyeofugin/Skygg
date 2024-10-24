package game.entities.individuals.firedancer;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;
import utils.MyMaths;

import java.util.List;

public class S_SingingBlades extends Skill {

    public S_SingingBlades(Hero hero) {
        super(hero);
        this.iconPath = "/res/icons/singingblades.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.dmgMultipliers = List.of(new Multiplier(Stat.FAITH, 0.15),
                new Multiplier(Stat.FINESSE, 0.4));
        this.targetType = TargetType.SINGLE;
        this.distance = 2;
        this.dmg = 4;
        this.countAsHits = 2;
        this.damageType = DamageType.NORMAL;
    }

    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/res/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "2 Hits, (FIN+FAITH)% chance to burn.";
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        int magic = this.hero.getStat(Stat.MAGIC);
        int finesse = this.hero.getStat(Stat.FINESSE);
        if (MyMaths.success(magic + finesse)) {
            target.addEffect(new Burning(-1, 1), this.hero);
        }
    }

    @Override
    public String getName() {
        return "Singing Blades";
    }

    @Override
    public void addSubscriptions() {

    }
}
