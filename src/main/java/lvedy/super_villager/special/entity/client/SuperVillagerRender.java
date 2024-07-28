package lvedy.super_villager.special.entity.client;

import lvedy.super_villager.ModSetting;
import lvedy.super_villager.Super_villager;
import lvedy.super_villager.special.entity.custom.SuperVillagerEntity;
import lvedy.super_villager.special.event.OverWorld;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SuperVillagerRender extends GeoEntityRenderer<SuperVillagerEntity> {
    public SuperVillagerRender(EntityRendererFactory.Context renderManager) {
        super(renderManager, new SuperVillagerModel());
    }
    @Override
    public Identifier getTextureLocation(SuperVillagerEntity animatable) {
        if(!ModSetting.Last) {
            if(OverWorld.tick % 6 >= 3)
                return Identifier.of(Super_villager.MOD_ID, "textures/entity/white.png");
            else
                return Identifier.of(Super_villager.MOD_ID, "textures/entity/small_villager.png");
        }
        return Identifier.of(Super_villager.MOD_ID,"textures/entity/super.png");
    }

    @Override
    public void render(SuperVillagerEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        poseStack.scale(OverWorld.a,OverWorld.b,OverWorld.c);
        //poseStack.scale(3f,0.5f,3f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
