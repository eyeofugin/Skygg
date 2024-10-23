package game.skills.backgroundskills;

import game.entities.Hero;
import game.entities.individuals.dev.dummy.DUMMY;
import game.skills.DamageType;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import java.util.List;

public class AllyHeal extends Skill {

    public AllyHeal(Hero hero) {
        super(hero);
        this.name = "Spark";
        this.iconPath = "/res/icons/spark.png";
        setToInitial();
        initAnimation();
    }

    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(Skill.SkillTag.DMG);
        this.targetType = TargetType.SINGLE;
        this.distance = 8;
        this.dmg = 1;
        this.damageType = DamageType.MAGIC;
    }

    protected void initAnimation() {
        String path = "res/sprites/enemies/blorgon/blorgon_melee.png";
        switch(((DUMMY)this.hero).colorIndex) {
            case 1:
                path = "res/sprites/enemies/blorgon/blorgon_melee.png";
                break;
            case 2:
                path = "res/sprites/enemies/blorgon_red/blorgon_melee.png";
                break;
            case 3:
                path = "res/sprites/enemies/blorgon_blue/blorgon_melee.png";
                break;
            case 4:
                path = "res/sprites/enemies/blorgon_green/blorgon_melee.png";
                break;

        }
        this.hero.anim.setupAnimation(path, this.name, new int[]{15, 30, 45, 60});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "Descr.";
    }

    @Override
    public void addSubscriptions() {}
}
