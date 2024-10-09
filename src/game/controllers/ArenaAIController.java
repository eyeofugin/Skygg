package game.controllers;

import framework.Logger;
import framework.states.Arena;
import game.entities.Entity;
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
        for (Entity ai : this.arena.enemies.entities) {
            int averageLife = (int) Arrays.stream(this.arena.enemies.entities).map(e->e.getStat(Stat.MAX_LIFE)).mapToInt(Integer::intValue)
                    .summaryStatistics().getAverage();
            calculatePreferredPosition(ai, averageLife);
        }
    }

    private void estimateBeatDownMeter() {
        int mediumValue = 500;
        for (Entity e : this.arena.getAllLivingEntities()) {
            int lifePercentage = e.getStat(Stat.CURRENT_LIFE) * 100 / e.getStat(Stat.MAX_LIFE);
            mediumValue += e.enemy?lifePercentage:-1*lifePercentage;
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
            if (this.arena.performSuccessCheck(s)) {
                Logger.logLn("AI will perform " + s.name + "/" + bestAction.rating + " at position " + bestAction.targets[0].position);
                this.arena.activeSkill = s;
                this.arena.activeSkill.setTargets(List.of(bestAction.targets));

                this.arena.activeSkill.perform();
                this.arena.activeTargets = bestAction.targets;
                this.arena.nextAction = "resolveSkill";
                this.arena.status = Arena.Status.WAIT_ON_ANIMATION;
            }
        }
    }


    private void evaluateSkills() {
        for (Skill s: this.arena.activeEntity.skills) {
            if (s != null) {
                Logger.aiLogln("evaluate:" +s.name);
                Skill cast = s._cast;
                if (cast == null || !this.arena.activeEntity.canPerform(cast)) {
                    Logger.aiLogln("ai cannot perform");
                    continue;
                }
                for (Entity[] targets : getPossibleTargetGroups(cast)) {
                    Logger.aiLog("rating for targetgroup ");
                    for (Entity target : targets) {
                        Logger.aiLog(target.name + " ");
                    }
                    int rating = 0; //Rating borders ~+-20(==400%dmg/heal)
                    rating += getDamageRating(cast, targets);
                    rating += getHealRating(cast,targets);
                    rating += getTempoRating(cast);
                    rating += getCustomAIRating(cast, targets);
                    //TODO missing move rating

                    Logger.aiLogln("\n\trating:"+rating);

                    Action action = new Action();
                    action.skill = cast;
                    action.targets = targets;
                    action.rating = rating;
                    this.turnOptions.add(action);
                }
            }
        }
    }
    private int getDamageRating(Skill cast, Entity[] targets) {
        int weightedPercentages = 0;
        int lethality = this.arena.activeEntity.getCastingStat(cast, Stat.LETHALITY);
        int estimatedDamage = cast.getDamage();
        Logger.aiLog(" estimated dmg:"+estimatedDamage);
        for (Entity e : targets) {
            Logger.aiLog(" target:"+e.name);
            int dmgPercentage = e.simulateDamageInPercentages(this.arena.activeEntity, estimatedDamage, cast.getDamageType(), lethality, cast);
            if (dmgPercentage >= e.getMissingLifePercentage()) {
                Logger.aiLog(" low life bonus!");
                dmgPercentage *= 5;
            }
            int entityWeightedPercentage = e.enemy?-1*dmgPercentage:dmgPercentage;
            Logger.aiLog(" weighted dmg percentage:"+entityWeightedPercentage);
            weightedPercentages += entityWeightedPercentage;
        }
        int damageRating = weightedPercentages / 10;
        Logger.aiLog(" sum weighted dmg percentage:"+weightedPercentages);
        Logger.aiLog(" dmgRating:"+damageRating);
        return damageRating;
    }
    private int getHealRating(Skill cast, Entity[] targets) {
        int weightedPercentages = 0;
        int estimatedDamage = cast.getHeal();
        Logger.aiLog(" estimated heal:"+estimatedDamage);
        for (Entity e : targets) {
            Logger.aiLog(" target:"+e.name);
            int healPercentage = e.simulateHealInPercentages(this.arena.activeEntity, estimatedDamage, cast);
            if (e.getCurrentLifePercentage() < 30) {
                Logger.aiLog(" low life bonus!");
                healPercentage *= 3;
            }

            int entityWeightedPercentage = e.enemy?healPercentage:-1*healPercentage;
            Logger.aiLog(" weighted heal percentage:"+entityWeightedPercentage);
            weightedPercentages += entityWeightedPercentage;
        }
        int healRating = weightedPercentages / 10;
        Logger.aiLog(" sum weighted heal percentage:"+weightedPercentages);
        Logger.aiLog(" healRating:"+healRating);
        return healRating;
    }
    private int getTempoRating(Skill cast) {
        int tempoRating = 0;
        if (cast.tags.contains(Skill.AiSkillTag.SETUP)
                && this.beatDownMeter < 7 && this.beatDownMeter > 3) {
            tempoRating = 2;
        }
        Logger.aiLog(" temporating:"+tempoRating);
        return tempoRating;
    }
    private int getCustomAIRating(Skill cast, Entity[] targets) {
        int result = 0;
        for (Entity e: targets) {
            result+=cast.getAIRating(e,beatDownMeter);
        }
        Logger.aiLog(" customrating:"+result);
        return result;
    }
