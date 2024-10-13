package game.skills.backgroundskills;

import game.entities.Hero;
import game.entities.Multiplier;
import game.skills.Skill;
import game.skills.Stat;

public class AllyHeal extends Skill {
    public AllyHeal(Hero e) {
        super(e);
        this.name="ally_heal";
        this.translation="Ally Heal";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
//        this.activateRange = new Range(1,4);
//        this.targetRange = new Range(1,4);
        this.damageType= Stat.FORCE;
//        this.effects = List.of(new Effect(EffectType.STATUS_INFLICTION, -1, new Burning_deprecated(), null));
        this.dmgMultipliers = of(new Multiplier[] {
                new Multiplier(Stat.LETHALITY,0.8),
                new Multiplier(Stat.HARMONY,0.8)
        });
        this.dmg = 1;
        this.distance = 5;
        this.actionCost = 1;
    }

    @Override
    public Skill getCast() {
        AllyHeal cast = new AllyHeal(this.Hero);
        cast.copyFrom(this);
        return cast;
    }

    @Override
    public String getDescriptionFor(Hero e) {
        return "Heals target friend for " + getDamage() + "" + getDmgMultiplierString(e) + " up to " + this.distance + " away. [br] " +
                "Sets target on fire.";
    }
}
