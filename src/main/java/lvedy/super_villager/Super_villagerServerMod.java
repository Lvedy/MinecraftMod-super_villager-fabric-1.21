package lvedy.super_villager;

import lvedy.super_villager.registry.ModNetWorking;
import net.fabricmc.api.DedicatedServerModInitializer;

public class Super_villagerServerMod implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        ModNetWorking.registryServer();
    }
}
