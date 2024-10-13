package game.skills.backgroundskills;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Shielded;

import javax.imageio.plugins.jpeg.JPEGImageReadParam;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class TEC_Personal_Shield extends Skill {

    private static final int TURNS = 3;
    private static final int ACTION_COST = 2;
    private static final int CD_TOTAL = 5;

    public TEC_Personal_Shield(Hero e) {
        super(e);
        this.name="tec_personal_shield";
        this.translation="Personal Shield";
        this.description= "getDescription()";
        this.animationR="runR";
        this.animationL="runL";
        this.targetType = TargetType.SELF;
        this.effects = List.of(new Shielded());
        this.actionCost = ACTION_COST;
        this.cdMax = CD_TOTAL;
        this.tags = List.of(AiSkillTag.BUFF);
    }
    @Override
    public Skill getCast() {
        TEC_Personal_Shield cast = new TEC_Personal_Shield(this.Hero);
        cast.copyFrom(this);
        return cast;
    }
    @Override
    public String getDescriptionFor(Hero e) {
        return "Gain shielded." ;
    }
}
