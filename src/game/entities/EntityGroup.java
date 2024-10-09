package game.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntityGroup {

    public Entity[] entities;
    public List<Entity> fallen = new ArrayList<>();

    public EntityGroup(Entity[] entities) {
        this.entities = entities;
    }

}
