package game.entities.individuals.dragonbreather;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgChangesPayload;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.changeeffects.globals.Heat;

public class S_BlazingSkin extends Skill {

    public S_BlazingSkin(Hero hero) {
        super(hero);
        this.iconPath = "/icons/blazingskin.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.passive = true;
    }

    @Override
    protected void initAnimation() {

    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "20%+ defense in HEAT";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_CHANGES, new Connection(this, DmgChangesPayload.class, "dmgChanges"));
    }

    public void dmgChanges(DmgChangesPayload pl) {
        if (pl.target != null &&
                pl.target.equals(this.hero)) {
            if (this.hero.arena.globalEffect != null && this.hero.arena.globalEffect instanceof Heat) {
                pl.dmg = pl.dmg*80 / 100;
            }
        }
    }
    @Override
    public String getName() {
        return "Blazing Skin";
    }
}
