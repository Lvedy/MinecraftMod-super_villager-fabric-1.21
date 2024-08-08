package lvedy.super_villager.special.entity.client;

import lvedy.super_villager.ModSetting;
import lvedy.super_villager.Super_villager;
import lvedy.super_villager.special.entity.custom.SuperVillagerEntity;
import lvedy.super_villager.special.event.OverWorld;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;

public class SuperVillagerModel extends GeoModel<SuperVillagerEntity> {
    @Override
    public Identifier getModelResource(SuperVillagerEntity animatable) {
        if(!ModSetting.Last)
            return Identifier.of(Super_villager.MOD_ID,"geo/small_villager.geo.json");
        return Identifier.of(Super_villager.MOD_ID,"geo/super.geo.json");
    }

    @Override
    public Identifier getTextureResource(SuperVillagerEntity animatable) {
        if(!ModSetting.Last) {
            if (OverWorld.tick % 6 >= 3)
                return Identifier.of(Super_villager.MOD_ID, "textures/entity/white.png");
            else
                return Identifier.of(Super_villager.MOD_ID, "textures/entity/small_villager.png");
        }
        return Identifier.of(Super_villager.MOD_ID,"textures/entity/super.png");
    }

    @Override
    public Identifier getAnimationResource(SuperVillagerEntity animatable) {
        if(!ModSetting.Last)
            return Identifier.of(Super_villager.MOD_ID,"animations/small_villager.animation.json");
        return Identifier.of(Super_villager.MOD_ID,"animations/super.animation.json");
    }

    @Override
    public void setCustomAnimations(SuperVillagerEntity animatable, long instanceId, AnimationState<SuperVillagerEntity> animationState) {
        GeoBone head = getAnimationProcessor().getBone("head");
    }
}
