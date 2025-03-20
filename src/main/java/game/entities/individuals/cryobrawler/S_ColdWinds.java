package game.entities.individuals.cryobrawler;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.EndOfRoundPayload;
import framework.connector.payloads.GlobalEffectChangePayload;
import game.entities.Hero;
import game.skills.AbilityType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Frost;
import game.skills.changeeffects.globals.AetherWinds;
import game.skills.changeeffects.globals.HolyLight;

import java.util.List;
import java.util.Random;

public class S_ColdWinds extends Skill {

    public S_ColdWinds(Hero hero) {
        super(hero);
        this.iconPath = "entities/cryobrawler/icons/coldwinds.png";
        addSubscriptions();
        setToInitial();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.SETUP);
        this.targetType = TargetType.ARENA;
        this.manaCost = 4;
        this.abilityType = AbilityType.TACTICAL;
    }


    @Override
    public int getAIRating(Hero target) {
        return 3;
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.END_OF_ROUND, new Connection(this, EndOfRoundPayload.class, "endOfRound"));
    }

    public void endOfRound(EndOfRoundPayload pl) {
        if (pl.arena.globalEffect instanceof AetherWinds) {
            Random rand = new Random();
            List<Hero> enemies = this.hero.getEnemies();
            enemies.get(rand.nextInt(enemies.size()))
                    .addEffect(new Frost(1), this.hero);
        }
    }
    @Override
    public String getDescriptionFor(Hero hero) {
        return "Summon Aether Winds. During Aether Winds each turn a rdm enemy gets a frost stack";
    }
    @Override
    public void applySkillEffects(Hero target) {
        super.applySkillEffects(target);
        this.hero.arena.setGlobalEffect(new AetherWinds());
    }

    @Override
    public String getName() {
        return "Cold Winds";
    }
}
