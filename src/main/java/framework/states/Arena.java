package framework.states;

import framework.Engine;
import framework.Logger;
import framework.Property;
import framework.connector.Connector;
import framework.connector.payloads.EndOfRoundPayload;
import framework.connector.payloads.GlobalEffectChangePayload;
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
import game.objects.Equipment;
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

    private final HUD hud;
    public ArenaAIController aiController;

    public Queue queue = new Queue();


    public enum Status {
        TARGET_CHOICE,
        WAIT_ON_ANIMATION,
        WAIT_ON_AI,
        WAIT_ON_HUD
    }
    public int round = 0;
    public boolean pvp = true;
    public Status status = Status.WAIT_ON_HUD;
    public String nextAction = null;
    public int activePointer = 0;
    public int[] pointers = new int[0];
    public int matrixPointer = 0;
    public int[] targetMatrix = new int[0];
    public boolean finished = false;

    public Skill activeSkill;
    public Hero activeHero = null;
    public Hero[] activeTargets = null;
    public HeroTeam friends;
    public HeroTeam enemies;
    public GlobalEffect globalEffect;

    private final int[] friendXPos = new int[]{30, 98, 166, 234};
    private final int[] enemyXPos = new int[]{342, 410, 478, 546};
    public static int numberPositions = 4;
    public static int firstFriendPos = 0;
    public static int lastFriendPos = 3;
    public static int firstEnemyPos = 4;
    public static int lastEnemeyPos = 7;
    public static int[] friendPos = new int[]{0,1,2,3};
    public static int[] enemyPos = new int[]{4,5,6,7};
    public static int[] allPos = new int[]{0,1,2,3,4,5,6,7};
    private final int heroYPos = 80;

    public Arena(Engine e, boolean pvp) {
        super(Engine.X, Engine.Y);
        this.engine = e;
        this.round = this.engine.memory.pvpRound;
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
        for (int i = 0; i < this.friends.heroes.length; i++) {
            Hero hero = this.friends.heroes[i];
            hero.setPosition(i);
            hero.arena = this;
            hero.team = this.friends;
            for (Equipment item : hero.getEquipments()) {
                item.equipToHero(hero);
            }
            hero.changeStatTo(Stat.CURRENT_LIFE, hero.getStat(Stat.LIFE));
            hero.changeStatTo(Stat.CURRENT_MANA, hero.getStat(Stat.MANA));
            hero.changeStatTo(Stat.CURRENT_FAITH, 0);
        }
        for (int i = 0; i < this.enemies.heroes.length; i++) {
            Hero hero = this.enemies.heroes[i];
            hero.setPosition(Math.abs(i-2) + numberPositions);
            hero.arena = this;
            hero.team = this.enemies;
            for (Equipment item : hero.getEquipments()) {
                item.equipToHero(hero);
            }
            hero.changeStatTo(Stat.CURRENT_LIFE, hero.getStat(Stat.LIFE));
            hero.changeStatTo(Stat.CURRENT_MANA, hero.getStat(Stat.MANA));
            hero.changeStatTo(Stat.CURRENT_FAITH, 0);
        }
        initialOrder();
        this.activeHero = this.queue.peek();
        if (this.activeHero == null) {
            System.out.println("Could not retrieve first in queue");
            return;
        }
        this.hud.setActiveHero(this.activeHero);
        if (!this.pvp) {
            this.aiController.setup();
        }
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
                    || this.activeSkill.getTargetType().equals(TargetType.SINGLE_OTHER)) {
                this.pointers = getSingleTargets();
            } else if (this.activeSkill.getTargetType().equals(TargetType.ALL_TARGETS)) {
                this.pointers = getAllTargets();
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
        if (this.checkEndOfMatch()) {
            return;
        }

        this.updateEntities();
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
    private boolean checkEndOfMatch() {
        if (this.friends.deadHeroes.size() == numberPositions || this.enemies.deadHeroes.size() == numberPositions) {
            this.finished = true;
            return true;
        }
        return false;
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

        EndOfRoundPayload endOfTurnPayload = new EndOfRoundPayload()
                .setArena(this);
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
            this.activeHero.prepareCast();
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
        this.queue.didTurn(target);
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
            int indexOffset = e.isTeam2() ? numberPositions:0;

            if ((e.isTeam2() && (targetPos < firstEnemyPos || targetPos > lastEnemeyPos)) ||
                    (!e.isTeam2() && (targetPos <firstFriendPos || targetPos > lastFriendPos))) {
                return;
            }
            if ((e.isTeam2() && (this.enemies.heroes.length <= targetPos-numberPositions || this.enemies.heroes[targetPos-numberPositions] == null)) ||
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
                || this.activeSkill.getTargetType().equals(TargetType.SINGLE_OTHER)
                || this.activeSkill.getTargetType().equals(TargetType.ALL_TARGETS)) {
            this.setupTargetMatrix();
            int startIndex = this.activeHero.isTeam2() ? 0 : this.targetMatrix.length-1;
            this.activePointer = this.targetMatrix[startIndex];
            this.matrixPointer = startIndex;
        } else {
            Random rand = new Random();
            int from = this.activeHero.isTeam2()?firstFriendPos:lastFriendPos;
            int until = this.activeHero.isTeam2()?firstEnemyPos:lastEnemeyPos;
            this.pointers = new int[]{};
            switch (this.activeSkill.getTargetType()) {
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
                case ALL:
                    this.pointers = allPos;
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
        GlobalEffect oldEffect = this.globalEffect;
        this.globalEffect = globalEffect;
        GlobalEffectChangePayload globalEffectChangePayload = new GlobalEffectChangePayload()
                .setEffect(this.globalEffect)
                .setOldEffect(oldEffect);
        Connector.fireTopic(Connector.GLOBAL_EFFECT_CHANGE, globalEffectChangePayload);
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
    private int[] getAllTargets() {
        return this.targetMatrix;
    }
    private void renderPointer() {
        for (int j : pointers) {
            int x;
            if (j > lastFriendPos) {
                x = enemyXPos[j - firstEnemyPos];
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
                int x = enemyXPos[hero.getPosition() - firstEnemyPos];
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
