package game.entities.individuals.sniper;

import game.entities.Hero;
import game.skills.AbilityType;
import game.skills.Skill;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;
import game.skills.changeeffects.effects.Cover;

public class S_SmokeGrenade extends Skill {

    public S_SmokeGrenade(Hero hero) {
        super(hero);
        this.iconPath = "entities/sniper/icons/smokegrenade.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.targetType = TargetType.ALL_TARGETS;
        this.possibleCastPositions = new int[]{0,1,2,3};
        this.possibleTargetPositions = new int[]{0,1,2,3};
        this.cdMax = 4;
        this.abilityType = AbilityType.TACTICAL;
    }



    @Override
    public int getAIRating(Hero target) {
        return 1 + target.getMissingLifePercentage() / 50;
    }

    @Override
    public void resolve() {
        int turns = this.hero.hasPermanentEffect(Combo.class)>0?2:1;
        for (Hero target : this.targets) {
            target.addEffect(new Cover(turns), this.hero);
        }
        this.hero.removePermanentEffectOfClass(Combo.class);
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Get Smokescreen for 1 turn. if combo: 2 turns";
    }


    @Override
    public String getName() {
        return "Smoke Grenade";
    }
}
