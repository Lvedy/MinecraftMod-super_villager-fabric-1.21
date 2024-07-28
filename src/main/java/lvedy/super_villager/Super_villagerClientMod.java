package lvedy.super_villager;

import lvedy.super_villager.registry.ModNetWorking;
import lvedy.super_villager.special.event.KeyInputHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class Super_villagerClientMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModNetWorking.registryClient();
        KeyInputHandler.registry();
    }
}
