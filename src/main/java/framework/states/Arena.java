package framework.states;

import framework.Engine;
import framework.Logger;
import framework.Property;
import framework.connector.Connector;
import framework.connector.payloads.EndOfRoundPayload;
import framework.connector.payloads.PrepareUpdatePayload;
import framework.connector.payloads.StartOfMatchPayload;
import framework.connector.payloads.UpdatePayload;
import framework.graphics.GUIElement;
import framework.graphics.containers.Queue;
import framework.graphics.text.Color;
import framework.resources.SpriteLibrary;
import framework.graphics.containers.HUD;

import game.controllers.ArenaAIController;
import game.entities.Hero;
import game.entities.HeroTeam;
import game.skills.GlobalEffect;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.TargetType;

import game.skills.changeeffects.effects.Scoped;
import game.skills.changeeffects.globals.Heat;
import game.skills.changeeffects.statusinflictions.Rooted;
import utils.MyMaths;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;

public class Arena extends GUIElement {

    public Engine engine;
    private boolean started = false;

    private final HUD hud;
    public ArenaAIController aiController;

    public Queue queue = new Queue();

    public enum Status {
        TARGET_CHOICE,
        WAIT_ON_ANIMATION,
        WAIT_ON_AI,
        WAIT_ON_HUD
    }
    public boolean pvp = true;
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
    public GlobalEffect globalEffect;

    private final int[] friendXPos = new int[]{30, 98, 166, 234};
    private final int[] enemyXPos = new int[]{342, 410, 478, 546};
    private final int heroYPos = 80;

    public Arena(Engine e, boolean pvp) {
        super(Engine.X, Engine.Y);
        this.engine = e;
        this.hud = new HUD(e);
        this.hud.setArena(this);
        this.pvp = pvp;
        if (!this.pvp) {
            this.aiController = new ArenaAIController(this);
        }
    }

    public void setTeams(HeroTeam friend, HeroTeam enemies) {
        this.friends = friend;
        this.enemies = enemies;
        initialOrder();
        this.activeHero = this.queue.peek();
        if (this.activeHero == null) {
            System.out.println("Could not retrieve first in queue");
            return;
        }
        this.hud.setActiveHero(this.activeHero);
        this.aiController.setup();
        this.updateEntities();
        this.startOfMatch();
    }
    private void initialOrder() {
        List<Hero> backwards = new ArrayList<>(Stream.concat(friends.getHeroesAsList().stream(), enemies.getHeroesAsList().stream())
                .filter(Objects::nonNull)
                .toList());
        Collections.shuffle(backwards);
        backwards = backwards.stream()
                .sorted((Hero e1, Hero e2)->Integer.compare(e2.getStat(Stat.SPEED), e1.getStat(Stat.SPEED)))
                .toList();
        this.queue.addAll(backwards);
    }

