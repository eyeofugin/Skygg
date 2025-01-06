package framework.connector.payloads;

import framework.connector.ConnectionPayload;
import framework.states.Arena;

public class EndOfRoundPayload extends ConnectionPayload {
    public Arena arena;
    public EndOfRoundPayload setArena(Arena arena) {
        this.arena = arena;
        return this;
    }
}
