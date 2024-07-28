package lvedy.super_villager.registry;

import lvedy.super_villager.Super_villager;
import lvedy.super_villager.special.entity.client.SuperVillagerRender;
import lvedy.super_villager.special.entity.custom.SuperVillagerEntity;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntity {
    public static final EntityType<SuperVillagerEntity> SUPER_VILLAGER = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(Super_villager.MOD_ID,"super_villager"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE,SuperVillagerEntity::new).
                    dimensions(EntityDimensions.fixed(1.0f,3.0f)).build());

    public static void registry(){
        FabricDefaultAttributeRegistry.register(ModEntity.SUPER_VILLAGER,SuperVillagerEntity.setAttributes());
        EntityRendererRegistry.register(ModEntity.SUPER_VILLAGER, SuperVillagerRender::new);
    }
}
