package lvedy.super_villager.special.GUI;

import com.mojang.authlib.GameProfile;
import eu.pb4.sgui.api.ClickType;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.elements.GuiElementInterface;
import eu.pb4.sgui.api.gui.SimpleGui;
import lvedy.super_villager.ModSetting;
import lvedy.super_villager.Super_villager;
import lvedy.super_villager.registry.ModItem;
import lvedy.super_villager.special.networking.TradeC2SPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class TradeGUI {
    public static void open(ServerPlayerEntity player) {
        SimpleGui gui = getSimpleGui(player);
        //1号位
        {
            ItemStack itemStack = Items.ROTTEN_FLESH.getDefaultStack();
            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("↓").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            gui.setSlot(1, new GuiElementBuilder(itemStack).setCount(8).setCallback((index, clickType, actionType) -> ModSetting.ModTrade(player,1)));
        }
        //2号位
        {
            ItemStack itemStack = Items.ARROW.getDefaultStack();
            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("↓").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            gui.setSlot(2, new GuiElementBuilder(itemStack).setCount(8).setCallback((index, clickType, actionType) -> ModSetting.ModTrade(player,2)));
        }
        //3号位
        {
            ItemStack itemStack = Items.BONE.getDefaultStack();
            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("↓").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            gui.setSlot(3, new GuiElementBuilder(itemStack).setCount(8).setCallback((index, clickType, actionType) -> ModSetting.ModTrade(player,3)));
        }
        //4号位
        {
            ItemStack itemStack = Items.STRING.getDefaultStack();
            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("↓").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            gui.setSlot(4, new GuiElementBuilder(itemStack).setCount(7).setCallback((index, clickType, actionType) -> ModSetting.ModTrade(player,4)));
        }
        //5号位
        {
            ItemStack itemStack = Items.SPIDER_EYE.getDefaultStack();
            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("↓").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            gui.setSlot(5, new GuiElementBuilder(itemStack).setCount(3).setCallback((index, clickType, actionType) -> ModSetting.ModTrade(player,5)));
        }
        //6号位
        {
            ItemStack itemStack = Items.GUNPOWDER.getDefaultStack();
            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("↓").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            gui.setSlot(6, new GuiElementBuilder(itemStack).setCount(5).setCallback((index, clickType, actionType) -> ModSetting.ModTrade(player,6)));
        }
        //7号位
        {
            ItemStack itemStack = Items.IRON_INGOT.getDefaultStack();
            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("↓").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            gui.setSlot(7, new GuiElementBuilder(itemStack).setCount(5).setCallback((index, clickType, actionType) -> ModSetting.ModTrade(player,22)));
        }
        //10号位
        {
            ItemStack itemStack = Items.EMERALD.getDefaultStack();
            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("购买绿宝石x1").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            itemStack.set(DataComponentTypes.LORE, LoreComponent.DEFAULT.with(Text.literal("花费腐肉x8").formatted(Formatting.GREEN).styled((style) -> style.withItalic(false))));
            gui.setSlot(10, new GuiElementBuilder(itemStack).setCount(1).setCallback((index, clickType, actionType) -> ModSetting.ModTrade(player,1)));
        }
        //11号位
        {
            ItemStack itemStack = Items.EMERALD.getDefaultStack();
            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("购买绿宝石x1").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            itemStack.set(DataComponentTypes.LORE, LoreComponent.DEFAULT.with(Text.literal("花费箭矢x8").formatted(Formatting.GREEN).styled((style) -> style.withItalic(false))));
            gui.setSlot(11, new GuiElementBuilder(itemStack).setCount(1).setCallback((index, clickType, actionType) -> ModSetting.ModTrade(player,2)));
        }
        //12号位
        {
            ItemStack itemStack = Items.EMERALD.getDefaultStack();
            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("购买绿宝石x1").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            itemStack.set(DataComponentTypes.LORE, LoreComponent.DEFAULT.with(Text.literal("花费骨头x8").formatted(Formatting.GREEN).styled((style) -> style.withItalic(false))));
            gui.setSlot(12, new GuiElementBuilder(itemStack).setCount(1).setCallback((index, clickType, actionType) -> ModSetting.ModTrade(player,3)));
        }
        //13号位
        {
            ItemStack itemStack = Items.EMERALD.getDefaultStack();
            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("购买绿宝石x2").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            itemStack.set(DataComponentTypes.LORE, LoreComponent.DEFAULT.with(Text.literal("花费线x7").formatted(Formatting.GREEN).styled((style) -> style.withItalic(false))));
            gui.setSlot(13, new GuiElementBuilder(itemStack).setCount(2).setCallback((index, clickType, actionType) -> ModSetting.ModTrade(player,4)));
        }
        //14号位
        {
            ItemStack itemStack = Items.EMERALD.getDefaultStack();
            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("购买绿宝石x1").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            itemStack.set(DataComponentTypes.LORE, LoreComponent.DEFAULT.with(Text.literal("花费蜘蛛眼x3").formatted(Formatting.GREEN).styled((style) -> style.withItalic(false))));
            gui.setSlot(14, new GuiElementBuilder(itemStack).setCount(1).setCallback((index, clickType, actionType) -> ModSetting.ModTrade(player,5)));
        }
        //15号位
        {
            ItemStack itemStack = Items.EMERALD.getDefaultStack();
            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("购买绿宝石x3").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            itemStack.set(DataComponentTypes.LORE, LoreComponent.DEFAULT.with(Text.literal("花费火药x5").formatted(Formatting.GREEN).styled((style) -> style.withItalic(false))));
            gui.setSlot(15, new GuiElementBuilder(itemStack).setCount(3).setCallback((index, clickType, actionType) -> ModSetting.ModTrade(player,6)));
        }
        //16号位
        {
            ItemStack itemStack = Items.EMERALD.getDefaultStack();
            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("购买绿宝石x8").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            itemStack.set(DataComponentTypes.LORE, LoreComponent.DEFAULT.with(Text.literal("花费铁x5").formatted(Formatting.GREEN).styled((style) -> style.withItalic(false))));
            gui.setSlot(16, new GuiElementBuilder(itemStack).setCount(8).setCallback((index, clickType, actionType) -> ModSetting.ModTrade(player,22)));
        }
        //19号位
        {
            ItemStack itemStack = Items.ANVIL.getDefaultStack();
            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("购买铁砧").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            itemStack.set(DataComponentTypes.LORE, LoreComponent.DEFAULT.with(Text.literal("花费绿宝石x48").formatted(Formatting.GREEN).styled((style) -> style.withItalic(false))));
            gui.setSlot(19, new GuiElementBuilder(itemStack).setCount(1).setCallback((index, clickType, actionType) -> ModSetting.ModTrade(player,10)));
        }
        //20号位
        {
            ItemStack itemStack = Items.EXPERIENCE_BOTTLE.getDefaultStack();
            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("购买附魔之瓶x32").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            itemStack.set(DataComponentTypes.LORE, LoreComponent.DEFAULT.with(Text.literal("花费绿宝石x8").formatted(Formatting.GREEN).styled((style) -> style.withItalic(false))));
            gui.setSlot(20, new GuiElementBuilder(itemStack).setCount(32).setCallback((index, clickType, actionType) -> ModSetting.ModTrade(player,12)));
        }
        //21号位
        {
            ItemStack itemStack = Items.NETHERITE_INGOT.getDefaultStack();
            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("购买下界合金锭x1").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            itemStack.set(DataComponentTypes.LORE, LoreComponent.DEFAULT.with(Text.literal("花费绿宝石x56").formatted(Formatting.GREEN).styled((style) -> style.withItalic(false))));
            gui.setSlot(21, new GuiElementBuilder(itemStack).setCount(1).setCallback((index, clickType, actionType) -> ModSetting.ModTrade(player,19)));
        }
        //28号位
        {
            Enchantment enchantment = player.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).get(Enchantments.POWER);
            ItemStack itemStack = Items.ENCHANTED_BOOK.getDefaultStack();
            itemStack.addEnchantment(RegistryEntry.of(enchantment),1);
            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("购买力量I").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            itemStack.set(DataComponentTypes.LORE, LoreComponent.DEFAULT.with(Text.literal("花费绿宝石x8").formatted(Formatting.GREEN).styled((style) -> style.withItalic(false))));
            gui.setSlot(28, new GuiElementBuilder(itemStack).setCount(1).setCallback((index, clickType, actionType) -> ModSetting.ModTrade(player,13)));
        }
        //29号位
        {
            Enchantment enchantment = player.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).get(Enchantments.SHARPNESS);
            ItemStack itemStack = Items.ENCHANTED_BOOK.getDefaultStack();
            itemStack.addEnchantment(RegistryEntry.of(enchantment),1);
            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("购买锋利I").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            itemStack.set(DataComponentTypes.LORE, LoreComponent.DEFAULT.with(Text.literal("花费绿宝石x8").formatted(Formatting.GREEN).styled((style) -> style.withItalic(false))));
            gui.setSlot(29, new GuiElementBuilder(itemStack).setCount(1).setCallback((index, clickType, actionType) -> ModSetting.ModTrade(player,14)));
        }
        //30号位
        {
            Enchantment enchantment = player.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).get(Enchantments.PROTECTION);
            ItemStack itemStack = Items.ENCHANTED_BOOK.getDefaultStack();
            itemStack.addEnchantment(RegistryEntry.of(enchantment),1);
            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("购买保护I").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            itemStack.set(DataComponentTypes.LORE, LoreComponent.DEFAULT.with(Text.literal("花费绿宝石x8").formatted(Formatting.GREEN).styled((style) -> style.withItalic(false))));
            gui.setSlot(30, new GuiElementBuilder(itemStack).setCount(1).setCallback((index, clickType, actionType) -> ModSetting.ModTrade(player,15)));
        }
        //37号位
        {
            Enchantment enchantment1 = player.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).get(Enchantments.EFFICIENCY);
            Enchantment enchantment2 = player.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).get(Enchantments.UNBREAKING);
            Enchantment enchantment3 = player.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).get(Enchantments.FORTUNE);
            ItemStack itemStack = Items.DIAMOND_SHOVEL.getDefaultStack();
            itemStack.addEnchantment(RegistryEntry.of(enchantment1),4);
            itemStack.addEnchantment(RegistryEntry.of(enchantment2),4);
            itemStack.addEnchantment(RegistryEntry.of(enchantment3),10);
            itemStack.set(DataComponentTypes.CUSTOM_NAME,Text.literal("购买逗猫棒").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            itemStack.set(DataComponentTypes.LORE, LoreComponent.DEFAULT.with(Text.literal("花费绿宝石x64").formatted(Formatting.GREEN).styled((style) -> style.withItalic(false))));
            gui.setSlot(37, new GuiElementBuilder(itemStack).setCount(1).setCallback((index, clickType, actionType) -> ModSetting.ModTrade(player,11)));
        }
        //38号位
        {
            Enchantment enchantment1 = player.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).get(Enchantments.UNBREAKING);
            Enchantment enchantment2 = player.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).get(Enchantments.PROTECTION);
            ItemStack itemStack = Items.DIAMOND_BOOTS.getDefaultStack();
            itemStack.addEnchantment(RegistryEntry.of(enchantment1),4);
            itemStack.addEnchantment(RegistryEntry.of(enchantment2),4);
            itemStack.set(DataComponentTypes.CUSTOM_NAME,Text.literal("购买足力健").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            List<AttributeModifiersComponent.Entry> list = new ArrayList<>(){{
                add(new AttributeModifiersComponent.Entry(EntityAttributes.GENERIC_MOVEMENT_SPEED,new EntityAttributeModifier(Identifier.of(Super_villager.MOD_ID,"base_speed"),0.8, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE), AttributeModifierSlot.FEET));
                add(new AttributeModifiersComponent.Entry(EntityAttributes.GENERIC_ARMOR,new EntityAttributeModifier(Identifier.of(Super_villager.MOD_ID,"base_armor"),4, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.FEET));
                add(new AttributeModifiersComponent.Entry(EntityAttributes.GENERIC_ARMOR_TOUGHNESS,new EntityAttributeModifier(Identifier.of(Super_villager.MOD_ID,"base_armor_toughness"),4, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.FEET));
                add(new AttributeModifiersComponent.Entry(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE,new EntityAttributeModifier(Identifier.of(Super_villager.MOD_ID,"base_armor_toughness"),0.1, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.FEET));
            }};
            itemStack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS,new AttributeModifiersComponent(list,true));
            itemStack.set(DataComponentTypes.LORE, LoreComponent.DEFAULT.with(Text.literal("花费绿宝石x64").formatted(Formatting.GREEN).styled((style) -> style.withItalic(false))));
            gui.setSlot(38, new GuiElementBuilder(itemStack).setCount(1).setCallback((index, clickType, actionType) -> ModSetting.ModTrade(player,20)));
        }
        //39号位
        {
            ItemStack itemStack = Items.ENCHANTED_GOLDEN_APPLE.getDefaultStack();
            Collection<? extends Text> texts = new ArrayList<>() {{
                add(Text.literal("购买茄子").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
                add(Text.literal("(这真的不是附魔金苹果)").styled((style) -> style.withStrikethrough(true)).formatted(Formatting.GRAY).styled((style) -> style.withItalic(false)));
            }};
            itemStack.set(DataComponentTypes.CUSTOM_NAME, Texts.join(texts, Text.of(" ")));
            itemStack.set(DataComponentTypes.LORE, LoreComponent.DEFAULT.with(Text.literal("花费绿宝石x16").formatted(Formatting.GREEN).styled((style) -> style.withItalic(false))));
            gui.setSlot(39, new GuiElementBuilder(itemStack).setCount(1).setCallback((index, clickType, actionType) -> ModSetting.ModTrade(player,21)));
        }
        //32号位
        {
            ItemStack itemStack = Items.APPLE.getDefaultStack();
            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("购买村民最大生命值+1").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            itemStack.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);
            gui.setSlot(32, new GuiElementBuilder(itemStack).setCount(1).setCallback((index, clickType, actionType) -> {
                ModSetting.ModTrade(player,7);
            }).addLoreLine(Text.literal("花费绿宝石x24").formatted(Formatting.GREEN).styled((style) -> style.withItalic(false)))
              .addLoreLine(Text.literal("当前值:"+ ModSetting.villager_health).formatted(Formatting.YELLOW).styled((style) -> style.withItalic(false)))
            );
        }
        //33号位
        {
            ItemStack itemStack = Items.IRON_CHESTPLATE.getDefaultStack();
            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("购买村民基础护甲+0.5").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            itemStack.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);
            List<AttributeModifiersComponent.Entry> list = new ArrayList<>(){{
                add(new AttributeModifiersComponent.Entry(EntityAttributes.GENERIC_ARMOR,new EntityAttributeModifier(Identifier.of(Super_villager.MOD_ID,"base_armor"),0, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.FEET));
            }};
            itemStack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS,new AttributeModifiersComponent(list,false));
            gui.setSlot(33, new GuiElementBuilder(itemStack).setCount(1).setCallback((index, clickType, actionType) -> {
                ModSetting.ModTrade(player,8);
            }).addLoreLine(Text.literal("花费绿宝石x24").formatted(Formatting.GREEN).styled((style) -> style.withItalic(false)))
              .addLoreLine(Text.literal("最大值:30").formatted(Formatting.YELLOW).styled((style) -> style.withItalic(false)))
              .addLoreLine(Text.literal("当前值:"+ ModSetting.villager_armor*0.5).formatted(Formatting.YELLOW).styled((style) -> style.withItalic(false)))
            );
        }
        //34号位
        {
            ItemStack itemStack = Items.SHIELD.getDefaultStack();
            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("购买村民基础护甲韧性+0.4").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            itemStack.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);
            gui.setSlot(34, new GuiElementBuilder(itemStack).setCount(1).setCallback((index, clickType, actionType) -> {
                ModSetting.ModTrade(player,9);
            }).addLoreLine(Text.literal("花费绿宝石x24").formatted(Formatting.GREEN).styled((style) -> style.withItalic(false)))
              .addLoreLine(Text.literal("最大值:20").formatted(Formatting.YELLOW).styled((style) -> style.withItalic(false)))
              .addLoreLine(Text.literal("当前值:"+ ModSetting.villager_armor_toughness*0.4).formatted(Formatting.YELLOW).styled((style) -> style.withItalic(false))));
        }
        //35号位
        {
            ItemStack itemStack = ModItem.SUPER_VILLAGER_SPAWN_EGG.getDefaultStack();
            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("←村民升级").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            gui.setSlot(35, new GuiElementBuilder(itemStack).setCount(1));
        }
        //41号位
        {
            ItemStack itemStack = Items.BEETROOT.getDefaultStack();
            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("购买生命提升(全体玩家)").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            itemStack.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);
            gui.setSlot(41, new GuiElementBuilder(itemStack).setCount(ModSetting.player_heath_boost+1).setCallback((index, clickType, actionType) -> {
                ModSetting.ModTrade(player,16);
            }).addLoreLine(Text.literal("花费绿宝石x32").formatted(Formatting.GREEN).styled((style) -> style.withItalic(false)))
              .addLoreLine(Text.literal("最大值:5").formatted(Formatting.YELLOW).styled((style) -> style.withItalic(false)))
              .addLoreLine(Text.literal("当前值:"+ ModSetting.player_heath_boost).formatted(Formatting.YELLOW).styled((style) -> style.withItalic(false)))
            );
        }
        //42号位
        {
            ItemStack itemStack = Items.REDSTONE.getDefaultStack();
            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("购买力量(全体玩家)").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            itemStack.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);
            gui.setSlot(42, new GuiElementBuilder(itemStack).setCount(ModSetting.player_power+1).setCallback((index, clickType, actionType) -> {
                ModSetting.ModTrade(player,17);
            }).addLoreLine(Text.literal("花费绿宝石x64").formatted(Formatting.GREEN).styled((style) -> style.withItalic(false)))
              .addLoreLine(Text.literal("最大值:3").formatted(Formatting.YELLOW).styled((style) -> style.withItalic(false)))
              .addLoreLine(Text.literal("当前值:"+ ModSetting.player_power).formatted(Formatting.YELLOW).styled((style) -> style.withItalic(false)))
            );
        }
        //43号位
        {
            ItemStack itemStack = Items.GOLD_INGOT.getDefaultStack();
            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("购买抗性提升(全体玩家)").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            itemStack.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);
            gui.setSlot(43, new GuiElementBuilder(itemStack).setCount(ModSetting.player_register+1).setCallback((index, clickType, actionType) -> {
                ModSetting.ModTrade(player,18);
            }).addLoreLine(Text.literal("花费绿宝石x64").formatted(Formatting.GREEN).styled((style) -> style.withItalic(false)))
              .addLoreLine(Text.literal("最大值:2").formatted(Formatting.YELLOW).styled((style) -> style.withItalic(false)))
              .addLoreLine(Text.literal("当前值:"+ ModSetting.player_register).formatted(Formatting.YELLOW).styled((style) -> style.withItalic(false)))
            );
        }
        //44号位
        {
            ItemStack itemStack = Items.PLAYER_HEAD.getDefaultStack();
            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("←团队升级").formatted(Formatting.AQUA).styled((style) -> style.withItalic(false)));
            gui.setSlot(44, new GuiElementBuilder(itemStack).setCount(1).setSkullOwner(new GameProfile(player.getUuid(), "you"), player.server));
        }
        gui.open();
    }

    public static @NotNull SimpleGui getSimpleGui(ServerPlayerEntity player) {
        Random random = new Random();
        SimpleGui gui = new SimpleGui(ScreenHandlerType.GENERIC_9X6, player, false) {
            @Override
            public void onOpen() {
                if(!ModSetting.tradingPlayer.contains(player))
                    ModSetting.tradingPlayer.add(player);
                super.onOpen();
            }

            @Override
            public void onClose() {
                ModSetting.tradingPlayer.remove(player);
                super.onClose();
            }

            @Override
            public boolean onClick(int index, ClickType type, SlotActionType action, GuiElementInterface element) {
                ClientPlayNetworking.send(new TradeC2SPayload(0));
                return super.onClick(index, type, action, element);
            }
        };
        gui.setTitle(Text.literal(getString(random.nextInt(11)).formatted(Formatting.GOLD)));
        gui.setAutoUpdate(true);
        return gui;
    }

    public static String getString(int i){
        if(i==1)
            return "早上好,中午好,以及晚上好";
        if(i==2)
            return "你给我绿宝石,我给你货物,不用操心太多";
        if(i==3)
            return "这不叫捡破烂,我只是拿了别人不要的宝贝";
        if(i==4)
            return "想知道我的故事？朋友,好故事很贵的";
        if(i==5)
            return "别担心,消费能促进这个世界的进步";
        if(i==6)
            return "别嫌贵,收集这些破烂很费劲的";
        if(i==7)
            return "有备无患,有备无患啊朋友";
        if(i==8)
            return "一点点垃圾，一点点恐怖的小玩意儿...";
        if(i==9)
            return "指纹留多了商品会掉价的，朋友。";
        return "感谢你的大力支持,朋友,让我们继续合作吧";
    }
}
