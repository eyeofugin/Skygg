package game.controllers;

import framework.Logger;
import framework.states.Arena;
import game.entities.Hero;
import game.skills.AiSkillTag;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import game.skills.changeeffects.effects.Combo;
import game.skills.genericskills.S_Skip;
import utils.Action;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ArenaAIController {

    public Arena arena;
    public AIMemory memory;
    private List<Action> turnOptions = new ArrayList<>();

    public ArenaAIController(Arena arena) {
        this.arena = arena;
        this.memory = new AIMemory();
    }
    public void setup() {
        for (Hero ai : this.arena.teams.get(1).heroes) {
            int averageLife = (int) Arrays.stream(this.arena.teams.get(1).heroes).map(e->e.getStat(Stat.LIFE)).mapToInt(Integer::intValue)
                    .summaryStatistics().getAverage();
            calculatePreferredPosition(ai, averageLife);
        }
    }

    public void turn() {
        this.turnOptions = new ArrayList<>();
        Logger.aiLogln("-------------------------------------------------------------");
        evaluateSkills();
        Action bestAction = null;
        int bestRating = -999;
        Logger.aiLogln("Rated Actions");
        for (Action action : this.turnOptions) {
            Logger.aiLogln(action.skill.getName() + "/" + action.rating);
            if (action.rating > bestRating && action.targets != null && action.targets.length > 0) {
                bestAction = action;
                bestRating = action.rating;
            }
        }
        if (bestAction == null) {
            Logger.aiLogln("no moves?");
            for (Skill skill : this.arena.activeHero.getSkills()) {
                if (skill instanceof S_Skip) {
                    this.arena.activeSkill = skill;
                    this.arena.activeSkill.perform();
                    this.arena.nextAction = "resolveSkill";
                    this.arena.status = Arena.Status.WAIT_ON_ANIMATION;
                }
            }
        } else {
            Skill s = bestAction.skill;
            Logger.aiLog("AI will perform " + s.getName() + "/" + bestAction.rating + " at position " + bestAction.targets[0].getPosition());
            this.arena.activeSkill = s;
            this.arena.activeSkill.setTargets(bestAction.targets);

            this.arena.activeSkill.perform();
            this.arena.activeTargets = bestAction.targets;
            this.arena.nextAction = "resolveSkill";
            this.arena.status = Arena.Status.WAIT_ON_ANIMATION;
        }
    }


    private void evaluateSkills() {
        for (Skill s: this.arena.activeHero.getSkills()) {
            if (s != null) {
                Logger.aiLogln("evaluate:" +s.getName());
                if ( !this.arena.activeHero.canPerform(s)) {
                    Logger.aiLogln("ai cannot perform");
                    continue;
                }
                if (s.getTargetType().equals(TargetType.ARENA)) {
                    int rating  = 0;

                    rating += s.getAIArenaRating(this.arena);

                    Action action = new Action();
                    action.skill = s;
                    action.targets = new Hero[0];
                    action.rating = rating;
                    this.turnOptions.add(action);
                } else if(s.getTargetType().equals(TargetType.ONE_RDM)
                            || s.getTargetType().equals(TargetType.TWO_RDM)
                            || s.getTargetType().equals(TargetType.THREE_RDM)) {
                    //ignore action for now
                } else {
                    for (Hero[] targets : getPossibleTargetGroups(s)) {
                        Logger.aiLog("\trating for targetgroup ");
                        for (Hero target : targets) {
                            if (target != null) {
                                Logger.aiLog(target.getName() + " ");
                            }
                        }
                        int rating = 0; //Rating borders ~+-20(==400%dmg/heal)
                        rating += getDamageRating(s, targets);
                        rating += getHealRating(s,targets);
                        rating += getCustomAIRating(s, targets);
                        rating += getShieldRating(s, targets);
                        rating += getComboRating(s);
                        rating += getFaithGainRating(s);
                        Logger.aiLogln("\n\trating:"+rating);

                        Action action = new Action();
                        action.skill = s;
                        action.targets = targets;
                        action.rating = rating;
                        this.turnOptions.add(action);
                    }
                }
            }
        }
    }


//--------------------RATINGS-----------------------------

    private int getDamageRating(Skill cast, Hero[] targets) {
        int weightedPercentages = 0;
        int lethality = 0; // this.arena.activeHero.getStat(Stat.LETHALITY, cast);
        for (Hero e : targets) {
            int estimatedDamage = cast.getDmgWithMulti(e);
            Logger.aiLog(" target:"+e.getName());

            int dmgPercentage = e.simulateDamageInPercentages(this.arena.activeHero, estimatedDamage, cast.getDamageMode(), lethality, cast);
            if (dmgPercentage >= e.getCurrentLifePercentage()) {
                Logger.aiLog(" low life bonus!");
                dmgPercentage *= 5;
            }
            int HeroWeightedPercentage = e.isTeam2()?-1*dmgPercentage:dmgPercentage;
            Logger.aiLog(" weighted dmg percentage:"+HeroWeightedPercentage);
            weightedPercentages += HeroWeightedPercentage;
        }
        int damageRating = weightedPercentages / 10;
        Logger.aiLog(" sum weighted dmg percentage:"+weightedPercentages);
        Logger.aiLog(" dmgRating:"+damageRating);
        return damageRating;
    }
    private int getHealRating(Skill cast, Hero[] targets) {
        int weightedPercentages = 0;
        for (Hero e : targets) {
            int estimatedDamage = cast.getHealWithMulti(e);
            Logger.aiLog(" target:"+e.getName());
            int healPercentage = e.simulateHealInPercentages(this.arena.activeHero, estimatedDamage, cast);
            if (e.getCurrentLifePercentage() < 30) {
                Logger.aiLog(" low life bonus!");
                healPercentage *= 3;
            }

            int HeroWeightedPercentage = e.isTeam2()?healPercentage:-1*healPercentage;
            Logger.aiLog(" weighted heal percentage:"+HeroWeightedPercentage);
            weightedPercentages += HeroWeightedPercentage;
        }
        int healRating = weightedPercentages / 10;
        Logger.aiLog(" sum weighted heal percentage:"+weightedPercentages);
        Logger.aiLog(" healRating:"+healRating);
        return healRating;
    }

    private int getCustomAIRating(Skill cast, Hero[] targets) {
        int result = 0;
        for (Hero target: targets) {
            result+=cast.getAIRating(target);
        }
        Logger.aiLog(" customrating:"+result);
        return result;
    }

    private int getShieldRating(Skill cast, Hero[] targets) {
        int result = 0;
        if (cast.getShield(null) == 0) {
            return 0;
        }
        for (Hero e : targets) {
            if (e.isTeam2()) {
                result += getShieldRating(e);
            } else {
                result -= getShieldRating(e);
            }
        }
        return result;
    }

    private int getShieldRating(Hero target) {
        if (target.getCurrentLifePercentage() < 25) {
            return 3;
        }
        if (target.getCurrentLifePercentage() < 50) {
            return 2;
        }
        return 1;
    }

    private int getComboRating(Skill s) {
        if (s.aiTags.contains(AiSkillTag.COMBO_ENABLED) && s.hero.hasPermanentEffect(Combo.class) > 0) {
            return 1;
        }
        return 0;
    }

    private int getFaithGainRating(Skill s) {
        if (s.aiTags.contains(AiSkillTag.FAITH_GAIN)) {
            double missingFaith = 1.0 - s.hero.getResourcePercentage(Stat.CURRENT_FAITH);
            return (int)(missingFaith / 0.3);
        }
        return 0;
    }

//---------------------------------Targeting---------------------------

    private List<Hero[]> getPossibleTargetGroups(Skill s) {
        List<Hero[]> preFilter = new ArrayList<>();
        List<Hero[]> result = new ArrayList<>();
        int[] targetMatrix = s.setupTargetMatrix();
        switch (s.getTargetType()) {
            case SINGLE -> setSingleTargetGroups(s, targetMatrix, preFilter, true);
            case SINGLE_OTHER -> setSingleTargetGroups(s, targetMatrix, preFilter, false);
            case SELF -> preFilter.add(new Hero[]{this.arena.activeHero});
            case ALL -> setAllTargetGroups(preFilter);
            case ALL_TARGETS -> setAllTargetsTargetGroups(targetMatrix, preFilter);
        }
        for (Hero[] heroArray : preFilter) {
            List<Hero> targets = new ArrayList<>();
            for (Hero hero : heroArray) {
                if (hero != null) {
                    targets.add(hero);
                }
            }
            if (!targets.isEmpty()) {
                result.add(targets.toArray(new Hero[0]));
            }
        }

        return result;
    }
    private void setSingleAllyTargetGroups(Skill s, List<Hero[]> result) {
        int[] targetMatrix = s.setupTargetMatrix();
        for (int i : targetMatrix) {
            Hero[] targets = new Hero[1];
            targets[0] = this.arena.getAtPosition(i);
            result.add(targets);
        }
    }
    private void setSingleTargetGroups(Skill s, int[] targetMatrix, List<Hero[]> result, boolean includeSelf) {
        for (int i : targetMatrix) {
            Hero[] targets = new Hero[1];
            Hero target = this.arena.getAtPosition(i);
            if (!target.equals(s.hero) || includeSelf) {
                targets[0] = target;
                result.add(targets);
            }
        }
    }
    private void setLineTargetGroups(Skill s, List<Hero[]> results) {
        int[] targetMatrix = s.setupTargetMatrix();
        int casterPosition = this.arena.activeHero.getPosition();
        if (targetMatrix.length>0) {
            List<Hero> lineGroup = new ArrayList<>();
            for (int i = targetMatrix[0]; i < casterPosition; i++) {
                Hero target = this.arena.getAtPosition(i);
                lineGroup.add(target);
            }
            results.add(lineGroup.toArray(new Hero[0]));
        }
    }
    private void setAllTargetGroups(List<Hero[]> results) {
        results.add(this.arena.getAllLivingEntities().toArray(new Hero[0]));
    }
    private void setAllTargetsTargetGroups(int[] targetMatrix, List<Hero[]> results) {
        Hero[] targets = new Hero[targetMatrix.length];
        int index = 0;
        for (int i : targetMatrix) {
            targets[index++] = this.arena.getAtPosition(i);
        }
        results.add(targets);
    }
    private void setAllAllyTargetGroups(List<Hero[]> results) {
        results.add(this.arena.activeHero.getAllies().toArray(new Hero[0]));
    }
    private void setAllEnemyTargetGroups(List<Hero[]> results) {
        List<Hero> enemies = this.arena.getAllLivingEntities().stream()
                .filter(e->e.isTeam2() != this.arena.activeHero.isTeam2()).toList();
        results.add(enemies.toArray(new Hero[0]));
    }
    private void setFirstTwoEnemyTargetGroups(List<Hero[]> results) {
        results.add(new Hero[]{this.arena.getAtPosition(2),this.arena.getAtPosition(3)});
    }
    private void setFirstEnemyTargetGroups(List<Hero[]> results) {
        results.add(new Hero[]{this.arena.getAtPosition(3)});
    }
    private void removeNullValuesAndTheDead(Hero[] source) {
        List<Hero> resultList = new ArrayList<>();
        for (Hero e: source) {
            if (e != null && e.getStat(Stat.CURRENT_LIFE) > 0) {
                resultList.add(e);
            }
        }
        resultList.toArray(source);
    }

    private void calculatePreferredPosition(Hero ai, int averageTeamLife) {
        List<Double> factors = new ArrayList<>();
        for (int i = 0; i < ai.getSkills().length; i++) {
            Skill s = ai.getSkills()[i];
            if (s != null) {
//                if (s.attributes.contains(SkillAttribute.BUFF) && s.distance > 0) {
//                    factors.add(s.distance > 2
//                            ? 8.0 : 7.0);
//                }
//                if (s.attributes.contains(SkillAttribute.DAMAGE)) {
//                    factors.add(Math.max(8.0, (double) 4 + s.distance));
//                }
            }
        }
        factors.add(ai.getStat(Stat.LIFE) < averageTeamLife
                ? 5.0 : 8.0);

//        ai.effectivePosition = (int) Math.round(factors.stream()
//                .mapToDouble(a -> a)
//                .average().orElse(8.0));
    }
}
