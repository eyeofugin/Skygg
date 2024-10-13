package game.skills.backgroundskills;

import framework.Logger;
import game.entities.Hero;
import game.skills.Skill;

public class MAR_Free_AA extends Skill {

    public boolean hadFreeAA = false;
    public MAR_Free_AA(Hero e) {
        super(e);
        this.name="mar_free_aa";
        this.translation="Free aa";
        this.description= "getDescription()";
        this.passive = true;
    }
    @Override
    public Skill getCast() {
        MAR_Free_AA cast = new MAR_Free_AA(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Hero e) {
        return "The first auto attack is free each turn.";
    }

    @Override
    public void startOfTurn() {
        if (this.ogSkill!=null) {
            ((MAR_Free_AA)this.ogSkill).hadFreeAA = false;
        }
        this.hadFreeAA = false;
    }
    @Override
    public void replacementEffect(Skill cast) {
        if (cast.Hero == this.Hero && cast.isAutoAttack() && !hadFreeAA) {
            Logger.logLn(this.Hero.name + ".mar_freeAA.replacement");
            cast.setActionCost(0);
            this.hadFreeAA = true;
        }
    }
}
