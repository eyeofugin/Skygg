package game.entities.individuals.paladin;

import framework.connector.Connection;
import framework.connector.Connector;
import framework.connector.payloads.DmgToShieldPayload;
import framework.connector.payloads.ShieldBrokenPayload;
import framework.connector.payloads.StartOfTurnPayload;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;

public class S_ShiningShield extends Skill {

    private boolean broken = false;

    public S_ShiningShield(Hero hero) {
        super(hero);
        this.iconPath = "/res/icons/shiningshield.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
        this.hero.addResource(Stat.SHIELD, Stat.LIFE,this.hero.getStat(Stat.LIFE) * 10 / 100);
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
        return "Get a 10% Life shield that regenerates completely at the start of your turn until broken. Whenever damage is done to your shield, get that much favor";
    }

    @Override
    public void addSubscriptions() {
        Connector.addSubscription(Connector.DMG_TO_SHIELD, new Connection(this, DmgToShieldPayload.class, "dmgToShield"));
        Connector.addSubscription(Connector.SHIELD_BROKEN, new Connection(this, ShieldBrokenPayload.class, "shieldBroken"));
        Connector.addSubscription(this.hero.getId() + Connector.START_OF_TURN, new Connection(this, StartOfTurnPayload.class, "startOfTurn"));
    }

    public void dmgToShield(DmgToShieldPayload pl) {
        if (broken) {
            return;
        }
        this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, pl.dmg);
    }
    public void shieldBroken(ShieldBrokenPayload pl) {
        this.broken = true;
    }
    public void startOfTurn(StartOfTurnPayload pl) {
        if (broken) {
            return;
        }
        int shield = this.hero.getStat(Stat.SHIELD);
        int shiningShield = this.hero.getStat(Stat.LIFE) * 10 / 100;
        if (shield < shiningShield) {
            this.hero.changeStatTo(Stat.SHIELD, shiningShield);
        }
    }

    @Override
    public String getName() {
        return "Shining Shield";
    }
}
