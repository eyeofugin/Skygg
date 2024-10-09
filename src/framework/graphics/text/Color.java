package framework.graphics.text;

public enum Color {
    BLACK(0),
    WHITE(-1),
    DARKGREY(7829367),
    GREEN(9104670),
    RED(-65536),
    VOID(-12450784),
    MEDIUMGREEN(1283100),
    DARKYELLOW(12558336),
    LIGHTYELLOW(15204096),
    DARKGREEN(18184),
    BROWN(10839079),
    BLUE(9215),
    DARKRED(6816256);

    public final int VALUE;

    Color(int value) {this.VALUE = value;}
}
