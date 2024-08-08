package lvedy.super_villager.registry;

import lvedy.super_villager.ModSetting;
import lvedy.super_villager.special.GUI.TradeGUI;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Objects;

import static net.minecraft.server.command.CommandManager.literal;

public class ModCommand {

    public static void registry(){
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("变身")
                .executes(context -> {
                    ModSetting.pian_xin = !ModSetting.pian_xin;
                    return 1;
                })
        ));

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("开始")
                .executes(context -> {
                    ModSetting.resetTrade();
                    ModSetting.start = !ModSetting.start;
                    return 1;
                })
        ));

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("测试屏幕")
                .executes(context -> {
                    TradeGUI.open(context.getSource().getPlayerOrThrow());
                    return 1;
                })
        ));

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("危险等级")
                .executes(context -> {
                    Objects.requireNonNull(context.getSource().getPlayer()).sendMessage(Text.literal("""
                        危险等级将影响怪的生命，攻击，护甲等,在一些特殊波次怪会有其余加强，以下是一些示例
                        危险等级3: 蜘蛛会被刷出
                        危险等级5: 苦力怕会被刷出/在地狱时被替换为凋零骷髅
                        危险等级7: 僵尸获得生命恢复I
                        危险等级10:苦力怕有概率生成闪电苦力怕
                                    在地狱时被替换为凋零骷髅会破坏方块(有20s冷却)
                        危险等级15:女巫会被刷出
                        危险等级20:骷髅刷新占比变高,所有怪获得抗性提升I
                        危险等级25:蜘蛛会被替换为洞穴蜘蛛
                        危险等级30:所有怪获得力量I
                        .....
                        """).formatted(Formatting.GREEN));
                    return 1;
                })
        ));

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("测试")
                .executes(context -> {
                    Enchantment enchantment = context.getSource().getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).get(Enchantments.EFFICIENCY);
                    ItemStack itemStack = Items.DIAMOND_SHOVEL.getDefaultStack();
                    itemStack.addEnchantment(RegistryEntry.of(enchantment),4);
                    context.getSource().getPlayerOrThrow().getInventory().insertStack(itemStack);
                    return 1;
                })
        ));
    }
}
