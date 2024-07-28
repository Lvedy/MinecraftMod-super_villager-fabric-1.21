package lvedy.super_villager.registry;

import lvedy.super_villager.Super_villager;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItem {
    public static final Item SUPER_VILLAGER_SPAWN_EGG = registerItem("super_villager_spawn_egg",new SpawnEggItem(ModEntity.SUPER_VILLAGER,0x9a6d58, 0x5b413a,new Item.Settings()));

    public static SpawnEggItem registerItem(String name, SpawnEggItem item){
        SpawnEggItem registerItem;
        registerItem = Registry.register(Registries.ITEM, Identifier.of(Super_villager.MOD_ID, name), item);
        return registerItem;
    }

    public static final ItemGroup test_ItemGroup = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(Super_villager.MOD_ID,"super_villager_item_group"), FabricItemGroup.builder().displayName(Text.translatable("itemgroup.super_villager")).
                    icon(()->new ItemStack(Items.CREEPER_HEAD)).entries((displayContext, entries) -> {
                        entries.add(ModItem.SUPER_VILLAGER_SPAWN_EGG);//从这里添加该选项卡所拥有的物品
                    }).build());

    public static void registry(){

    }
}
