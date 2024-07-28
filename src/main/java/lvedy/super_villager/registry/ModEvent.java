package lvedy.super_villager.registry;

import lvedy.super_villager.special.event.OverWorld;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class ModEvent {
    static OverWorld overWorld = new OverWorld();

    public static void registry(){
        ServerTickEvents.START_SERVER_TICK.register(overWorld);
    }
}
