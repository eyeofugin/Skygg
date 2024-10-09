package game.skills.backgroundskills;

import framework.Logger;
import game.entities.Entity;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.changeeffects.effects.Bounty;
import game.skills.changeeffects.effects.BountyTarget;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HUN_Start_Hunt extends Skill {
    public HUN_Start_Hunt(Entity e) {
        super(e);
        this.name="hun_start_hunt";
        this.translation="Start the Hunt";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.effects = List.of(getBountyTarget());
        this.passive = true;
    }
    @Override
    public Skill getCast() {
        HUN_Start_Hunt cast = new HUN_Start_Hunt(this.entity);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Entity e) {
        return "Choose a rdm target each round. They will gain bounty target.";
    }
    @Override
    public void startOfTurn() {
        List<Entity> enemies = Arrays.stream(this.entity.arena.getAllLivingEntities())
                .filter(e -> e.enemy != this.entity.enemy).toList();
        Random rdm = new Random();
        Logger.logLn(this.entity.name + ".hun_starthunt.startofturn");
        enemies.get(rdm.nextInt(enemies.size())).addEffect(getBountyTarget(),this.entity);
    }
    public static Effect getBountyTarget() {
        return new BountyTarget();
    }
    public static Effect getBounty() {
        return new Bounty();
    }
}
