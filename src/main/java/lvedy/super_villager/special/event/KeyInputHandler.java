package lvedy.super_villager.special.event;

import lvedy.super_villager.ModSetting;
import lvedy.super_villager.special.entity.custom.SuperVillagerEntity;
import lvedy.super_villager.special.networking.SettingC2Spayload_2;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class KeyInputHandler {
    public static final String KEY_CATEGORY_SV = "卡慕模组:超级村民";
    public static final String KEY_DRINK = "模组设置界面调出按键";
    public static final String KEY_TRADE = "交易界面调出按键";

    public static KeyBinding drinkingkey;
    public static KeyBinding tradekey;


    public static void registryDrinkWater(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(client.getNetworkHandler() != null) {
                if (drinkingkey.wasPressed()) {
                    ModSetting.open_screen = true;
                    ModSetting.look_screen = true;
                    ClientPlayNetworking.send(new SettingC2Spayload_2((ModSetting.getSettingIntArray()),false,false));
                }
            }
        });
    }

    public static void registryTrade(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(client.getNetworkHandler() != null) {
                if (tradekey.wasPressed() && ModSetting.d) {
                    if (client.player != null) {
                        if(!client.player.getWorld().getEntitiesByClass(SuperVillagerEntity.class,client.player.getBoundingBox().expand(15), Entity::isAlive).isEmpty()) {
                            ClientPlayNetworking.send(new SettingC2Spayload_2((ModSetting.getSettingIntArray()), false,true));
                        }
                        else
                            client.player.sendMessage(Text.literal("你离村民太远了!!").formatted(Formatting.DARK_RED));
                    }
                }
            }
        });
    }

    public static void registry(){
        drinkingkey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_DRINK, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_O,KEY_CATEGORY_SV
        ));
        registryDrinkWater();
        tradekey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_TRADE, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_P,KEY_CATEGORY_SV
        ));
        registryTrade();
    }
}
