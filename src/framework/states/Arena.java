package framework.states;

import framework.Engine;
import framework.Logger;
import framework.Property;
import framework.connector.Connector;
import framework.connector.payloads.CanPerformPayload;
import framework.connector.payloads.PrepareUpdatePayload;
import framework.connector.payloads.UpdatePayload;
import framework.graphics.GUIElement;

import framework.graphics.text.Color;
import framework.graphics.text.TextAlignment;
import framework.resources.SpriteLibrary;
import framework.graphics.containers.HUD;
import game.controllers.ArenaAIController;
import game.entities.Hero;
import game.entities.HeroGroup;
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

    public Queue<Hero> order = new LinkedList<>();

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
    public Hero activeHero = null;
    public Hero[] activeTargets = null;
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
        this.activeHero = this.order.peek();
        if (this.activeHero == null) {
            System.out.println("Could not retrieve first in queue");
            return;
        }
        this.hud.setActiveHero(this.activeHero.enemy? this.friends.heroes[3]: this.activeHero);
        this.aiController.setup();
        this.updateEntities();
    }
    private void initialOrder() {
        List<Hero> backwards = new ArrayList<>(Stream.concat(friends.getHeroesAsList().stream(), enemies.getHeroesAsList().stream())
                .filter(Objects::nonNull)
                .toList());
        Collections.shuffle(backwards);
        backwards = backwards.stream()
                .sorted((Hero e1, Hero e2)->Integer.compare(e2.getStat(Stat.SPEED, null), e1.getStat(Stat.SPEED, null)))
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
        friends.updateAnimations(frame);
        enemies.updateAnimations(frame);
    }
    private void updateWaitOnAnimation() {
        int amntRunning = 0;
        for (Hero e : Stream.concat(friends.getHeroesAsList().stream(), enemies.getHeroesAsList().stream()).toList()) {
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
        this.order.removeAll(friends.removeTheDead());
        this.order.removeAll(this.enemies.removeTheDead());
    }
    private void updateEntities() {
        PrepareUpdatePayload prepareUpdatePayload = new PrepareUpdatePayload();
        Connector.fireTopic(Connector.PREPARE_UPDATE, prepareUpdatePayload);

        UpdatePayload updatePayload = new UpdatePayload();
        Connector.fireTopic(Connector.UPDATE, updatePayload);
    }
    public void devDoTurn(int skillIndex, Hero[] targets) {
        this.activeSkill = this.activeHero.getSkills()[skillIndex]._cast;
        this.activeSkill.setTargets(java.util.List.of(targets));
        Logger.logLn("Load Skill " + this.activeSkill.name);
        if (this.performSuccessCheck(this.activeSkill)) {
            this.activeSkill.perform();
            this.activeSkill.resolve();
        }
    }
    private void resumeTurn() {
        Logger.logLn("resumeTurn()");
        this.removeTheDead();
        this.updateEntities();
        this.activeHero.prepareCast();
        if (this.activeHero.getStats().get(Stat.CURRENT_ACTION) > 0) {
            if (this.activeHero.enemy) {
                aiTurn();
            } else {
                this.status = Status.WAIT_ON_HUD;
                this.hud.activateTeamOverview();
                this.hud.setActiveHero(this.activeHero);
            }
        } else {
            endOfTurn();
        }
    }
    private void endOfTurn() {
        Logger.logLn("endOfTurn()");
        this.activeHero.endOfTurn();
        Hero lastActiveUnit = this.order.remove();
        this.order.add(lastActiveUnit);
//        for(Hero e: this.getAllLivingEntities()) {
//            Logger.logLn(e.toString());
//        }
        startTurn();
    }
    private void startTurn() {
        Logger.logLn("startTurn()");
        this.activeHero = this.order.peek();
        if (this.activeHero != null) {
            Logger.logLn("-----------------------------");
            Logger.logLn("Start turn for " + this.activeHero.getName() + " " + this.activeHero.position);
            this.activeHero.startOfTurn();
            resumeTurn();
        }
    }
    private void aiTurn() {
        Logger.logLn("aiTurn()");
        this.status = Status.WAIT_ON_AI;
        this.aiController.turn();
//        this.activeHero.changeStatTo(Stat.CURRENT_ACTION,0);

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
        CanPerformPayload canPerformPayload = new CanPerformPayload()
                .setSkill(skill);
        Connector.fireTopic(Connector.CAN_PERFORM, canPerformPayload);
        return canPerformPayload.success;
    }
    public void resolveSkill() {
        this.activeSkill.resolve();
        this.activeSkill = null;
        this.nextAction = null;
    }
    public void move(Hero e, int toGo, int dir) {
        if (toGo>0) {
            int targetPos = e.position+dir;
            int indexOffset = e.enemy ? 4:0;

            if ((e.enemy && (targetPos == 3 || targetPos == 8)) ||
                    (!e.enemy && (targetPos == -1 || targetPos == 4))) {
                return;
            }
            if ((e.enemy && (this.enemies.heroes.length <= targetPos-4 || this.enemies.heroes[targetPos-4] == null)) ||
                    !e.enemy && (this.friends.heroes.length <= targetPos || this.friends.heroes[targetPos] == null)) {
                return;
            }

            HeroTeam group = e.enemy ? this.enemies : this.friends;
            int oldPosition = e.position;
            Hero switchWith = group.heroes[targetPos-indexOffset];

            switchWith.position = oldPosition;
            e.position = targetPos;
            group.heroes[targetPos-indexOffset] = e;
            group.heroes[oldPosition-indexOffset] = switchWith;

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
            int from = this.activeHero.enemy?0:4;
            int until = this.activeHero.enemy?3:7;
            switch (this.activeSkill.getTargetType()) {
                case ALL:
                    this.pointers = new int[]{0,1,2,3,4,5,6,7};
                    break;
                case SELF:
                    this.pointers = new int[]{this.activeHero.position};
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
                    this.pointers = this.activeHero.enemy?new int[]{4,5,6,7}:new int[]{0,1,2,3};
                    break;
                case ALL_ENEMY:
                    this.pointers = this.activeHero.enemy?new int[]{0,1,2,3}:new int[]{4,5,6,7};
                    break;
                case FIRST_ENEMY:
                    this.pointers = this.activeHero.enemy?new int[]{3}:new int[]{4};
                    break;
                case FIRST_TWO_ENEMIES:
                    this.pointers = this.activeHero.enemy?new int[]{2,3}:new int[]{4,5};
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

    public List<Hero> getAllLivingEntities() {
        return Stream.concat(this.friends.getHeroesAsList().stream(), this.enemies.getHeroesAsList().stream()).toList();
    }

    public Hero getAtPosition(int position) {
        for (Hero e: getAllLivingEntities()) {
            if (e.position == position) {
                return e;
            }
        }
        return null;
    }
    public Hero[] getEntitiesAt(int[] positions) {
        List<Hero> resultList = new ArrayList<>();
        for (int position : positions) {
            resultList.add(getAtPosition(position));
        }
        return resultList.stream().filter(Objects::nonNull).toList().toArray(new Hero[0]);
    }
    public Hero[] getTeam(boolean enemy) {
        List<Hero> team = new ArrayList<>();
        for (Hero e : getAllLivingEntities()) {
            if (e.enemy == enemy) {
                team.add(e);
            }
        }
        return team.toArray(new Hero[0]);
    }
    public int getPositionInQueue(Hero e) {
        //TODO
        return 3;
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
        int casterPosition = this.activeHero.position;
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
        friends.render();
        enemies.render();
    }
    private void renderTeam(Point[] points, HeroTeam entities) {

        for (int i = 0; i < points.length; i++) {
            Hero e = entities.heroes[i];
            if (e == null) {
                continue;
            }
            int[] pixels = e.render();
            fillWithGraphicsSize(points[i].x, points[i].y, e.anim.width, e.anim.height, pixels, e.equals(this.activeHero)?Color.WHITE:null);
            int barsY = points[i].y + e.anim.height+5;
            writeBar(points[i].x, points[i].x+33, barsY, barsY + 3, (double)e.getStats().get(Stat.CURRENT_LIFE) / e.getStat(Stat.MAX_LIFE), Color.GREEN, Color.BLACK);
            int actionBallOffset = 0;
            int healthBarOffset = 35;
            for (int a = 0; a < e.getStats().get(Stat.CURRENT_ACTION) && a < 3; a++) {
                int from = points[i].x + healthBarOffset + actionBallOffset;
                int until = points[i].x + healthBarOffset + actionBallOffset + 3;
                fillWithGraphics(from, until, barsY, barsY + 3, SpriteLibrary.sprites.get("actionS"),false, Color.VOID, Color.VOID);
                actionBallOffset+=5;
            }
//            int overheatOffset = 0;
//            String overheatIcon = e.getPrimary().isOverheated()? "overheat_active": "overheat_inactive";
//            for (int o = 0; o < e.getPrimary().getCurrentOverheat(); o++) {
//                int from = points[i].x + e.anim.width - overheatOffset - 3;
//                int until = points[i].x + e.anim.width - overheatOffset;
//                fillWithGraphics(from, until, barsY, barsY + 3, SpriteLibrary.sprites.get(overheatIcon),false, Color.VOID, Color.VOID);
//                overheatOffset+=1;
//            }
            int effectsY = barsY + 10;
            int effectsX = points[i].x;
            int paddingBetween = (e.anim.width - 5 * Property.EFFECT_ICON_SIZE) / 4;

            int paintedEffects = 0;
            for (Effect effect : e.getEffects()) {
                int[] sprite;
                if (effect.type == Effect.ChangeEffectType.STAT_CHANGE) {
                    sprite = getTextLine(effect.stat.getIconKey(), Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE,
                            1, TextAlignment.CENTER, effect.intensity > 0 ? Color.GREEN : Color.RED, Color.WHITE);
                } else {
                    sprite = SpriteLibrary.sprites.get(effect.getClass().getName());
                }
                for (int n = 0; n < effect.stacks; n++) {
                    fillWithGraphicsSize(effectsX, effectsY, Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE,
                            sprite, false, null,
                            effect.type.equals(Effect.ChangeEffectType.EFFECT) ? Color.DARKYELLOW : Color.RED);
                    paintedEffects++;

                    if (paintedEffects % 5 == 0) {
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