    @Override
    public void update(int frame) {
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
                    System.out.println(Arrays.toString(e.getStackTrace()));
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
            this.hud.activateTeamArenaOv();
            this.activeSkill.setToInitial();
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
                    || this.activeSkill.getTargetType().equals(TargetType.SINGLE_ALLY_IN_FRONT)
                    || this.activeSkill.getTargetType().equals(TargetType.SINGLE_ALLY_BEHIND)) {
                this.pointers = getSingleTargets();
            } else if (this.activeSkill.getTargetType().equals(TargetType.LINE)) {
                this.pointers = getLineTargets();
            } else if (this.activeSkill.getTargetType().equals(TargetType.ENEMY_LINE)) {
                this.pointers = getEnemyLineTargets();
            }
        }
    }

    private void removeTheDead() {
        this.queue.removeAll(friends.removeTheDead());
        this.queue.removeAll(this.enemies.removeTheDead());
    }
    private void updateEntities() {
        PrepareUpdatePayload prepareUpdatePayload = new PrepareUpdatePayload();
        Connector.fireTopic(Connector.PREPARE_UPDATE, prepareUpdatePayload);

        UpdatePayload updatePayload = new UpdatePayload();
        Connector.fireTopic(Connector.UPDATE, updatePayload);
    }
    private void resumeTurn() {
        Logger.logLn("resumeTurn()");
        this.removeTheDead();
        this.updateEntities();
        this.activeHero.prepareCast();
        if (this.activeHero.getStat(Stat.CURRENT_ACTION) > 0) {
            if (!this.pvp && this.activeHero.isTeam2()) {
                aiTurn();
            } else {
                this.status = Status.WAIT_ON_HUD;
                this.hud.activateTeamArenaOv();
                this.hud.setActiveHero(this.activeHero);
            }
        } else {
            endOfTurn();
        }
    }
    private void endOfTurn() {
        Logger.logLn("endOfTurn()");
        this.activeHero.endOfTurn();
        this.queue.didTurn(this.activeHero);

        checkEndOfRound();
    }
    private void checkEndOfRound() {
        if (this.queue.hasHeroUp()) {
            startTurn();
        } else {
            endOfRound();
        }
    }
    private void endOfRound() {
        for (Hero hero: getAllLivingEntities()) {
            hero.endOfRound();
        }

        EndOfRoundPayload endOfTurnPayload = new EndOfRoundPayload();
        Connector.fireTopic(Connector.END_OF_ROUND, endOfTurnPayload);

        this.queue.restartTurnQueue();

        this.startTurn();
    }
    private void startOfMatch() {
        StartOfMatchPayload startOfMatchPayload = new StartOfMatchPayload();
        Connector.fireTopic(Connector.START_OF_MATCH, startOfMatchPayload);
        this.startTurn();
    }
    private void startTurn() {
        Logger.logLn("startTurn()");
        this.activeHero = this.queue.peek();
        if (this.activeHero != null) {
            Logger.logLn("-----------------------------");
            Logger.logLn("Start turn for " + this.activeHero.getName() + " " + this.activeHero.getPosition());
            this.activeHero.startOfTurn();
            resumeTurn();
        }
    }
    private void aiTurn() {
        Logger.logLn("aiTurn()");
        this.status = Status.WAIT_ON_AI;
//        this.activeHero.aiTurn();
        this.aiController.turn();
    }

    private void performSkill() {
         if (this.activeSkill.getTargets() == null || this.activeSkill.getTargets().isEmpty()
                || this.activeSkill.getTargetType().equals(TargetType.ARENA)) {
            this.activeTargets = getEntitiesAt(this.pointers);
            this.activeSkill.setTargets(java.util.List.of(this.activeTargets));
        }
        Logger.logLn("Player will perform " + this.activeSkill.getName() + " at position " + this.activePointer);
        //allEntities.onPerform(skill, targets);
        this.activeSkill.perform();
        this.pointers = new int[0];
        this.nextAction = "resolveSkill";
        this.status = Status.WAIT_ON_ANIMATION;
    }

    public void resolveSkill() {
        this.activeSkill.resolve();
        this.activeSkill = null;
        this.nextAction = null;
    }

    public void stun(Hero target) {
        this.queue.sendToBack(target);
    }
    public void moveTo(Hero e, int targetPos) {
        int toGo = Math.abs(e.getPosition()-targetPos);
        int dir = e.getPosition() < targetPos ? 1:-1;
        move(e,toGo,dir);
    }
    public void move(Hero e, int toGo, int dir) {
        if (e.hasPermanentEffect(Rooted.class) > 0 ||
            e.hasPermanentEffect(Scoped.class) > 0) {
            return;
        }
        if (toGo>0) {
            int targetPos = e.getPosition()+dir;
            int indexOffset = e.isTeam2() ? 4:0;

            if ((e.isTeam2() && (targetPos == 3 || targetPos == 8)) ||
                    (!e.isTeam2() && (targetPos == -1 || targetPos == 4))) {
                return;
            }
            if ((e.isTeam2() && (this.enemies.heroes.length <= targetPos-4 || this.enemies.heroes[targetPos-4] == null)) ||
                    !e.isTeam2() && (this.friends.heroes.length <= targetPos || this.friends.heroes[targetPos] == null)) {
                return;
            }

            HeroTeam group = e.isTeam2() ? this.enemies : this.friends;
            int oldPosition = e.getPosition();
            Hero switchWith = group.heroes[targetPos-indexOffset];

            if (switchWith.hasPermanentEffect(Rooted.class) > 0 ||
                switchWith.hasPermanentEffect(Scoped.class) > 0) {
                return;
            }

            switchWith.setPosition(oldPosition);
            e.setPosition(targetPos);
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
                || this.activeSkill.getTargetType().equals(TargetType.SINGLE_ALLY_BEHIND)
                || this.activeSkill.getTargetType().equals(TargetType.LINE)
                || this.activeSkill.getTargetType().equals(TargetType.ENEMY_LINE)) {
            this.setupTargetMatrix();
            int startIndex = this.activeHero.isTeam2() ? 0 : this.targetMatrix.length-1;
            this.activePointer = this.targetMatrix[startIndex];
            this.matrixPointer = startIndex;
        } else {
            Random rand = new Random();
            int from = this.activeHero.isTeam2()?0:4;
            int until = this.activeHero.isTeam2()?3:7;
            switch (this.activeSkill.getTargetType()) {
                case ALL:
                    this.pointers = new int[]{0,1,2,3,4,5,6,7};
                    break;
                case SELF:
                    this.pointers = new int[]{this.activeHero.getPosition()};
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
                    this.pointers = this.activeHero.isTeam2()?new int[]{4,5,6,7}:new int[]{0,1,2,3};
                    break;
                case ALL_ENEMY:
                    this.pointers = this.activeHero.isTeam2()?new int[]{0,1,2,3}:new int[]{4,5,6,7};
                    break;
                case FIRST_ENEMY:
                    this.pointers = this.activeHero.isTeam2()?new int[]{3}:new int[]{4};
                    break;
                case FIRST_TWO_ENEMIES:
                    this.pointers = this.activeHero.isTeam2()?new int[]{2,3}:new int[]{4,5};
                    break;
                case ARENA:
                    this.pointers = new int[]{};
                    break;
            }
            this.performSkill();
        }
    }
    private void setupTargetMatrix() {
        this.targetMatrix = this.activeSkill.setupTargetMatrix();
    }

    public List<Hero> getAllLivingEntities() {
        return Stream.concat(this.friends.getHeroesAsList().stream(), this.enemies.getHeroesAsList().stream()).toList();
    }

    public Hero getAtPosition(int position) {
        for (Hero e: getAllLivingEntities()) {
            if (e != null && e.getPosition() == position) {
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
    public int getPositionInQueue(Hero e) {
        //TODO
        return 3;
    }

    public void setGlobalEffect(GlobalEffect globalEffect) {
        this.globalEffect = globalEffect;
    }
    //RENDER

    @Override
    public int[] render() {
        background(Color.RED);
        renderBackGround();
        renderPointer();
        renderTeams();
        renderHUD();
        renderArenaEffect();
        renderQueue();
        return this.pixels;
    }

    private void renderBackGround() {
        int[] canvas = SpriteLibrary.getSprite("scene01");
        fillWithGraphicsSize(0,0,640,360,canvas, null);
    }

    private int[] getSingleTargets() {
        return new int[]{this.activePointer};
    }
    private int[] getLineTargets() {
        int casterPosition = this.activeHero.getPosition();
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
    private int[] getEnemyLineTargets() {
        int firstEnemyPosition = this.activeHero.isTeam2()?3:4;
        int[] targets = new int[Math.abs(firstEnemyPosition-this.activePointer) + 1];
        int index = 0;
        for (int j = Math.min(this.activePointer,firstEnemyPosition); j <= Math.max(this.activePointer,firstEnemyPosition);j++) {
            targets[index] = j;
            index++;
        }
        return targets;
    }
    private void renderPointer() {
        for (int j : pointers) {
            int x;
            if (j > 3) {
                x = enemyXPos[j - 4];
            } else {
                x = friendXPos[j];
            }
            int[] pointer = SpriteLibrary.getSprite("arrow_down");
            fillWithGraphicsSize(x + (64 / 2 - 16), heroYPos - (32), 32, 32, pointer, null);
        }
    }
    private void renderTeams() {
        for (Hero hero: friends.heroes) {
            if (hero != null) {
                int x = friendXPos[hero.getPosition()];
                fillWithGraphicsSize(x, heroYPos, hero.getWidth(), hero.getHeight(), hero.render(), this.activeHero.equals(hero));
            }
        }
        for (Hero hero: enemies.heroes) {
            if (hero != null) {
                int x = enemyXPos[hero.getPosition() - 4];
                fillWithGraphicsSize(x, heroYPos, hero.getWidth(), hero.getHeight(), hero.render(), this.activeHero.equals(hero));
            }
        }
    }
    private void renderHUD() {
        fillWithGraphicsSize(0,0,640,360,hud.render(),null);
    }
    private void renderArenaEffect() {
        if (this.globalEffect != null) {
            int[] effectPixels = SpriteLibrary.getSprite(Heat.class.getName());
            fillWithGraphicsSize(this.width-40, 8, Property.GLOBAL_EFFECT_WIDTH, Property.GLOBAL_EFFECT_HEIGHT, effectPixels, Color.WHITE);
        }
    }
    private void renderQueue() {
        if (this.queue != null) {
            fillWithGraphicsSize(Property.QUEUE_X, Property.QUEUE_Y, Property.QUEUE_WIDTH, Property.QUEUE_HEIGHT, this.queue.render(),false);
        }
    }
}
