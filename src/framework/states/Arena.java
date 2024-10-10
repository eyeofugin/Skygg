package framework.states;

import framework.Engine;
import framework.Logger;
import framework.Property;
import framework.graphics.GUIElement;
import framework.graphics.elements.ArenaHeroElement;
import framework.graphics.text.Color;
import framework.graphics.text.TextAlignment;
import framework.resources.SpriteLibrary;
import framework.graphics.containers.HUD;
import game.controllers.ArenaAIController;
import game.entities.Entity;
import game.entities.EntityGroup;
import game.entities.HeroTeam;
import game.objects.Equipment;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;
import utils.MyMaths;

import java.awt.Point;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Random;
import java.util.stream.Stream;

public class Arena extends GUIElement {

    public Engine engine;
    private boolean started = false;

    private final HUD hud;
    public ArenaAIController aiController;

    public Queue<ArenaHeroElement> order = new LinkedList<>();

    public enum Status {
        TARGET_CHOICE,
        WAIT_ON_ANIMATION,
        WAIT_ON_AI,
        WAIT_ON_HUD
    }
    public Status status = Status.WAIT_ON_HUD;
    public String nextAction = null;
    public int activePointer = 0;
    public int[] pointers = new int[0];
    public int matrixPointer = 0;
    public int[] targetMatrix = new int[0];

    public Skill activeSkill;
    public ArenaHeroElement activeEntity = null;
    public ArenaHeroElement[] activeTargets = null;
    public HeroTeam friends;
    public HeroTeam enemies;
    private final int[] friendXPos = new int[]{0, 68, 136, 204};
    private final int[] enemyXPos = new int[]{312, 380, 448, 516};

    public Arena() {
        super(0,0);
        this.hud = null;
    }

    public Arena(Engine e) {
        super(Engine.X, Engine.Y);
        this.engine = e;
        this.hud = new HUD(e);
        this.hud.setArena(this);
        this.aiController = new ArenaAIController(this);
    }

    public void setTeams(HeroTeam friend, HeroTeam enemies) {
        this.friends = friend;
        this.enemies = enemies;
        initialOrder();
        this.activeEntity = this.order.peek();
        this.hud.setActiveEntity(this.activeEntity.enemy? this.friends.entities[3]: this.activeEntity);
        this.aiController.setup();
        this.updateEntities();
    }
    private void initialOrder() {
        java.util.List<ArenaHeroElement> backwards = new java.util.ArrayList<>(Stream.concat(Arrays.stream(friends.entities), Arrays.stream(enemies.entities))
                .filter(Objects::nonNull)
                .toList());
        Collections.shuffle(backwards);
        backwards = backwards.stream()
                .sorted((Entity e1, Entity e2)->Integer.compare(e2.getStat(Stat.SPEED), e1.getStat(Stat.SPEED)))
                .toList();
        this.order.addAll(backwards);
    }

    @Override
    public void update(int frame) {
        if(!started) {
            started = true;
            startTurn();
        }
        switch (status) {
            case TARGET_CHOICE -> updateChooseTarget();
            case WAIT_ON_ANIMATION -> updateWaitOnAnimation();
        }
        this.hud.update(frame);
        updateAnimations(frame);
    }
    private void updateAnimations(int frame) {
        Arrays.stream(friends.entities).filter(Objects::nonNull).forEach(e->e.update(frame));
        Arrays.stream(enemies.entities).filter(Objects::nonNull).forEach(e->e.update(frame));
    }
    private void updateWaitOnAnimation() {
        int amntRunning = 0;
        for (Entity e : Stream.concat(Arrays.stream(friends.entities), Arrays.stream(enemies.entities)).toList()) {
            if (e!= null && e.anim.waitFor) {
                amntRunning++;
            }
        }
        if (amntRunning == 0) {
            if (this.nextAction != null) {
                try {
                    Logger.logLn(this.nextAction);
                    Method method = this.getClass().getMethod(this.nextAction);
                    method.invoke(this);
                } catch (Exception e) {
                    e.printStackTrace();
                    this.nextAction = null;
                }
            } else {
                resumeTurn();
            }
        }
    }

