package lvedy.super_villager.registry;

import lvedy.super_villager.ModSetting;
import lvedy.super_villager.Super_villager;
import lvedy.super_villager.special.GUI.SettingScreen;
import lvedy.super_villager.special.GUI.TradeGUI;
import lvedy.super_villager.special.networking.DeadC2SPayload;
import lvedy.super_villager.special.networking.SettingC2SPayload;
import lvedy.super_villager.special.networking.SettingC2Spayload_2;
import lvedy.super_villager.special.networking.TradeC2SPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ModNetWorking{
    public static final Identifier SET1 = Identifier.of(Super_villager.MOD_ID, "set1");
    public static final Identifier SET2 = Identifier.of(Super_villager.MOD_ID, "set2");
    public static final Identifier TRADE = Identifier.of(Super_villager.MOD_ID, "trade");
    public static final Identifier DEAD = Identifier.of(Super_villager.MOD_ID, "dead");
    public static void registry(){
        //服务器接收到客户端包的处理
        PayloadTypeRegistry.playC2S().register(SettingC2Spayload_2.ID, SettingC2Spayload_2.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(SettingC2Spayload_2.ID, (payload, context) -> context.server().execute(() -> {
            int[] int_s = payload.a();
            if(payload.b())
                ModSetting.setSetting(int_s);
            for(ServerPlayerEntity player:context.server().getPlayerManager().getPlayerList()) {
                ServerPlayNetworking.send(player,new SettingC2SPayload(ModSetting.getSettingIntArray()));
            }
            if(payload.trade())
                TradeGUI.open(context.player());
        }));
        //更新交易界面
        PayloadTypeRegistry.playC2S().register(TradeC2SPayload.ID, TradeC2SPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(TradeC2SPayload.ID, (payload, context) -> context.server().execute(() -> {
            for(ServerPlayerEntity player:ModSetting.tradingPlayer) {
                TradeGUI.open(player);
            }
        }));
        //村民趋势
        PayloadTypeRegistry.playC2S().register(DeadC2SPayload.ID, DeadC2SPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(DeadC2SPayload.ID, (payload, context) -> context.server().execute(() -> {
            for(ServerPlayerEntity player:context.server().getPlayerManager().getPlayerList()) {
                player.changeGameMode(GameMode.SPECTATOR);
                ModSetting.sendTitle(player, "游戏失败", Formatting.RED);
            }
        }));

        //客户端接收到服务器包的处理
        PayloadTypeRegistry.playS2C().register(SettingC2SPayload.ID, SettingC2SPayload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(SettingC2SPayload.ID, (payload, context) -> context.client().execute(() -> {
            int[] int_s = payload.a();
            ModSetting.setSetting(int_s);
            if (ModSetting.open_screen || ModSetting.look_screen) {
                context.client().setScreen(new SettingScreen(Text.literal("设置")));
                ModSetting.open_screen = false;
            }
            context.player().currentScreenHandler.sendContentUpdates();
            //Objects.requireNonNull(context.client().player).sendMessage(Text.of("步骤3完成"));
        }));
    }

    public static void registryServer(){
        //服务器接收到客户端的数据后的处理
    }

    public static void registryClient(){
        //客户端接收到服务端的数据后的处理
    }

    public static Boolean Trade(PlayerEntity player, int a){
        Registry<Enchantment> enchantmentRegistry = player.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT);
        if(a==1)
            return ModSetting.getSlotByItem(player, Items.EMERALD.getDefaultStack(),Items.ROTTEN_FLESH.getDefaultStack(),1,8);
        if(a==2)
            return ModSetting.getSlotByItem(player,Items.EMERALD.getDefaultStack(),Items.ARROW.getDefaultStack(),1,8);
        if(a==3)
            return ModSetting.getSlotByItem(player,Items.EMERALD.getDefaultStack(),Items.BONE.getDefaultStack(),1,8);
        if(a==4)
            return ModSetting.getSlotByItem(player,Items.EMERALD.getDefaultStack(),Items.STRING.getDefaultStack(),2,7);
        if(a==5)
            return ModSetting.getSlotByItem(player,Items.EMERALD.getDefaultStack(),Items.SPIDER_EYE.getDefaultStack(),1,3);
        if(a==6)
            return ModSetting.getSlotByItem(player,Items.EMERALD.getDefaultStack(),Items.GUNPOWDER.getDefaultStack(),3,5);
        if(a==7)
            return ModSetting.TradeForVillager(player,Items.EMERALD,24,0);
        if(a==8)
            return ModSetting.TradeForVillager(player,Items.EMERALD,24,1);
        if(a==9)
            return ModSetting.TradeForVillager(player,Items.EMERALD,24,2);
        if(a==10)
            return ModSetting.getSlotByItem(player,Items.ANVIL.getDefaultStack(),Items.EMERALD.getDefaultStack(),1,48);
        if(a==11) {
            ItemStack itemStack = Items.DIAMOND_SHOVEL.getDefaultStack();
            itemStack.addEnchantment(enchantmentRegistry.getEntry(enchantmentRegistry.get(Enchantments.EFFICIENCY)), 4);
            itemStack.addEnchantment(enchantmentRegistry.getEntry(enchantmentRegistry.get(Enchantments.UNBREAKING)), 4);
            itemStack.addEnchantment(enchantmentRegistry.getEntry(enchantmentRegistry.get(Enchantments.FORTUNE)), 10);
            itemStack.set(DataComponentTypes.CUSTOM_NAME,Text.literal("逗猫棒").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            return ModSetting.getSlotByItem(player,itemStack,Items.EMERALD.getDefaultStack(),1,64);
        }
        if(a==12)
            return ModSetting.getSlotByItem(player,Items.EXPERIENCE_BOTTLE.getDefaultStack(),Items.EMERALD.getDefaultStack(),32,8);
        if(a==13) {
            ItemStack itemStack = Items.ENCHANTED_BOOK.getDefaultStack();
            itemStack.addEnchantment(enchantmentRegistry.getEntry(enchantmentRegistry.get(Enchantments.POWER)), 1);
            return ModSetting.getSlotByItem(player, itemStack, Items.EMERALD.getDefaultStack(), 1, 10);
        }
        if(a==14) {
            ItemStack itemStack = Items.ENCHANTED_BOOK.getDefaultStack();
            itemStack.addEnchantment(enchantmentRegistry.getEntry(enchantmentRegistry.get(Enchantments.SHARPNESS)), 1);
            return ModSetting.getSlotByItem(player, itemStack, Items.EMERALD.getDefaultStack(), 1, 8);
        }
        if(a==15) {
            ItemStack itemStack = Items.ENCHANTED_BOOK.getDefaultStack();
            itemStack.addEnchantment(enchantmentRegistry.getEntry(enchantmentRegistry.get(Enchantments.PROTECTION)), 1);
            return ModSetting.getSlotByItem(player, itemStack, Items.EMERALD.getDefaultStack(), 1, 4);
        }
        if(a==16)
            return ModSetting.TradeForVillager(player,Items.EMERALD,32,3);
        if(a==17)
            return ModSetting.TradeForVillager(player,Items.EMERALD,64,4);
        if(a==18)
            return ModSetting.TradeForVillager(player,Items.EMERALD,64,5);
        if(a==19)
            return ModSetting.getSlotByItem(player,Items.NETHERITE_INGOT.getDefaultStack(),Items.EMERALD.getDefaultStack(),1,56);
        if(a==20){
            Enchantment enchantment1 = player.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).get(Enchantments.UNBREAKING);
            Enchantment enchantment2 = player.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).get(Enchantments.PROTECTION);
            ItemStack itemStack = Items.DIAMOND_BOOTS.getDefaultStack();
            itemStack.addEnchantment(RegistryEntry.of(enchantment1),4);
            itemStack.addEnchantment(RegistryEntry.of(enchantment2),4);
            itemStack.set(DataComponentTypes.CUSTOM_NAME,Text.literal("足力健").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            List<AttributeModifiersComponent.Entry> list = new ArrayList<>(){{
                add(new AttributeModifiersComponent.Entry(EntityAttributes.GENERIC_MOVEMENT_SPEED,new EntityAttributeModifier(Identifier.of(Super_villager.MOD_ID,"base_speed"),0.8, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE), AttributeModifierSlot.FEET));
            }};
            itemStack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS,new AttributeModifiersComponent(list,true));
            return ModSetting.getSlotByItem(player,itemStack,Items.EMERALD.getDefaultStack(),1,64);
        }
        if(a==21){
            ItemStack itemStack = Items.ENCHANTED_GOLDEN_APPLE.getDefaultStack();
            Collection<? extends Text> texts = new ArrayList<>() {{
                add(Text.literal("这是茄子").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
                add(Text.literal("(这真的不是附魔金苹果)").styled((style) -> style.withStrikethrough(true).withItalic(false)).formatted(Formatting.GRAY));
            }};
            itemStack.set(DataComponentTypes.CUSTOM_NAME,Texts.join(texts, Text.of(" ")));
            return ModSetting.getSlotByItem(player,itemStack,Items.EMERALD.getDefaultStack(),1,16);
        }
        if(a==22)
            return ModSetting.getSlotByItem(player,Items.EMERALD.getDefaultStack(),Items.IRON_INGOT.getDefaultStack(),1,2);
        return false;
    }
}
