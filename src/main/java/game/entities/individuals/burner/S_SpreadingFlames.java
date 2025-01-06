package game.entities.individuals.burner;

import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Burning;

import java.util.List;
import java.util.Random;

public class S_SpreadingFlames extends Skill {

    public S_SpreadingFlames(Hero hero) {
        super(hero);
        this.iconPath = "/icons/spreadingflames.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }
    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.targetType = TargetType.ALL_ENEMY;
        this.faithCost = 6;
        this.ultimate = true;
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "For each enemy burn stack, give another burn stack randomly.";
    }

    @Override
    public int getAIRating(Hero target) {
        return target.getPermanentEffectStacks(Burning.class) / 2;
    }

    @Override
    public void resolve() {
        List<Hero> enemies = this.hero.getEnemies();
        int enemyBurningStacks = enemies.stream().mapToInt(e->e.hasPermanentEffect(Burning.class)).sum();
        int amntEnemies = enemies.size();
        for (int i = 0; i < enemyBurningStacks; i++) {
            Random random = new Random();
            int enemyIndex = random.nextInt(amntEnemies);
            Hero enemy = enemies.get(enemyIndex);
            enemy.addEffect(new Burning(1), this.hero);
        }
    }

    @Override
    public String getName() {
        return "Spreading Flames";
    }
}