    private void updateChooseTarget() {
        if (engine.keyB._leftPressed) {
            this.getNextMatrixPointer(-1);
            this.activePointer = this.targetMatrix[matrixPointer];
        }
        if (engine.keyB._rightPressed) {
            this.getNextMatrixPointer(1);
            this.activePointer = this.targetMatrix[matrixPointer];
        }
        setPointerArray();
        if (engine.keyB._backPressed) {
            this.status = Status.WAIT_ON_HUD;
            this.hud.activateTeamOverview();
            this.activeSkill.resetCast();
            this.pointers = new int[0];
            this.targetMatrix = new int[0];
        }
        if (engine.keyB._enterPressed) {
            performSkill();
            this.pointers = new int[0];
            this.targetMatrix = new int[0];
        }
    }
    private void getNextMatrixPointer(int dir) {
        int nextMatrixPointer = this.matrixPointer+dir;
        if (nextMatrixPointer >= 0 && nextMatrixPointer < this.targetMatrix.length) {
            this.matrixPointer = nextMatrixPointer;
        }
    }
    private void setPointerArray() {
        if (this.status == Status.TARGET_CHOICE) {
            if (this.activeSkill.getTargetType().equals(TargetType.SINGLE)
                    || this.activeSkill.getTargetType().equals(TargetType.SINGLE_ALLY)
                    || this.activeSkill.getTargetType().equals(TargetType.SINGLE_ALLY_IN_FRONT)) {
                this.pointers = getSingleTargets();
            } else if (this.activeSkill.getTargetType().equals(TargetType.LINE)) {
                this.pointers = getLineTargets();
            }
        }
    }

    private void removeTheDead() {
        for (int i = 0; i < this.friends.entities.length; i++) {
            if (this.friends.entities[i] != null && this.friends.entities[i].getStat(Stat.CURRENT_LIFE) < 1) {
                this.friends.fallen.add(this.friends.entities[i]);
                this.order.remove(this.friends.entities[i]);
                this.friends.entities[i] = null;
                for (int j = i-1; j >= 0; j--) {
                    if (this.friends.entities[j] != null) {
                        this.friends.entities[j+1] = this.friends.entities[j];
                        this.friends.entities[j+1].position = j+1;
                        this.friends.entities[j] = null;
                    }
                }
            }
        }
        for (int i = 0; i < this.enemies.entities.length; i++) {
            if (this.enemies.entities[i] != null && this.enemies.entities[i].getStat(Stat.CURRENT_LIFE) < 1) {
                this.enemies.fallen.add(this.enemies.entities[i]);
                this.order.remove(this.enemies.entities[i]);
                this.enemies.entities[i] = null;
                for (int j = i + 1; j < this.enemies.entities.length; j++) {
                    if (this.enemies.entities[j] != null) {
                        this.enemies.entities[j - 1] = this.enemies.entities[j];
                        this.enemies.entities[j - 1].position = j-1 + 4;
                        this.enemies.entities[j] = null;
                    }
                }
            }
        }
    }
    private void updateEntities() {
        for (Entity e : this.getAllLivingEntities()) {
            if (e != null) {
                e.prepareUpdate();
            }
        }
        for (Entity e : this.getAllLivingEntities()) {
            if (e != null) {
                e.update();
            }
        }
    }
    public void devStartTurn(int entityIndex) {
        this.activeEntity = this.getAtPosition(entityIndex);
        this.activeEntity.startOfTurn();
        this.removeTheDead();
        this.updateEntities();
        this.activeEntity.prepareCast();
    }
    public void devDoTurn(int skillIndex, Entity[] targets) {
        this.activeSkill = this.activeEntity.skills[skillIndex]._cast;
        this.activeSkill.setTargets(java.util.List.of(targets));
        Logger.logLn("Load Skill " + this.activeSkill.name);
        if (this.performSuccessCheck(this.activeSkill)) {
            this.activeSkill.perform();
            this.activeSkill.resolve();
        }
    }
    public void devEndTurn() {
        this.activeEntity.endOfTurn();
    }
    private void resumeTurn() {
        Logger.logLn("resumeTurn()");
        this.removeTheDead();
        this.updateEntities();
        this.activeEntity.prepareCast();
        if (this.activeEntity.getStat(Stat.CURRENT_ACTION) > 0) {
            if (this.activeEntity.enemy) {
                aiTurn();
            } else {
                this.status = Status.WAIT_ON_HUD;
                this.hud.activateTeamOverview();
                this.hud.setActiveEntity(this.activeEntity);
            }
        } else {
            endOfTurn();
        }
    }
    private void endOfTurn() {
        Logger.logLn("endOfTurn()");
        this.activeEntity.endOfTurn();
        Entity lastActiveUnit = this.order.remove();
        this.order.add(lastActiveUnit);
//        for(Entity e: this.getAllLivingEntities()) {
//            Logger.logLn(e.toString());
//        }
        startTurn();
    }
    private void startTurn() {
        Logger.logLn("startTurn()");
        this.activeEntity = this.order.peek();
        if (this.activeEntity != null) {
            Logger.logLn("-----------------------------");
            Logger.logLn("Start turn for " + this.activeEntity.name + " " + this.activeEntity.position);
            this.activeEntity.startOfTurn();
            resumeTurn();
        }
    }
    private void aiTurn() {
        Logger.logLn("aiTurn()");
        this.status = Status.WAIT_ON_AI;
        this.aiController.turn();
//        this.activeEntity.changeStatTo(Stat.CURRENT_ACTION,0);

//        endOfTurn();
    }

