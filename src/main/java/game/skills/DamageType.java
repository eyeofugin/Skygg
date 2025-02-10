package game.skills;

public enum DamageType {
    NORMAL, //neutral
    HEAT, //+ frost dark - light
    FROST, //+ light - heat ~ dark
    DARK,//+ light - heat ~ frost
    LIGHT,//+ heat - light frost
    TRUE;
}
