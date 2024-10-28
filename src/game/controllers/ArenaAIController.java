package game.controllers;

import framework.Logger;
import framework.states.Arena;
import game.entities.Hero;
import game.skills.Skill;
import game.skills.Stat;
import utils.Action;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ArenaAIController {

    public Arena arena;
    public AIMemory memory;
    private int beatDownMeter = 5;
    private List<Action> turnOptions = new ArrayList<>();

    public ArenaAIController(Arena arena) {
        this.arena = arena;
        this.memory = new AIMemory();
    }
    public void setup() {
        for (Hero ai : this.arena.enemies.heroes) {
            int averageLife = (int) Arrays.stream(this.arena.enemies.heroes).map(e->e.getStat(Stat.LIFE)).mapToInt(Integer::intValue)
                    .summaryStatistics().getAverage();
            calculatePreferredPosition(ai, averageLife);
        }
    }

    private void estimateBeatDownMeter() {
        int mediumValue = 500;
        for (Hero e : this.arena.getAllLivingEntities()) {
            int lifePercentage = e.getStat(Stat.CURRENT_LIFE) * 100 / e.getStat(Stat.LIFE);
            mediumValue += e.isEnemy()?lifePercentage:-1*lifePercentage;
        }
        this.beatDownMeter = mediumValue/100;
    }
    public void turn() {
        this.turnOptions = new ArrayList<>();
        estimateBeatDownMeter();
        evaluateSkills();
        Action bestAction = null;
        int bestRating = -999;
        for (Action action : this.turnOptions) {
            if (action.rating > bestRating && action.targets != null && action.targets.length > 0) {
                bestAction = action;
                bestRating = action.rating;
            }
        }
        if (bestAction == null) {
            Logger.logLn("no moves?");
        } else {
            Skill s = bestAction.skill;
            Logger.logLn("AI will perform " + s.getName() + "/" + bestAction.rating + " at position " + bestAction.targets[0].getPosition());
            this.arena.activeSkill = s;
            this.arena.activeSkill.setTargets(List.of(bestAction.targets));

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
                for (Hero[] targets : getPossibleTargetGroups(s)) {
                    Logger.aiLog("rating for targetgroup ");
                    for (Hero target : targets) {
                        if (target != null) {
                            Logger.aiLog(target.getName() + " ");
                        }
                    }
                    int rating = 0; //Rating borders ~+-20(==400%dmg/heal)
                    rating += getDamageRating(s, targets);
                    rating += getHealRating(s,targets);
                    rating += getTempoRating(s);
                    rating += getCustomAIRating(s, targets);
                    //TODO missing move rating

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
    private int getDamageRating(Skill cast, Hero[] targets) {
        int weightedPercentages = 0;
        int lethality = 0; // this.arena.activeHero.getStat(Stat.LETHALITY, cast);
        int estimatedDamage = cast.getDmgWithMulti();
        Logger.aiLog(" estimated dmg:"+estimatedDamage);
        for (Hero e : targets) {
            Logger.aiLog(" target:"+e.getName());
            int dmgPercentage = e.simulateDamageInPercentages(this.arena.activeHero, estimatedDamage, cast.getDamageType(), lethality, cast);
            if (dmgPercentage >= e.getMissingLifePercentage()) {
                Logger.aiLog(" low life bonus!");
                dmgPercentage *= 5;
            }
            int HeroWeightedPercentage = e.isEnemy()?-1*dmgPercentage:dmgPercentage;
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
        int estimatedDamage = cast.getHeal();
        Logger.aiLog(" estimated heal:"+estimatedDamage);
        for (Hero e : targets) {
            Logger.aiLog(" target:"+e.getName());
            int healPercentage = e.simulateHealInPercentages(this.arena.activeHero, estimatedDamage, cast);
            if (e.getCurrentLifePercentage() < 30) {
                Logger.aiLog(" low life bonus!");
                healPercentage *= 3;
            }

            int HeroWeightedPercentage = e.isEnemy()?healPercentage:-1*healPercentage;
            Logger.aiLog(" weighted heal percentage:"+HeroWeightedPercentage);
            weightedPercentages += HeroWeightedPercentage;
        }
        int healRating = weightedPercentages / 10;
        Logger.aiLog(" sum weighted heal percentage:"+weightedPercentages);
        Logger.aiLog(" healRating:"+healRating);
        return healRating;
    }
    private int getTempoRating(Skill cast) {
        int tempoRating = 0;
        if (cast.tags.contains(Skill.SkillTag.SETUP)
                && this.beatDownMeter < 7 && this.beatDownMeter > 3) {
            tempoRating = 2;
        }
        Logger.aiLog(" temporating:"+tempoRating);
        return tempoRating;
    }
    private int getCustomAIRating(Skill cast, Hero[] targets) {
        int result = 0;
        for (Hero e: targets) {
            result+=cast.getAIRating(e,beatDownMeter);
        }
        Logger.aiLog(" customrating:"+result);
        return result;
    }
//    private int getLethalityRating(Hero[] targets, int damagePercentage) {
//        int lethality = 0;
//        for (Hero target : targets) {
//            int currentLifePercentage = target.getStat(Stat.CURRENT_LIFE) * 100 / target.getStat(Stat.MAX_LIFE);
//            if (damagePercentage > currentLifePercentage) {
//                if (target.enemy == this.arena.activeHero.enemy) {
//                    lethality -= 2;
//                } else {
//                    lethality++;
//                }
//            }
//        }
//        return lethality * 5;
//    }
//    private int getHealRating(Hero[] targets, int healPercentage) {
//        int healRating = 0;
//        for (Hero target : targets) {
//            int currentLifePercentage = target.getStat(Stat.CURRENT_LIFE) * 100 / target.getStat(Stat.MAX_LIFE);
//            int weightedHeal = healPercentage * 2 / currentLifePercentage;
//            healRating += weightedHeal;
//        }
//        return healRating;
//    }
//    private int getRescueRating(Hero[] targets) {
//        int rescue = 0;
//        for (Hero target : targets) {
//            int currentLifePercentage = target.getStat(Stat.CURRENT_LIFE) * 100 / target.getStat(Stat.MAX_LIFE);
//            if (currentLifePercentage < 20) {
//                rescue++;
//            }
//        }
//        return rescue * 3;
//    }
//    private int getEffectRatingForSkill(Skill s, Hero[] targets) {
//        int effectRating = 0;
//        for (Effect e: s.getEffects()) {
//            effectRating += getEffectTargetRating(e, targets);
//        }
//        for (Effect e: s.getCasterEffects()) {
//            effectRating += getEffectCasterRating(e);
//        }
//        //TODO rebuild to use effectCondition
////        for (Map.Entry<Effect, List<Effect>> conditional : s.conditionals.entrySet()) {
////            if (conditional.getKey().targetCaster && this.arena.activeHero.hasEffect(conditional.getKey()) > 0) {
////                for (Effect subEffect : conditional.getValue()) {
////                    effectRating += getEffectRating(subEffect, targets);
////                }
////            } else {
////                for (Effect subEffect : conditional.getValue()) {
////                   for (Hero target : targets) {
////                       if (target.hasEffect(subEffect) > 0) {
////                           effectRating += getEffectRating(subEffect, new Hero[]{target});
////                       }
////                   }
////                }
////            }
////        }
//        return effectRating;
//    }
//    private int getEffectCasterRating(Effect e) {
//        int effectRating = 0;
//        if (e.permanentEffect != null) {
//            effectRating += getChangeEffectRating(e, this.arena.activeHero);
//        }
//        return effectRating;
//    }
//
//    private int getEffectTargetRating(Effect e, Hero[] targets) {
//        int effectRating = 0;
//        {
//            for (Hero t : targets) {
//                if (e.permanentEffect != null) {
//                    effectRating += getChangeEffectRating(e, t);
//                } else {
//                    effectRating += getOtherRating(e, t);
//                }
//            }
//        }
//        return effectRating;
//    }
//    private int getChangeEffectRating(Effect e, Hero t) {
//        int effectRating = 0;
//        boolean isEnemy = t.enemy != this.arena.activeHero.enemy;
//        Hero[] teamMates = this.arena.getTeam(this.arena.activeHero.enemy);
//        for (Hero teamMate : teamMates) {
////            if (hasPayoff(e.permanentEffect.getClass(), teamMate, isEnemy)) {effectRating++;}
//        }
//        if (Angry.class.equals(e.permanentEffect.getClass())) {
//            effectRating += !isEnemy ? getAngryRating(t) : -1;
//        } else if (Advantage.class.equals(e.permanentEffect.getClass())) {
//            effectRating += !isEnemy ? getAdvantageRating(t) : -3;
//        } else if (Ascended.class.equals(e.permanentEffect.getClass())) {
//            effectRating += !isEnemy ? getAscendedRating(t) : -3;
//        } else if (Bleeding.class.equals(e.permanentEffect.getClass())) {
//            effectRating += isEnemy ? getBleedingRating(t) : -3;
//        } else if (Blinded.class.equals(e.permanentEffect.getClass())) {
//            effectRating += isEnemy ? getBlindedRating(t) : -3;
////            case Bounty.class -> effectRating += !isEnemy? getBountyRating(t): -3;
////            case BountyTarget.class -> effectRating += isEnemy? getBountyTargetRating(t): -3;
//        } else if (Burning.class.equals(e.permanentEffect.getClass())) {
//            effectRating += isEnemy ? getBurningRating(t) : -3;
//        } else if (Confused.class.equals(e.permanentEffect.getClass())) {
//            effectRating += isEnemy ? getConfusedRating(t) : -3;
//        } else if (Cover.class.equals(e.permanentEffect.getClass())) {
//            effectRating += !isEnemy ? getCoverRating(t) : -3;
//        } else if (Disadvantage.class.equals(e.permanentEffect.getClass())) {
//            effectRating += isEnemy ? getDisadvantageRating(t) : -3;
//        } else if (Enlightened.class.equals(e.permanentEffect.getClass())) {
//            effectRating += !isEnemy ? getEnlightenedRating(t) : -3;
//        } else if (Paralysed.class.equals(e.permanentEffect.getClass())) {
//            effectRating += isEnemy ? getParalysedRating(t) : -3;
//        } else if (Precise.class.equals(e.permanentEffect.getClass())) {
//            effectRating += !isEnemy ? getPreciseRating(t) : -3;
//        } else if (Resilient.class.equals(e.permanentEffect.getClass())) {
//            effectRating += !isEnemy ? getResilientRating(t) : -3;
//        } else if (Rooted.class.equals(e.permanentEffect.getClass())) {
//            effectRating += isEnemy ? getRootedRating(t) : -3;
//        } else if (Shielded.class.equals(e.permanentEffect.getClass())) {
//            effectRating += !isEnemy ? getShieldedRating(t) : -3;
//        } else if (Steadfast.class.equals(e.permanentEffect.getClass())) {
//            effectRating += !isEnemy ? getSteadFastRating(t) : -3;
//        } else if (Taunted.class.equals(e.permanentEffect.getClass())) {
//            effectRating += isEnemy ? getTauntedRating(t) : -3;
//        } else if (Timeout.class.equals(e.permanentEffect.getClass())) {
//            effectRating += isEnemy ? getTimeoutRating(t) : -3;
//        } else if (Prowess.class.equals(e.permanentEffect.getClass())) {
//            effectRating += !isEnemy ? getVigilantRating(t) : -3;
//        }
//        return effectRating;
//    }
//    private int getOtherRating(Effect effect, Hero t) {
//        boolean isEnemy = t.enemy != this.arena.activeHero.enemy;
//        switch (effect.type) {
//            case BLOCK_ABILITY, RDM_CD_UP, OBJECT_PUSH, OBJECT_PULL -> { return isEnemy ?  3 :  -3;}
//            case RDM_CD_DOWN -> { return isEnemy ? -3 : 3;}
//            case CLEANSE ->  { return getCleanseRating(t, isEnemy); }
//        }
//        return 0;
//    }
//    private int getCoverRating(Hero target) {
//        int payOffs = 0;
//        if (target.role.equals(Hero.ROLE.DPS) || target.role.equals(Hero.ROLE.SUPPORT)) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getShieldedRating(Hero target) {
//        int payOffs = 0;
//        if (target.role.equals(Hero.ROLE.DPS) || target.role.equals(Hero.ROLE.SUPPORT)) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getBleedingRating(Hero target) {
//        int payOffs = 1;
//        if (target.role.equals(Hero.ROLE.BRAWL) || target.role.equals(Hero.ROLE.TANK)) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getSteadFastRating(Hero target) {
//        int payOffs = 1;
//        if (target.role.equals(Hero.ROLE.BRAWL) || target.role.equals(Hero.ROLE.TANK)) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getResilientRating(Hero target) {
//        int payOffs = 1;
//        if (target.role.equals(Hero.ROLE.BRAWL) || target.role.equals(Hero.ROLE.TANK)) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getBlindedRating(Hero target) {
//        int payOffs = 1;
//        if (target.role.equals(Hero.ROLE.DPS) || target.role.equals(Hero.ROLE.BRAWL)) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getVigilantRating(Hero target) {
//        int payOffs = 1;
//        if (target.role.equals(Hero.ROLE.DPS) || target.role.equals(Hero.ROLE.BRAWL)) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getTauntedRating(Hero target) {
//        int payOffs = 1;
//        if (target.role != null && (target.role.equals(Hero.ROLE.DPS) || target.role.equals(Hero.ROLE.BRAWL))) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getPreciseRating(Hero target) {
//        int payOffs = 1;
//        if (target.role != null && (target.role.equals(Hero.ROLE.DPS) || target.role.equals(Hero.ROLE.BRAWL))) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getParalysedRating(Hero target) {
//        int payOffs = 0;
//        payOffs++;
//        if (target.role != null && (target.role.equals(Hero.ROLE.DPS) || target.role.equals(Hero.ROLE.BRAWL))) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getTimeoutRating(Hero target) {
//        int payOffs = 0;
//        payOffs++;
//        if (target.role != null && target.role.equals(Hero.ROLE.DPS)) {
//            payOffs++;
//        }
//        if (this.arena.getPositionInQueue(target) < 3) {payOffs++;}
//        return payOffs;
//    }
//    private int getBurningRating(Hero target) {
//        int payOffs = 1;
//        if (target.role != null && (target.role.equals(Hero.ROLE.BRAWL) || target.role.equals(Hero.ROLE.TANK))) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getEnlightenedRating(Hero t) {
//        int payOffs = 1;
//        if (t.role != null && (t.role.equals(Hero.ROLE.BRAWL) || t.role.equals(Hero.ROLE.TANK))) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getAngryRating(Hero t) {
//        return 1; //TODO
//    }
//    private int getAdvantageRating(Hero t) {
//        int payOffs = 0;
//        payOffs++;
//        if (t.role != null && t.role.equals(Hero.ROLE.DPS)) {
//            payOffs+=2;
//        }
//        if (t.role != null && t.role.equals(Hero.ROLE.BRAWL)) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getDisadvantageRating(Hero t) {
//        int payOffs = 0;
//        payOffs++;
//        if (t.role != null && t.role.equals(Hero.ROLE.DPS)) {
//            payOffs+=2;
//        }
//        if (t.role != null && t.role.equals(Hero.ROLE.BRAWL)) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getAscendedRating(Hero t) {
//        int payOffs = 0;
//        payOffs++;
//        if (t.role != null && t.role.equals(Hero.ROLE.DPS)) {
//            payOffs+=4;
//        } else if (t.role != null && t.role.equals(Hero.ROLE.BRAWL)) {
//            payOffs+=3;
//        } else {
//            payOffs++;
//        }
//
//        return payOffs;
//    }
//    private int getConfusedRating(Hero target) {
//        int payOffs = 0;
//        payOffs++;
//        if (target.role != null && target.role.equals(Hero.ROLE.DPS)) {
//            payOffs+=2;
//        }
//        if (target.role != null && target.role.equals(Hero.ROLE.BRAWL)) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getRootedRating(Hero t) {
//        int payOffs = 0;
//        if (t.role != null && (t.role.equals(Hero.ROLE.DPS) || t.role.equals(Hero.ROLE.SUPPORT))) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getCleanseRating(Hero target, boolean isEnemy) {
//        if (isEnemy) {
//            return -10;
//        }
//        boolean hasDebuffs = target.currentEffects.stream()
//                .anyMatch(effect -> effect.type.equals(Effect.EffectType.PERMANENT_EFFECT) &&
//                        effect.permanentEffect.getType().equals(ChangeEffect.ChangeEffectType.DEBUFF));
//        if (hasDebuffs) {
//            return 5;
//        }
//        return 0;
//    }
//    private int getClearRating(Hero target, boolean isEnemy) {
//
//        if (isEnemy && !target.currentEffects.isEmpty()) {
//            return -5;
//        }
//        if (!isEnemy && !target.currentEffects.isEmpty()) {
//            //keep it simple...
////            if (target.currentEffects.stream().anyMatch(e->e.status != null && hasTeammatePayoff(e.status.name, target))) {
////                return -5;
////            }
//            return 5;
//        }
//        return 0;
//    }
//    private <T extends ChangeEffect> boolean hasPayoff(Class<T> infliction, Hero teamMate, boolean targetIsEnemy) {
//        return Arrays.stream(teamMate.skills)
//                .filter(Objects::nonNull)
//                .anyMatch(skill -> Stream.concat(skill.effects.stream(), skill.casterEffects.stream())
//                        .anyMatch(effect -> effect.condition != null && effect.condition.effectAsCondition.permanentEffect.getClass().equals(infliction)
//                                && ((effect.condition.effectAsConditionForTarget && targetIsEnemy) /*|| best not to complicate things for a start...
//                                                (effect.targetCaster && target.equals(teamMate) && skill.attributes.contains(SkillAttribute.BUFF))*/)));
//    }
//    private boolean hasTeammatePayoff(String infliction, Hero teamMate) {
//        return Arrays.stream(teamMate.skills)
//                .anyMatch(skill -> skill.conditionals.keySet()
//                        .stream()
//                        .anyMatch(effect -> effect.targetCaster &&
//                                effect.status.equals(infliction) && skill.attributes.contains(SkillAttribute.BUFF)));
//    }

    private int[] getOptimalPositions(Map<int[], Integer> damageMap) {
        int highestNr = 0;
        int[] targets = null;
        for (Map.Entry<int[], Integer> entry: damageMap.entrySet()) {
            if (entry.getValue() > highestNr) {
                targets = entry.getKey();
                highestNr = entry.getValue();
            }
        }
        return targets;
    }

    private List<Hero[]> getPossibleTargetGroups(Skill s) {
        List<Hero[]> preFilter = new ArrayList<>();
        List<Hero[]> result = new ArrayList<>();
        switch (s.getTargetType()) {
            case SINGLE, SINGLE_ALLY, SINGLE_ALLY_IN_FRONT, SINGLE_ALLY_BEHIND -> setSingleTargetGroups(s, preFilter);
            case LINE -> setLineTargetGroups(s, preFilter);
            case SELF -> preFilter.add(new Hero[]{this.arena.activeHero});
            case ALL -> setAllTargetGroups(preFilter);
            case ALL_ALLY -> setAllAllyTargetGroups(preFilter);
            case ALL_ENEMY -> setAllEnemyTargetGroups(preFilter);
            case FIRST_TWO_ENEMIES -> setFirstTwoEnemyTargetGroups(preFilter);
            case FIRST_ENEMY -> setFirstEnemyTargetGroups(preFilter);
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
    private void setSingleTargetGroups(Skill s, List<Hero[]> result) {
        int[] targetMatrix = s.setupTargetMatrix();
        for (int i : targetMatrix) {
            Hero[] targets = new Hero[1];
            targets[0] = this.arena.getAtPosition(i);
            result.add(targets);
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
    private void setAllAllyTargetGroups(List<Hero[]> results) {
        results.add(this.arena.activeHero.getAllies().toArray(new Hero[0]));
    }
    private void setAllEnemyTargetGroups(List<Hero[]> results) {
        List<Hero> enemies = this.arena.getAllLivingEntities().stream()
                .filter(e->e.isEnemy() != this.arena.activeHero.isEnemy()).toList();
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
