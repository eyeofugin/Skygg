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
import utils.Action;
import utils.ActionQueue;
import utils.MyMaths;
import java.lang.reflect.Method;
import java.util.*;

public class Arena extends GUIElement {

    public Engine engine;

    private final HUD hud;
    public ArenaAIController aiController;

    public Queue queue = new Queue();
    private final ActionQueue actionQueue = new ActionQueue();

    public enum Status {
        HERO_CHOICE,
        TARGET_CHOICE,
        WAIT_ON_ANIMATION,
        WAIT_ON_TURN,
        WAIT_ON_AI,
        WAIT_ON_HUD
    }
    public int round = 0;
    public boolean pvp = true;
    public Status status = Status.HERO_CHOICE;
    public String nextAction = null;
    public int activePointer = 0;
    public int[] pointers = new int[0];
    public int matrixPointer = 0;
    public int[] targetMatrix = new int[0];
    public boolean finished = false;

    public Skill activeSkill;
    public Hero activeHero = null;
    public Hero[] activeTargets = null;
    public final List<HeroTeam> teams = new ArrayList<>();
    public int activeTeamId;
    private List<Hero> heroesChosen = new ArrayList<>();
    public GlobalEffect globalEffect;

    private final int[] friendXPos = new int[]{30, 98, 166, 234};
    private final int[] enemyXPos = new int[]{342, 410, 478, 546};
    public static int numberPositions = 4;
    public static int firstFriendPos = 0;
    public static int lastFriendPos = 3;
    public static int firstEnemyPos = 4;
    public static int lastEnemyPos = 7;
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
        this.teams.add(0,friend);
        this.teams.add(1,enemies);
        for (HeroTeam team : this.teams) {
            for (int i = 0; i < team.heroes.length; i++) {
                Hero hero = team.heroes[i];
                hero.setPosition(i);
                hero.arena = this;
                hero.team = team;
                for (Equipment item : hero.getEquipments()) {
                    item.equipToHero(hero);
                }
                hero.changeStatTo(Stat.CURRENT_LIFE, hero.getStat(Stat.LIFE));
                hero.changeStatTo(Stat.CURRENT_MANA, hero.getStat(Stat.MANA));
                hero.changeStatTo(Stat.CURRENT_FAITH, 0);
            }
        }
        if (!this.pvp) {
            this.aiController.setup();
        }
        this.startOfMatch();
    }
    private void startOfMatch() {
        StartOfMatchPayload startOfMatchPayload = new StartOfMatchPayload();
        Connector.fireTopic(Connector.START_OF_MATCH, startOfMatchPayload);
        this.startTurn();
    }
    private void startTurn() {
        for (Hero hero: this.getAllLivingEntities()) {
            hero.startOfTurn();
            hero.prepareCast();
        }
        this.status = Status.HERO_CHOICE;
        switchTeam(1);
    }
    private void switchTeam(int id) {
        this.pointers = new int[0];
        this.activePointer = id == 1 ? lastFriendPos : firstEnemyPos;
        this.activeTeamId = id;
    }

    @Override
    public void update(int frame) {
        switch (status) {
            case HERO_CHOICE -> updateChooseHero();
            case TARGET_CHOICE -> updateChooseTarget();
            case WAIT_ON_ANIMATION -> updateWaitOnAnimation();
            case WAIT_ON_TURN -> resumeTurn();
        }
        this.hud.update(frame);
        updateAnimations(frame);
    }
    private void updateAnimations(int frame) {
        this.teams.forEach(t->t.updateAnimations(frame));
    }
    private void updateWaitOnAnimation() {
        int amntRunning = 0;

        for (Hero e : this.teams.stream().flatMap(ht->ht.getHeroesAsList().stream()).toList()) {
            if (e!= null && e.anim.waitFor) {
                amntRunning++;
            }
        }
        if (amntRunning == 0) {
            if (this.nextAction != null) {
                try {
//                    Logger.logLn(this.nextAction);
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

    private void updateChooseHero() {
        if (engine.keyB._leftPressed) {
            this.activePointer = this.getNextTeamPointer(-1);
        }
        if (engine.keyB._rightPressed) {
            this.activePointer = this.getNextTeamPointer(1);
        }
        setPointerArray();
        if (engine.keyB._backPressed) {
            //if activehero is already null, remove last action
            this.activeHero = null;
            this.pointers = new int[0];
            this.targetMatrix = new int[0];
        }
        if (engine.keyB._enterPressed) {
            Hero hero = getAtPosition(this.activePointer);
            if (!this.heroesChosen.contains(hero)) {
                this.activeHero = hero;
                this.pointers = new int[0];
                this.targetMatrix = new int[0];
                this.status = Status.WAIT_ON_HUD;
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
            if (allChosen()) {
                this.activeTeamId++;
                if (this.activeTeamId == 1) {
                    switchTeam(2);
                    this.status = Status.HERO_CHOICE;
                } else {
                    this.status = Status.WAIT_ON_TURN;
                }
            } else {
                addAction();
                this.heroesChosen.add(this.activeHero);
            }
            this.pointers = new int[0];
            this.targetMatrix = new int[0];
        }
    }

    private boolean allChosen() {
        List<Hero> heroList = this.activeTeamId == 1? this.teams.get(0).getHeroesAsList() : this.teams.get(1).getHeroesAsList();
        return new HashSet<>(this.heroesChosen).containsAll(heroList);
    }

    private void addAction() {
        Action action = new Action();
        action.skill = this.activeSkill;
        action.targetPositions = this.pointers;
        action.caster = this.activeHero;
        this.actionQueue.addAction(action);
    }
    private int getNextTeamPointer(int dir) {
        int pointer = this.activePointer + dir;
        Hero hero = this.getAtPosition(pointer);
        if (hero == null || hero.team.teamNumber != this.activeTeamId) {
            return dir;
        }
        return pointer;
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
        } else if (this.status == Status.HERO_CHOICE) {
            this.pointers = getSingleTargets();
        }
    }

    private void removeTheDead() {
        this.queue.removeAll(this.teams.stream().flatMap(ht->ht.removeTheDead().stream()).toList());
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
        if (this.actionQueue.hasAction()) {
            Action action = this.actionQueue.getNextAction();
            if (action != null) {
                this.activeHero = action.caster;
                this.activeSkill = action.skill;
                if (this.activeHero.canPerform(this.activeSkill)) {
                    this.activeSkill.setTargets(getEntitiesAt(action.targetPositions));
                    this.performSkill();
                } else {
                    System.out.println("Failed. " + this.activeHero.getName() + ": " + this.activeSkill.getName());
                }
                this.actionQueue.remove(action);
            }
        } else {
            endOfRound();
        }
    }
    private boolean checkEndOfMatch() {
        if (this.teams.get(0).deadHeroes.size() == numberPositions || this.teams.get(1).deadHeroes.size() == numberPositions) {
            this.finished = true;
            return true;
        }
        return false;
    }
    private void endOfRound() {
        for (Hero hero: getAllLivingEntities()) {
            hero.endOfRound();
        }
        this.heroesChosen = new ArrayList<>();
        EndOfRoundPayload endOfTurnPayload = new EndOfRoundPayload()
                .setArena(this);
        Connector.fireTopic(Connector.END_OF_ROUND, endOfTurnPayload);

        this.startTurn();
    }
    private void aiTurn() {
        Logger.logLn("aiTurn()");
        this.status = Status.WAIT_ON_AI;
//        this.activeHero.aiTurn();
        this.aiController.turn();
    }

    private void performSkill() {
        this.activeSkill.perform();
        this.pointers = new int[0];
        this.nextAction = "resolveSkill";
        this.status = Status.WAIT_ON_ANIMATION;
    }

    public void resolveSkill() {
        this.activeSkill.resolve();
        this.activeHero.endOfTurn();
        this.actionQueue.updateActions();
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

            if ((e.isTeam2() && (targetPos < firstEnemyPos || targetPos > lastEnemyPos)) ||
                    (!e.isTeam2() && (targetPos <firstFriendPos || targetPos > lastFriendPos))) {
                return;
            }
            if ((e.isTeam2() && (this.teams.get(1).heroes.length <= targetPos-numberPositions || this.teams.get(1).heroes[targetPos-numberPositions] == null)) ||
                    !e.isTeam2() && (this.teams.get(0).heroes.length <= targetPos || this.teams.get(0).heroes[targetPos] == null)) {
                return;
            }

            HeroTeam group = e.isTeam2() ? this.teams.get(1) : this.teams.get(0);
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
            int until = this.activeHero.isTeam2()?firstEnemyPos:lastEnemyPos;
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
        return this.teams.stream().flatMap(ht->ht.getHeroesAsList().stream()).toList();
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
        for (Hero hero: this.teams.get(0).heroes) {
            if (hero != null) {
                int x = friendXPos[hero.getPosition()];
                fillWithGraphicsSize(x, heroYPos, hero.getWidth(), hero.getHeight(), hero.render(), this.activeHero.equals(hero));
            }
        }
        for (Hero hero: this.teams.get(1).heroes) {
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
