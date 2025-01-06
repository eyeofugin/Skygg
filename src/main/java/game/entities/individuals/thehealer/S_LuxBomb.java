package game.entities.individuals.thehealer;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.DamageMode;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import java.util.List;

public class S_LuxBomb extends Skill {

    public S_LuxBomb(Hero hero) {
        super(hero);
        this.iconPath = "/icons/luxbomb.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.SINGLE;
        this.dmg = 1;
        this.dmgMultipliers = List.of(new Multiplier(Stat.MAGIC, 0.1),
                new Multiplier(Stat.MANA, 0.3));
        this.damageMode = DamageMode.MAGICAL;
        this.damageType = DamageType.LIGHT;
        this.distance = 3;
        this.primary = true;
    }


    @Override
    public int getAIRating(Hero target) {
        return target.getMissingLifePercentage() / 25;
    }
    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});

    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Dmg";
    }


    @Override
    public String getName() {
        return "Lux Bomb";
    }
}
