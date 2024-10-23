package game.entities;

import framework.Logger;
import framework.Property;
import framework.connector.Connector;
import framework.connector.payloads.ActionInflictionPayload;
import framework.connector.payloads.CanPerformPayload;
import framework.connector.payloads.CastChangePayload;
import framework.connector.payloads.DmgChangesPayload;
import framework.connector.payloads.DmgTriggerPayload;
import framework.connector.payloads.EffectAddedPayload;
import framework.connector.payloads.EffectDmgChangesPayload;
import framework.connector.payloads.EffectFailurePayload;
import framework.connector.payloads.EndOfTurnPayload;
import framework.connector.payloads.HealChangesPayload;
import framework.connector.payloads.StartOfTurnPayload;
import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import framework.graphics.text.TextAlignment;
import framework.resources.SpriteLibrary;
import framework.resources.SpriteUtils;
import framework.states.Arena;
import game.objects.Equipment;
import game.skills.DamageType;
import game.skills.Effect;
import game.skills.Skill;
import game.skills.Stat;
import game.skills.changeeffects.statusinflictions.Injured;
import utils.MyMaths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class Hero extends GUIElement {

    public Arena arena;
    public Animator anim;
    public HeroTeam team;
    protected Map<String, int[][]> sprites = new HashMap<>();
    public String basePath = "";

    protected int id;
    protected String name;
    protected Skill[] skills;
    protected List<Equipment> equipments = new ArrayList<>();
    protected String portraitName;

    protected Map<Stat, Integer> stats = new HashMap<>();
    protected List<Effect> effects = new ArrayList<>();
    protected Stat secondaryResource;

    private int position;
    private boolean enemy;

    protected Hero(String name) {
        this.width = 64;
        this.height = 90;
        this.pixels = new int[this.width*this.height];
        this.name = name;
    }

    public Hero(String name, Map<Stat, Integer> stats, Skill[] skills) {
        this.width = 64;
        this.height = 90;
        this.pixels = new int[this.width*this.height];
        this.name = name;
        this.stats = stats;
        this.skills = skills;
    }

    public void enterArena(boolean enemy, int position, Arena arena) {
        this.enemy = enemy;
        this.position = position;
        this.arena = arena;
    }

    public <T extends Hero> void initBasePath(String name) {
        String base = "src/game/entities/individuals/";
        this.basePath = base + name;
    }

    protected abstract void initAnimator();
    protected abstract void initSkills();
    protected abstract void initStats();

    @Override
    public void update(int frame) {
        this.anim.animate();
    }
    @Override
    public int[] render() {
        background(Color.VOID);
        renderImage();
        renderBars();
        renderEffects();
        return pixels;
    }

    private void renderImage() {
        int[] image = this.enemy ? SpriteUtils.flipHorizontal(this.anim.image, 64) : this.anim.image;
        fillWithGraphicsSize(0, 0, 64, 64, image, false);
    }
    private void renderBars() {
        fillWithGraphicsSize(0, 65, 64, 5, getBar(64, 5, getResourcePercentage(Stat.LIFE), Color.GREEN, Color.DARKRED), false);
        if (this.secondaryResource != null) {
            fillWithGraphicsSize(0, 71, 64, 5, getBar(64, 5, getResourcePercentage(this.secondaryResource), getResourceColor(this.secondaryResource), Color.DARKRED), false);
        }
    }
    private void renderEffects() {
        int effectsX = 0;
        int effectsY = 72;
        int paintedEffects = 0;
        int paddingX = 2;
        int paddingY = 5;
        for (Effect effect : this.effects) {
            int[] sprite = SpriteLibrary.sprites.get(effect.getClass().getName());
//            GUIElement.addBorder(Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE, sprite, Color.WHITE);
//            effect.addStacksToSprite(sprite);
            fillWithGraphicsSize(effectsX, effectsY, Property.EFFECT_ICON_SIZE, Property.EFFECT_ICON_SIZE,
                    sprite, false, null,
                    effect.type.equals(Effect.ChangeEffectType.EFFECT) ? Color.DARKYELLOW : Color.RED);
            if (effect.stackable) {
                int[] stacks = getSmallNumTextLine(effect.stacks+"", Property.EFFECT_ICON_SIZE, this.editor.smallNumHeight, TextAlignment.RIGHT, Color.VOID, Color.BLACK);
                fillWithGraphicsSize(effectsX +1, effectsY + (Property.EFFECT_ICON_SIZE-2), Property.EFFECT_ICON_SIZE, this.editor.smallNumHeight,
                        stacks, false);
            } else if (effect.turns > 0) {
                int[] turns = getSmallNumTextLine(effect.turns+"", Property.EFFECT_ICON_SIZE, this.editor.smallNumHeight, TextAlignment.RIGHT, Color.VOID, Color.WHITE);
                fillWithGraphicsSize(effectsX +1, effectsY + (Property.EFFECT_ICON_SIZE-2), Property.EFFECT_ICON_SIZE, this.editor.smallNumHeight,
                        turns, false);
            }


            paintedEffects++;

            if (paintedEffects % 5 == 0) {
                effectsY += Property.EFFECT_ICON_SIZE + paddingY;
                effectsX = 0;
            } else {
                effectsX += Property.EFFECT_ICON_SIZE + paddingX;
            }
        }
    }

//StatMagic
    public int getStat(Stat stat) {
        if (stat == null) {
            return 0;
        }
        return this.stats.get(stat);
    }

    public void changeStatTo(Stat stat, int value) {
        this.stats.put(stat, value);
    }

    public void addToStat(Stat stat, int value) {
        this.stats.put(stat, Math.max(this.stats.get(stat) + value, 0));
    }

    public void addResource(Stat currentStat, Stat maxStat, int value) {
        int result = value + this.getStat(currentStat);
        int max = this.getStat(maxStat);
        if (result < 0) {
            this.changeStatTo(currentStat, 0);
        } else if ( result <= max) {
            this.changeStatTo(currentStat, result);
        } else {
            this.changeStatTo(currentStat, max);
        }
    }

    public int getSkillAccuracy(Skill skill) {
        if (skill.getAccuracy() == -1 || Skill.MAX_ACC_TARGET_TYPES.contains(skill.getTargetType())) {
            return 100;
        }
        return skill.getAccuracy();
    }
    public double getResourcePercentage(Stat resource) {
        if (resource == null) {
            return 0.0;
        }
        switch (resource) {
            case LIFE:
                return ((double)this.getStat(Stat.CURRENT_LIFE)) / this.getStat(Stat.LIFE);
            case MANA:
                return ((double)this.getStat(Stat.CURRENT_MANA)) / this.getStat(Stat.MANA);
            case FAITH:
                return ((double)this.getStat(Stat.CURRENT_FAITH)) / this.getStat(Stat.FAITH);
        }
        return 0.0;
    }
    public int getCurrentLifePercentage() {
        return this.stats.get(Stat.CURRENT_LIFE) * 100 / this.stats.get(Stat.LIFE);
    }
    public int getMissingLifePercentage() {
        return (100 - getCurrentLifePercentage());
    }


//TurnMagic
    public void prepareCast() {
        for (Skill skill : this.skills) {
            if (skill != null) {
                CastChangePayload payload = new CastChangePayload()
                        .setSkill(skill);
                Connector.fireTopic(Connector.CAST_CHANGE, payload);
            }
        }
    }
    public void startOfTurn() {
        StartOfTurnPayload startOfTurnPayload = new StartOfTurnPayload();
        Connector.fireTopic(this.id + Connector.START_OF_TURN, startOfTurnPayload);

        setActions();
    }
    public void endOfTurn() {
        EndOfTurnPayload endOfTurnPayload = new EndOfTurnPayload();
        Connector.fireTopic(this.id + Connector.END_OF_TURN, endOfTurnPayload);
        effectTurn();
        skillTurn();
        if (this.getStat(Stat.CURRENT_LIFE)<1) {
            return;
        }
        int heal = getStat(Stat.LIFE_REGAIN);
        if (this.hasPermanentEffect(Injured.class) > 0) {
            return;
        }
        heal(this, heal, null);
        changeStatTo(Stat.CURRENT_ACTION, this.stats.get(Stat.MAX_ACTION));
    }
    private void setActions() {
        ActionInflictionPayload actionInflictionPayload = new ActionInflictionPayload();
        Connector.fireTopic(Connector.ACTION_INFLICTION, actionInflictionPayload);
        this.addToStat(Stat.CURRENT_ACTION, actionInflictionPayload.getInfliction());
        if (this.getStat(Stat.CURRENT_ACTION) < 0) {
            this.changeStatTo(Stat.CURRENT_ACTION, 0);
        }
    }

//EffectMagic
    private void effectTurn() {
        for (Effect effect : effects) {
            effect.turn();
        }
        cleanUpEffect();
    }
    private void cleanUpEffect() {
        List<Effect> toRemove = this.effects.stream()
                .filter(e->e.turns == 0 || (e.stackable && e.stacks <= 0))
                .toList();
        for (Effect effectToRemove : toRemove) {
            removeEffect(effectToRemove);
        }
    }
    private void removeEffect(Effect effect) {
        effect.removeEffect();
        this.effects.remove(effect);
    }
    public <T extends Effect> int getPermanentEffectStacks(Class<T> clazz) {
        int amount = 0;
        for (Effect currentEffect : effects) {
            if (currentEffect.getClass().equals(clazz)) {
                amount+=currentEffect.stacks;
            }
        }
        return amount;
    }
    public void addAllEffects(List<Effect> effects, Hero caster) {
        for (Effect effect : effects) {
            this.addEffect(effect, caster);
        }
    }
    public void addEffect(Effect effect, Hero caster) {

        if (getEffectFailure(effect, caster)) {
            return;
        }
        boolean added = false;
        boolean newlyAdded = false;
        for (Effect effectHave : effects) {
            if (effectHave.getClass().equals(effect.getClass())) {
                if (effect.stackable) {
                    added = true;
                    newlyAdded = true;
                    effectHave.addStack(effect.stacks);
                } else {
                    effectHave.turns = effect.turns;
                    added = true;
                }
            }
        }
        if (!added) {
            Effect newEffect = effect.getNew();
            newEffect.origin = caster;
            newEffect.hero = this;
            this.effects.add(newEffect);
            newEffect.addToHero();
            newlyAdded = true;
        }
        if (newlyAdded) {
            EffectAddedPayload effectAddedPayload = new EffectAddedPayload()
                    .setEffect(effect)
                    .setCaster(caster);
            Connector.fireTopic(Connector.EFFECT_ADDED, effectAddedPayload);
        }
    }
    private boolean getEffectFailure(Effect effect, Hero caster) {
        EffectFailurePayload payload = new EffectFailurePayload()
                .setEffect(effect)
                .setCaster(caster);
        Connector.fireTopic(Connector.EFFECT_FAILURE, payload);
        return payload.isFailure();
    }

    public <T extends Effect> int hasPermanentEffect(Class<T> clazz) {
        int amount = 0;
        for (Effect currentEffect : effects) {
            if (currentEffect.getClass().equals(clazz)) {
                amount+=currentEffect.stacks;
            }
        }
        return amount;
    }
    public <T extends Effect> void decreaseEffectStack(Class<T> clazz) {
        if (this.hasPermanentEffect(clazz) > 0) {
            this.getPermanentEffect(clazz).stacks--;
            this.cleanUpEffect();
        }
    }
    public <T extends Effect> void removePermanentEffectOfClass(Class<T> clazz) {
        Logger.logLn(this.name + ".removePermanentEffect:" + clazz.getName());
        List<Effect> toRemove = this.effects.stream()
                .filter(e->e.getClass().equals(clazz))
                .toList();
        for (Effect effect : toRemove) {
            removeEffect(effect);
        }
    }
    public <T extends Effect> Effect getPermanentEffect(Class<T> clazz) {
        for (Effect currentEffect : effects) {
            if (currentEffect.getClass().equals(clazz)) {
                return currentEffect;
            }
        }
        return null;
    }

//SkillMagic
    private void skillTurn() {
        for (Skill s : this.skills) {
            if (s != null) {
                s.turn();
            }
        }
    }
    public <T extends Skill> boolean hasSkill(Class<T> clazz) {
        for (Skill s : this.skills) {
            if (s!= null && s.getClass().equals(clazz)) {
                return true;
            }
        }
        return false;
    }
    public boolean canPerform(Skill s) {

        if (!canPerformResourceCheck(s)) {
            return false;
        }
        CanPerformPayload canPerformPayload = new CanPerformPayload()
                .setSkill(s);
        Connector.fireTopic(Connector.CAN_PERFORM, canPerformPayload);
        return canPerformPayload.success;
    }
    private boolean canPerformResourceCheck(Skill s) {
        return this.stats.get(Stat.CURRENT_LIFE) > s.getLifeCost() &&
                this.stats.get(Stat.CURRENT_ACTION) >= s.getActionCost() &&
                this.stats.get(Stat.CURRENT_FAITH) >= s.getFaithCost() &&
                this.stats.get(Stat.CURRENT_MANA) >= s.getManaCost() &&
                s.getCdCurrent()<=0 && !s.isPassive();
    }
    public void payForSkill(Skill s) {
        addToStat(Stat.CURRENT_LIFE, -1*s.getLifeCost(this));
        addToStat(Stat.CURRENT_ACTION, -1*s.getActionCost());
        addToStat(Stat.CURRENT_FAITH, -1*s.getFaithCost());
        addToStat(Stat.CURRENT_MANA, -1*s.getManaCost());
        Logger.logLn("Paid life:"+s.getLifeCost(this));
        Logger.logLn("Paid action:"+s.getActionCost());
    }

    public void cleanse() {
        this.effects = new ArrayList<>();
    }

    public int simulateHealInPercentages(Hero caster, int heal, Skill skill) {
        HealChangesPayload healChangesPayload = new HealChangesPayload()
                .setCaster(caster)
                .setTarget(this)
                .setSkill(skill)
                .setHeal(heal);
        Connector.fireTopic(Connector.HEAL_CHANGES, healChangesPayload);
        int pureHeal = healChangesPayload.getHeal();
        int actualHeal = Math.min(pureHeal, this.stats.get(Stat.LIFE) - this.stats.get(Stat.CURRENT_LIFE));
        return actualHeal * 100 / this.stats.get(Stat.LIFE);
    }
    public void heal(Hero caster, int heal, Skill skill) {
        HealChangesPayload healChangesPayload = new HealChangesPayload()
                .setCaster(caster)
                .setTarget(this)
                .setSkill(skill)
                .setHeal(heal);
        Connector.fireTopic(Connector.HEAL_CHANGES, healChangesPayload);
        int resultHeal = healChangesPayload.getHeal();
        addResource(Stat.CURRENT_LIFE, Stat.LIFE, resultHeal);
    }

    public int damage(Hero caster, int damage, DamageType dmgType, int lethality, Skill skill) {

        int def = getStat(getDefenseStatFor(dmgType));
        int result = MyMaths.getDamage(damage, def, lethality);
        DmgChangesPayload dmgChangesPayload = new DmgChangesPayload()
                .setCaster(caster)
                .setTarget(this)
                .setSkill(skill)
                .setDmg(result)
                .setDmgtype(dmgType)
                .setSimulate(false);
        Connector.fireTopic(Connector.DMG_CHANGES, dmgChangesPayload);
        System.out.println("dmg:"+result);
        Logger.logLn("1play dmg animation of " + this.name + "/"+this.id);
        playAnimation("damaged", true);
        addResource(Stat.CURRENT_LIFE, Stat.LIFE, -1*result);
        return result;
    }
    private Stat getDefenseStatFor(DamageType dmgType) {
        if (Objects.requireNonNull(dmgType) == DamageType.NORMAL) {
            return Stat.STAMINA;
        }
        return Stat.ENDURANCE;
    }
    public int simulateDamageInPercentages(Hero caster, int damage, DamageType dmgType, int lethality, Skill skill) {
        int def = getStat(getDefenseStatFor(dmgType));
        int result = MyMaths.getDamage(damage, def, lethality);
        DmgChangesPayload dmgChangesPayload = new DmgChangesPayload()
                .setCaster(caster)
                .setTarget(this)
                .setSkill(skill)
                .setDmg(result)
                .setDmgtype(dmgType)
                .setSimulate(true);
        Connector.fireTopic(Connector.DMG_CHANGES, dmgChangesPayload);
        return result * 100 / this.stats.get(Stat.LIFE);
    }

    public void effectDamage(int damage, Effect effect) {
        EffectDmgChangesPayload dmgChangesPayload = new EffectDmgChangesPayload()
                .setTarget(this)
                .setEffect(effect)
                .setDmg(damage);
        Connector.fireTopic(Connector.EFFECT_DMG_CHANGES, dmgChangesPayload);
        damage = dmgChangesPayload.dmg;
        Logger.logLn("2play dmg animation of " + this.name + " at pos " + this.position);
        this.anim.playAnimation("damaged", true);
        addToStat(Stat.CURRENT_LIFE, -1*damage);
        DmgTriggerPayload dmgTriggerPayload = new DmgTriggerPayload()
                .setDmgDone(damage)
                .setTarget(this)
                .setEffect(effect);
        Connector.fireTopic(Connector.EFFECT_DMG_TRIGGER, dmgTriggerPayload);
    }


//GUI
    public void playAnimation(String anim) {
        this.playAnimation(anim, true);
    }
    public void playAnimation(String anim, boolean waitfor) {
        if (this.anim != null) {
            Logger.logLn("play skill animation of " + this.name);
            this.anim.playAnimation(anim, waitfor);
        }
    }
    public String getResourceString(Stat resource) {
        if (resource == null) {
            return "";
        }
        return switch (resource) {
            case LIFE -> getHealthString();
            case MANA -> getManaString();
            case FAITH -> getFaithString();
            default -> "";
        };
    }

    private String getFaithString() {
        return this.stats.get(Stat.CURRENT_FAITH) + "/" + this.stats.get(Stat.FAITH);
    }

    private String getManaString() {
        return this.stats.get(Stat.CURRENT_MANA) + "(+" + this.stats.get(Stat.MANA_REGAIN) + ")/" + this.stats.get(Stat.MANA);
    }

    public String getHealthString() {
        return this.stats.get(Stat.CURRENT_LIFE) + "(+" + this.stats.get(Stat.LIFE_REGAIN) + ")/" + this.stats.get(Stat.LIFE);
    }

    public static Color getResourceColor(Stat stat) {
        if (stat == null) {
            return Color.BLACK;
        }
        return switch (stat) {
            case LIFE -> Color.GREEN;
            case MANA -> Color.BLUE;
            case FAITH -> Color.DARKYELLOW;
            default -> null;
        };
    }
    public List<Hero> getAllies() {
        List<Hero> allies = new ArrayList<>();
        for (Hero hero: this.team.heroes) {
            if (hero != this) {
                allies.add(hero);
            }
        }
        return allies;
    }
    public List<Hero> getEnemies() {
        if (this.enemy) {
            return Arrays.stream(this.arena.friends.heroes).filter(Objects::nonNull).toList();
        } else {
            return Arrays.stream(this.arena.enemies.heroes).filter(Objects::nonNull).toList();
        }
    }

//GETTERS SETTERS

    public int getId() {
        return id;
    }

    public Hero setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Hero setName(String name) {
        this.name = name;
        return this;
    }

    public Skill[] getSkills() {
        return skills;
    }

    public Hero setSkills(Skill[] skills) {
        this.skills = skills;
        return this;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public Hero setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
        return this;
    }

    public String getPortraitName() {
        return portraitName;
    }

    public Hero setPortraitName(String portraitName) {
        this.portraitName = portraitName;
        return this;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public Hero setEffects(List<Effect> effects) {
        this.effects = effects;
        return this;
    }

    public Stat getSecondaryResource() {
        return secondaryResource;
    }

    public Hero setSecondaryResource(Stat secondaryResource) {
        this.secondaryResource = secondaryResource;
        return this;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isEnemy() {
        return enemy;
    }

    public void setEnemy(boolean enemy) {
        this.enemy = enemy;
    }
}
