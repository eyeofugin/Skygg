package game.skills;

public class Resource {
    private Stat resource;
    private Stat maxResource;
    private int amount;

    public Resource(Stat resource, Stat maxResource, int amount) {
        this.resource = resource;
        this.amount = amount;
        this.maxResource = maxResource;
    }

    public Stat getResource() {
        return resource;
    }

    public void setResource(Stat resource) {
        this.resource = resource;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Stat getMaxResource() {
        return maxResource;
    }

    public void setMaxResource(Stat maxResource) {
        this.maxResource = maxResource;
    }
}