    private void performSkill() {
        if (this.performSuccessCheck(this.activeSkill)) {
            if (this.activeSkill.getTargets() == null || this.activeSkill.getTargets().isEmpty()) {
                this.activeTargets = getEntitiesAt(this.pointers);
                this.activeSkill.setTargets(java.util.List.of(this.activeTargets));
            }
            Logger.logLn("Player will perform " + this.activeSkill.name + " at position " + this.activePointer);
            //allEntities.onPerform(skill, targets);
            this.activeSkill.perform();
            this.pointers = new int[0];
            this.nextAction = "resolveSkill";
            this.status = Status.WAIT_ON_ANIMATION;
        }
    }
    public boolean performSuccessCheck(Skill skill) {
        boolean skillCheck = this.skillperformSuccessCheck(skill);
        boolean effectCheck = this.effectperformSuccessCheck(skill);
        boolean equipmentCheck = this.equipmentperformSuccessCheck(skill);
        boolean canPerform = skillCheck && effectCheck && equipmentCheck;
        Logger.logLn("can perform: " + canPerform);
        return canPerform;
    }
    private boolean skillperformSuccessCheck(Skill cast) {
        boolean check = true;
        for (Skill s : Arrays.stream(this.getAllLivingEntities())
                .flatMap(entity -> Arrays.stream(entity.skills))
                .filter(Objects::nonNull).toList()) {
            check = s.performSuccessCheck(cast) && check;
        }
        return check;
    }
    private boolean effectperformSuccessCheck(Skill cast) {
        boolean check = true;
        for (Effect e: Arrays.stream(this.getAllLivingEntities())
                .flatMap(entity->entity.currentEffects.stream()).toList()) {
            check = e.performSuccessCheck(cast) && check;
        }
        return check;
    }
    private boolean equipmentperformSuccessCheck(Skill cast) {
        boolean check = true;
        for (Equipment e: Arrays.stream(this.getAllLivingEntities())
                .flatMap(entity->entity.equipments.stream()).toList()) {
            check = e.performSuccessCheck(cast) && check;
        }
        return check;
    }
    public void resolveSkill() {
        this.activeSkill.resolve();
        this.activeSkill = null;
        this.nextAction = null;
    }
    public void move(Entity e, int toGo, int dir) {
        if (toGo>0) {
            int targetPos = e.position+dir;
            int indexOffset = e.enemy ? 4:0;

            if ((e.enemy && (targetPos == 3 || targetPos == 8)) ||
                    (!e.enemy && (targetPos == -1 || targetPos == 4))) {
                return;
            }
            if ((e.enemy && (this.enemies.entities.length <= targetPos-4 || this.enemies.entities[targetPos-4] == null)) ||
                    !e.enemy && (this.friends.entities.length <= targetPos || this.friends.entities[targetPos] == null)) {
                return;
            }

            EntityGroup group = e.enemy ? this.enemies : this.friends;
            int oldPosition = e.position;
            Entity switchWith = group.entities[targetPos-indexOffset];

            switchWith.position = oldPosition;
            e.position = targetPos;
            group.entities[targetPos-indexOffset] = e;
            group.entities[oldPosition-indexOffset] = switchWith;

            toGo--;
            move(e, toGo, dir);
        }
    }
    public void chooseTargets(Skill s) {
        this.hud.disableTeamArenaOV();
        this.status = Status.TARGET_CHOICE;
        this.activeSkill = s;

        if (this.activeSkill.getTargetType().equals(TargetType.SINGLE)
                || this.activeSkill.getTargetType().equals(TargetType.SINGLE_ALLY)
                || this.activeSkill.getTargetType().equals(TargetType.SINGLE_ALLY_IN_FRONT)
                || this.activeSkill.getTargetType().equals(TargetType.LINE)) {
            this.setupTargetMatrix();
            this.activePointer = this.targetMatrix[this.targetMatrix.length-1];
            this.matrixPointer = this.targetMatrix.length-1;
        } else {
            Random rand = new Random();
            int from = this.activeEntity.enemy?0:4;
            int until = this.activeEntity.enemy?3:7;
            switch (this.activeSkill.getTargetType()) {
                case ALL:
                    this.pointers = new int[]{0,1,2,3,4,5,6,7};
                    break;
                case SELF:
                    this.pointers = new int[]{this.activeEntity.position};
                    break;
                case ONE_RDM:
                    this.pointers = new int[]{rand.nextInt(from,until+1)};
                    break;
                case TWO_RDM:
                    this.pointers = MyMaths.getIntArrayWithExclusiveRandValues(from, until, 2);
                    break;
                case THREE_RDM:
                    this.pointers = MyMaths.getIntArrayWithExclusiveRandValues(from, until, 3);
                    break;
                case ALL_ALLY:
                    this.pointers = this.activeEntity.enemy?new int[]{4,5,6,7}:new int[]{0,1,2,3};
                    break;
                case ALL_ENEMY:
                    this.pointers = this.activeEntity.enemy?new int[]{0,1,2,3}:new int[]{4,5,6,7};
                    break;
                case FIRST_ENEMY:
                    this.pointers = this.activeEntity.enemy?new int[]{3}:new int[]{4};
                    break;
                case FIRST_TWO_ENEMIES:
                    this.pointers = this.activeEntity.enemy?new int[]{2,3}:new int[]{4,5};
                    break;
            }
            this.performSkill();
        }
    }
    private void setupTargetMatrix() {
        this.targetMatrix = this.activeSkill.setupTargetMatrix();
    }
    private int[] getTargetMatrix(Skill s) {
        return s.setupTargetMatrix();
    }
    public Entity[] getAllLivingEntities() {
        java.util.List<Entity> result = new ArrayList<>();
        for (Entity e : Stream.concat(Arrays.stream(this.friends.entities), Arrays.stream(this.enemies.entities)).toList()) {
            if (e != null && e.getStat(Stat.CURRENT_LIFE) > 0) {
                result.add(e);
            }
        }
        return result.toArray(new Entity[0]);
    }
    public Entity getAtPosition(int position) {
        for (Entity e: getAllLivingEntities()) {
            if (e.position == position) {
                return e;
            }
        }
        return null;
    }
    public Entity[] getEntitiesAt(int[] positions) {
        java.util.List<Entity> resultList = new ArrayList<>();
        for (int position : positions) {
            resultList.add(getAtPosition(position));
        }
        return resultList.stream().filter(Objects::nonNull).toList().toArray(new Entity[0]);
    }
    public Entity[] getTeam(boolean enemy) {
        List<Entity> team = new ArrayList<>();
        for (Entity e : getAllLivingEntities()) {
            if (e.enemy == enemy) {
                team.add(e);
            }
        }
        return team.toArray(new Entity[0]);
    }
    public int getPositionInQueue(Entity e) {
        //TODO
        return 3;
    }
    public boolean canPerformCheck(Skill s) {
        boolean canPerformCheck = true;
        canPerformCheck = canPerformCheck && this.getTargetMatrix(s).length>0;
        canPerformCheck = canPerformCheck && this.skillsCanPerformCheck(s);
        canPerformCheck = canPerformCheck && this.equipmentsCanPerformCheck(s);
        canPerformCheck = canPerformCheck && this.effectsCanPerformCheck(s);
        return canPerformCheck;
    }

