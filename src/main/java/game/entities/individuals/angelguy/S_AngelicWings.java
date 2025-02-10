package game.entities.individuals.angelguy;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.effects.Cover;
import game.skills.changeeffects.statusinflictions.Bleeding;
import utils.MyMaths;

import java.util.List;

public class S_AngelicWings extends Skill {

    public S_AngelicWings(Hero hero) {
        super(hero);
        this.iconPath = "/icons/angelicwings.png";
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.BUFF);
        this.targetType = TargetType.SINGLE_ALLY;
        this.distance = 1;
        this.cdMax = 3;
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        target.addEffect(new Cover(2), this.hero);
        target.addEffect(new Combo(), this.hero);
        this.hero.heal(this.hero, 5 + (int)(this.hero.getStat(Stat.ENDURANCE)*0.1), this, false);
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Gives cover and combo. Heals self for 5 + 10% END";
    }


    @Override
    public String getName() {
        return "Angelic Wings";
    }
}
