package game.skills;

public abstract class GlobalEffect {

    private final String name;

    protected GlobalEffect(String name) {
        this.name = name;
    }

    public abstract void initSubscriptions();
    public abstract void turn();

    public String getName() {
        return name;
    }
}