    private boolean skillsCanPerformCheck(Skill cast) {
        boolean canCast = true;
        for (Skill s : Arrays.stream(this.getAllLivingEntities())
                .flatMap(entity -> Arrays.stream(entity.skills))
                .filter(Objects::nonNull).toList()) {
            canCast = canCast && s.canPerformCheck(cast);
        }
        return canCast;
    }
    private boolean equipmentsCanPerformCheck(Skill cast) {
        boolean canCast = true;
        for (Equipment e : Arrays.stream(this.getAllLivingEntities())
                .flatMap(entity -> entity.equipments.stream())
                .filter(Objects::nonNull).toList()) {
            canCast = canCast && e.canPerformCheck(cast);
        }
        return canCast;
    }
    private boolean effectsCanPerformCheck(Skill cast) {
        boolean canCast = true;
        for (Effect e : Arrays.stream(this.getAllLivingEntities())
                .flatMap(entity -> entity.currentEffects.stream())
                .filter(Objects::nonNull).toList()) {
            canCast = canCast && e.canPerformCheck(cast);
        }
        return canCast;
    }
    public void changeEffects(Skill skill) {
        this.skillChangeEffects(skill);
        this.effectChangeEffects(skill);
        this.equipmentChangeEffects(skill);
    }
    private void skillChangeEffects(Skill cast) {
        for (Skill s : Arrays.stream(this.getAllLivingEntities())
                .flatMap(entity -> Arrays.stream(entity.skills))
                .filter(Objects::nonNull).toList()) {
            s.changeEffects(cast);
        }
    }
    private void effectChangeEffects(Skill cast) {
        for (Effect e: Arrays.stream(this.getAllLivingEntities())
                .flatMap(entity->entity.currentEffects.stream()).toList()) {
            e.changeEffects(cast);
        }
    }
    private void equipmentChangeEffects(Skill cast) {
        for (Equipment e: Arrays.stream(this.getAllLivingEntities())
                .flatMap(entity->entity.equipments.stream()).toList()) {
            e.changeEffects(cast);
        }
    }
    public void replacementEffects(Skill skill) {
        this.skillReplacementEffects(skill);
        this.effectReplacementEffects(skill);
        this.equipmentReplacementEffects(skill);
    }
    private void skillReplacementEffects(Skill cast) {
        for (Skill s : Arrays.stream(this.getAllLivingEntities())
                .flatMap(entity -> Arrays.stream(entity.skills))
                .filter(Objects::nonNull).toList()) {
            s.replacementEffect(cast);
        }
    }
    private void effectReplacementEffects(Skill cast) {
        for (Effect e: Arrays.stream(this.getAllLivingEntities())
                .flatMap(entity->entity.currentEffects.stream()).toList()) {
            e.replacementEffect(cast);
        }
    }
    private void equipmentReplacementEffects(Skill cast) {
        for (Equipment e: Arrays.stream(this.getAllLivingEntities())
                .flatMap(entity->entity.equipments.stream()).toList()) {
            e.replacementEffect(cast);
        }
    }