//    private int getLethalityRating(Entity[] targets, int damagePercentage) {
//        int lethality = 0;
//        for (Entity target : targets) {
//            int currentLifePercentage = target.getStat(Stat.CURRENT_LIFE) * 100 / target.getStat(Stat.MAX_LIFE);
//            if (damagePercentage > currentLifePercentage) {
//                if (target.enemy == this.arena.activeEntity.enemy) {
//                    lethality -= 2;
//                } else {
//                    lethality++;
//                }
//            }
//        }
//        return lethality * 5;
//    }
//    private int getHealRating(Entity[] targets, int healPercentage) {
//        int healRating = 0;
//        for (Entity target : targets) {
//            int currentLifePercentage = target.getStat(Stat.CURRENT_LIFE) * 100 / target.getStat(Stat.MAX_LIFE);
//            int weightedHeal = healPercentage * 2 / currentLifePercentage;
//            healRating += weightedHeal;
//        }
//        return healRating;
//    }
//    private int getRescueRating(Entity[] targets) {
//        int rescue = 0;
//        for (Entity target : targets) {
//            int currentLifePercentage = target.getStat(Stat.CURRENT_LIFE) * 100 / target.getStat(Stat.MAX_LIFE);
//            if (currentLifePercentage < 20) {
//                rescue++;
//            }
//        }
//        return rescue * 3;
//    }
//    private int getEffectRatingForSkill(Skill s, Entity[] targets) {
//        int effectRating = 0;
//        for (Effect e: s.getEffects()) {
//            effectRating += getEffectTargetRating(e, targets);
//        }
//        for (Effect e: s.getCasterEffects()) {
//            effectRating += getEffectCasterRating(e);
//        }
//        //TODO rebuild to use effectCondition
////        for (Map.Entry<Effect, List<Effect>> conditional : s.conditionals.entrySet()) {
////            if (conditional.getKey().targetCaster && this.arena.activeEntity.hasEffect(conditional.getKey()) > 0) {
////                for (Effect subEffect : conditional.getValue()) {
////                    effectRating += getEffectRating(subEffect, targets);
////                }
////            } else {
////                for (Effect subEffect : conditional.getValue()) {
////                   for (Entity target : targets) {
////                       if (target.hasEffect(subEffect) > 0) {
////                           effectRating += getEffectRating(subEffect, new Entity[]{target});
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
//            effectRating += getChangeEffectRating(e, this.arena.activeEntity);
//        }
//        return effectRating;
//    }
//
//    private int getEffectTargetRating(Effect e, Entity[] targets) {
//        int effectRating = 0;
//        {
//            for (Entity t : targets) {
//                if (e.permanentEffect != null) {
//                    effectRating += getChangeEffectRating(e, t);
//                } else {
//                    effectRating += getOtherRating(e, t);
//                }
//            }
//        }
//        return effectRating;
//    }
//    private int getChangeEffectRating(Effect e, Entity t) {
//        int effectRating = 0;
//        boolean isEnemy = t.enemy != this.arena.activeEntity.enemy;
//        Entity[] teamMates = this.arena.getTeam(this.arena.activeEntity.enemy);
//        for (Entity teamMate : teamMates) {
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
//    private int getOtherRating(Effect effect, Entity t) {
//        boolean isEnemy = t.enemy != this.arena.activeEntity.enemy;
//        switch (effect.type) {
//            case BLOCK_ABILITY, RDM_CD_UP, OBJECT_PUSH, OBJECT_PULL -> { return isEnemy ?  3 :  -3;}
//            case RDM_CD_DOWN -> { return isEnemy ? -3 : 3;}
//            case CLEANSE ->  { return getCleanseRating(t, isEnemy); }
//        }
//        return 0;
//    }
//    private int getCoverRating(Entity target) {
//        int payOffs = 0;
//        if (target.role.equals(Entity.ROLE.DPS) || target.role.equals(Entity.ROLE.SUPPORT)) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getShieldedRating(Entity target) {
//        int payOffs = 0;
//        if (target.role.equals(Entity.ROLE.DPS) || target.role.equals(Entity.ROLE.SUPPORT)) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getBleedingRating(Entity target) {
//        int payOffs = 1;
//        if (target.role.equals(Entity.ROLE.BRAWL) || target.role.equals(Entity.ROLE.TANK)) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getSteadFastRating(Entity target) {
//        int payOffs = 1;
//        if (target.role.equals(Entity.ROLE.BRAWL) || target.role.equals(Entity.ROLE.TANK)) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getResilientRating(Entity target) {
//        int payOffs = 1;
//        if (target.role.equals(Entity.ROLE.BRAWL) || target.role.equals(Entity.ROLE.TANK)) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getBlindedRating(Entity target) {
//        int payOffs = 1;
//        if (target.role.equals(Entity.ROLE.DPS) || target.role.equals(Entity.ROLE.BRAWL)) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getVigilantRating(Entity target) {
//        int payOffs = 1;
//        if (target.role.equals(Entity.ROLE.DPS) || target.role.equals(Entity.ROLE.BRAWL)) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getTauntedRating(Entity target) {
//        int payOffs = 1;
//        if (target.role != null && (target.role.equals(Entity.ROLE.DPS) || target.role.equals(Entity.ROLE.BRAWL))) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getPreciseRating(Entity target) {
//        int payOffs = 1;
//        if (target.role != null && (target.role.equals(Entity.ROLE.DPS) || target.role.equals(Entity.ROLE.BRAWL))) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getParalysedRating(Entity target) {
//        int payOffs = 0;
//        payOffs++;
//        if (target.role != null && (target.role.equals(Entity.ROLE.DPS) || target.role.equals(Entity.ROLE.BRAWL))) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getTimeoutRating(Entity target) {
//        int payOffs = 0;
//        payOffs++;
//        if (target.role != null && target.role.equals(Entity.ROLE.DPS)) {
//            payOffs++;
//        }
//        if (this.arena.getPositionInQueue(target) < 3) {payOffs++;}
//        return payOffs;
//    }
//    private int getBurningRating(Entity target) {
//        int payOffs = 1;
//        if (target.role != null && (target.role.equals(Entity.ROLE.BRAWL) || target.role.equals(Entity.ROLE.TANK))) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getEnlightenedRating(Entity t) {
//        int payOffs = 1;
//        if (t.role != null && (t.role.equals(Entity.ROLE.BRAWL) || t.role.equals(Entity.ROLE.TANK))) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getAngryRating(Entity t) {
//        return 1; //TODO
//    }
//    private int getAdvantageRating(Entity t) {
//        int payOffs = 0;
//        payOffs++;
//        if (t.role != null && t.role.equals(Entity.ROLE.DPS)) {
//            payOffs+=2;
//        }
//        if (t.role != null && t.role.equals(Entity.ROLE.BRAWL)) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getDisadvantageRating(Entity t) {
//        int payOffs = 0;
//        payOffs++;
//        if (t.role != null && t.role.equals(Entity.ROLE.DPS)) {
//            payOffs+=2;
//        }
//        if (t.role != null && t.role.equals(Entity.ROLE.BRAWL)) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getAscendedRating(Entity t) {
//        int payOffs = 0;
//        payOffs++;
//        if (t.role != null && t.role.equals(Entity.ROLE.DPS)) {
//            payOffs+=4;
//        } else if (t.role != null && t.role.equals(Entity.ROLE.BRAWL)) {
//            payOffs+=3;
//        } else {
//            payOffs++;
//        }
//
//        return payOffs;
//    }
//    private int getConfusedRating(Entity target) {
//        int payOffs = 0;
//        payOffs++;
//        if (target.role != null && target.role.equals(Entity.ROLE.DPS)) {
//            payOffs+=2;
//        }
//        if (target.role != null && target.role.equals(Entity.ROLE.BRAWL)) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getRootedRating(Entity t) {
//        int payOffs = 0;
//        if (t.role != null && (t.role.equals(Entity.ROLE.DPS) || t.role.equals(Entity.ROLE.SUPPORT))) {
//            payOffs++;
//        }
//        return payOffs;
//    }
//    private int getCleanseRating(Entity target, boolean isEnemy) {
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
//    private int getClearRating(Entity target, boolean isEnemy) {
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
//    private <T extends ChangeEffect> boolean hasPayoff(Class<T> infliction, Entity teamMate, boolean targetIsEnemy) {
//        return Arrays.stream(teamMate.skills)
//                .filter(Objects::nonNull)
//                .anyMatch(skill -> Stream.concat(skill.effects.stream(), skill.casterEffects.stream())
//                        .anyMatch(effect -> effect.condition != null && effect.condition.effectAsCondition.permanentEffect.getClass().equals(infliction)
//                                && ((effect.condition.effectAsConditionForTarget && targetIsEnemy) /*|| best not to complicate things for a start...
//                                                (effect.targetCaster && target.equals(teamMate) && skill.attributes.contains(SkillAttribute.BUFF))*/)));
//    }
//    private boolean hasTeammatePayoff(String infliction, Entity teamMate) {
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

    private List<Entity[]> getPossibleTargetGroups(Skill s) {
        List<Entity[]> result = new ArrayList<>();
        switch (s.getTargetType()) {
            case SINGLE, SINGLE_ALLY, SINGLE_ALLY_IN_FRONT -> setSingleTargetGroups(s, result);
            case LINE -> setLineTargetGroups(s, result);
            case SELF -> result.add(new Entity[]{this.arena.activeEntity});
            case ALL -> setAllTargetGroups(result);
            case ALL_ALLY -> setAllAllyTargetGroups(result);
            case ALL_ENEMY -> setAllEnemyTargetGroups(result);
            case FIRST_TWO_ENEMIES -> setFirstTwoEnemyTargetGroups(result);
            case FIRST_ENEMY -> setFirstEnemyTargetGroups(result);
        }
        result.forEach(entities-> entities = removeNullValuesAndTheDead(entities));
        return result;
    }
    private void setSingleTargetGroups(Skill s, List<Entity[]> result) {
        int[] targetMatrix = s.setupTargetMatrix();
        for (int i : targetMatrix) {
            Entity[] targets = new Entity[1];
            targets[0] = this.arena.getAtPosition(i);
            result.add(targets);
        }
    }
    private void setLineTargetGroups(Skill s, List<Entity[]> results) {
        int[] targetMatrix = s.setupTargetMatrix();
        int casterPosition = this.arena.activeEntity.position;
        if (targetMatrix.length>0) {
            List<Entity> lineGroup = new ArrayList<>();
            for (int i = targetMatrix[0]; i < casterPosition; i++) {
                Entity target = this.arena.getAtPosition(i);
                lineGroup.add(target);
            }
            results.add(lineGroup.toArray(new Entity[0]));
        }
    }
    private void setAllTargetGroups(List<Entity[]> results) {
        results.add(this.arena.getAllLivingEntities());
    }
    private void setAllAllyTargetGroups(List<Entity[]> results) {
        results.add(this.arena.activeEntity.getAllies().toArray(new Entity[0]));
    }
    private void setAllEnemyTargetGroups(List<Entity[]> results) {
        List<Entity> enemies = Arrays.stream(this.arena.getAllLivingEntities())
                .filter(e->e.enemy != this.arena.activeEntity.enemy).toList();
        results.add(enemies.toArray(new Entity[0]));
    }
    private void setFirstTwoEnemyTargetGroups(List<Entity[]> results) {
        results.add(new Entity[]{this.arena.getAtPosition(2),this.arena.getAtPosition(3)});
    }
    private void setFirstEnemyTargetGroups(List<Entity[]> results) {
        results.add(new Entity[]{this.arena.getAtPosition(3)});
    }
    private Entity[] removeNullValuesAndTheDead(Entity[] source) {
        List<Entity> resultList = new ArrayList<>();
        for (Entity e: source) {
            if (e != null && e.getStat(Stat.CURRENT_LIFE) > 0) {
                resultList.add(e);
            }
        }
        return resultList.toArray(new Entity[0]);
    }

    private void calculatePreferredPosition(Entity ai, int averageTeamLife) {
        List<Double> factors = new ArrayList<>();
        for (int i = 0; i < ai.skills.length; i++) {
            Skill s = ai.skills[i];
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
        factors.add(ai.getStat(Stat.MAX_LIFE) < averageTeamLife
                ? 5.0 : 8.0);

        ai.effectivePosition = (int) Math.round(factors.stream()
                .mapToDouble(a -> a)
                .average().orElse(8.0));
    }
}
