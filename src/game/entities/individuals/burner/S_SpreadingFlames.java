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
        this.iconPath = "/res/icons/spreadingflames.png";
        addSubscriptions();
        setToInitial();
        initAnimation();
    }
    @Override
    public void setToInitial() {
        super.setToInitial();
        this.tags = List.of(SkillTag.DMG);
        this.targetType = TargetType.ALL_ENEMY;
        this.cdMax = 3;
    }

    @Override
    protected void initAnimation() {
        this.hero.anim.setupAnimation(this.hero.basePath + "/res/sprites/action_w.png", this.getName(), new int[]{15, 30, 45});
    }

    @Override
    public String getDescriptionFor(Hero hero) {
        return "For each enemy burn stack, give another burn stack randomly. if at least 5 stacks were given this way, +5 favor";
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
            enemy.addEffect(new Burning(-1, 1), this.hero);
        }
        if (enemyBurningStacks > 4) {
            this.hero.addResource(Stat.CURRENT_FAITH, Stat.FAITH, 3);
        }
    }

    @Override
    public String getName() {
        return "Spreading Flames";
    }

    @Override
    public void addSubscriptions() {}
}