    public void critTrigger(Entity target, Skill cast) {
        this.skillCritTrigger(target, cast);
    }
    private void skillCritTrigger(Entity target, Skill cast) {
        for (Skill s : Arrays.stream(this.getAllLivingEntities())
                .flatMap(entity -> Arrays.stream(entity.skills))
                .filter(Objects::nonNull).toList()) {
            s.critTrigger(target, cast);
        }
    }

    public void dmgTrigger(Entity target, Skill cast, int doneDamage) {
        this.skillReceiveDmgTrigger(target, cast, doneDamage);
        this.effectReceieveDmgTrigger(target, cast, doneDamage);

    }
    private void effectReceieveDmgTrigger(Entity target, Skill cast, int doneDamage) {
        for (Effect e: Arrays.stream(this.getAllLivingEntities())
                .flatMap(entity->entity.currentEffects.stream()).toList()) {
            e.dmgTrigger(target, cast, doneDamage);
        }
    }
    private void skillReceiveDmgTrigger(Entity target, Skill cast, int doneDamage) {
        for (Skill s : Arrays.stream(this.getAllLivingEntities())
                .flatMap(entity-> Arrays.stream(entity.skills))
                .filter(Objects::nonNull).toList()) {
            s.dmgTrigger(target, cast, doneDamage);
        }
    }
    //RENDER

    @Override
    public int[] render() {
        background(Color.RED);
        renderBackGround();
        renderPointer();
        renderTeams();
        renderHUD();
        return this.pixels;
    }

    private void renderBackGround() {
        int[] canvas = SpriteLibrary.sprites.get("scene01");
        fillWithGraphicsSize(0,0,640,360,canvas, null);
    }
    private void renderPointer() {
        for (int i = 0; i < pointers.length; i++) {
            Point p;
            if (this.pointers[i] > 3) {
                p = enemyPositions[pointers[i] - 4];
            } else {
                p = friendPositions[pointers[i]];
            }
            int[] pointer = SpriteLibrary.sprites.get("arrow");
            fillWithGraphicsSize(p.x + (64/2 - 6), p.y - (12), 12, 12,pointer,null);
        }
    }
    private int[] getSingleTargets() {
        return new int[]{this.activePointer};
        //handle radius targets by individual resolve of skill
//        int[] targets = new int[this.activeSkill.getTargetRadius()+1];
//        for (int i = 0; i <= this.activeSkill.getTargetRadius();i++) {
//            targets[i] = this.activePointer-i;
//        }
//        return targets;
    }
    private int[] getLineTargets() {
        int casterPosition = this.activeEntity.position;
        int[] targets = new int[Math.abs(casterPosition-this.activePointer)];
        int index = 0;
        for (int j = Math.min(this.activePointer,casterPosition); j <= Math.max(this.activePointer,casterPosition);j++) {
            if (j!=casterPosition) {
                targets[index] = j;
                index++;
            }
        }
        return targets;
    }
    private void renderTeams() {
        renderTeam(friendPositions, friends);
        renderTeam(enemyPositions, enemies);
    }
    private void renderTeam(Point[] points, EntityGroup entities) {

        for (int i = 0; i < points.length; i++) {
            Entity e = entities.entities[i];
            if (e == null) {
                continue;
            }
            int[] pixels = e.render();
            fillWithGraphicsSize(points[i].x, points[i].y, e.anim.width, e.anim.height, pixels, e.equals(this.activeEntity)?Color.WHITE:null);
            int barsY = points[i].y + e.anim.height+5;
            writeBar(points[i].x, points[i].x+33, barsY, barsY + 3, (double)e.getStat(Stat.CURRENT_LIFE) / e.getStat(Stat.MAX_LIFE), Color.GREEN, Color.BLACK);
            int actionBallOffset = 0;
            int healthBarOffset = 35;
            for (int a = 0; a < e.getStat(Stat.CURRENT_ACTION) && a < 3; a++) {
                int from = points[i].x + healthBarOffset + actionBallOffset;
                int until = points[i].x + healthBarOffset + actionBallOffset + 3;
                fillWithGraphics(from, until, barsY, barsY + 3, SpriteLibrary.sprites.get("actionS"),false, Color.VOID, Color.VOID);
                actionBallOffset+=5;
            }
            int overheatOffset = 0;
            String overheatIcon = e.getPrimary().isOverheated()? "overheat_active": "overheat_inactive";
            for (int o = 0; o < e.getPrimary().getCurrentOverheat(); o++) {
                int from = points[i].x + e.anim.width - overheatOffset - 3;
                int until = points[i].x + e.anim.width - overheatOffset;
                fillWithGraphics(from, until, barsY, barsY + 3, SpriteLibrary.sprites.get(overheatIcon),false, Color.VOID, Color.VOID);
                overheatOffset+=1;
            }
            int effectsY = barsY + 10;
            int effectsX = points[i].x;
            int paddingBetween = (e.anim.width - 5 * Property.EFFECT_ICON_SIZE) / 4;

            int paintedEffects = 0;
            Iterator<Effect> iterator = e.currentEffects.iterator();
            while(iterator.hasNext()) {
                Effect effect = iterator.next();
                int[] sprite;
                if (effect.type== Effect.ChangeEffectType.STAT_CHANGE) {
                    sprite = getTextLine(effect.stat.getIconKey(), Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE,
                            1, TextAlignment.CENTER,effect.intensity>0?Color.GREEN:Color.RED,Color.WHITE);
                } else {
                    sprite = SpriteLibrary.sprites.get(effect.getClass().getName());
                }
                for (int n = 0; n < effect.stacks; n++) {
                    fillWithGraphicsSize(effectsX, effectsY, Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE,
                            sprite, false, null,
                            effect.type.equals(Effect.ChangeEffectType.EFFECT)? Color.DARKYELLOW: Color.RED);
                    paintedEffects++;

                    if (paintedEffects%5 == 0) {
                        effectsY += Property.EFFECT_ICON_SIZE + 5;
                        effectsX = points[i].x;
                    } else {
                        effectsX += Property.EFFECT_ICON_SIZE + paddingBetween;
                    }
                }
            }


//            writeBar(points[i].x, points[i].x+192, points[i].y + e.anim.height + 35, points[i].y + e.anim.height + 45, (double)e.currentMana / e.maxMana, Color.BLUE, Color.BLACK);

        }
    }
    private void renderHUD() {
        fillWithGraphicsSize(0,0,640,360,hud.render(),null);
    }
}
