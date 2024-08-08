package lvedy.super_villager.special.event;

import lvedy.super_villager.ModSetting;
import lvedy.super_villager.special.entity.custom.SuperVillagerEntity;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static lvedy.super_villager.ModSetting.Last;
import static lvedy.super_villager.ModSetting.level;

public class OverWorld implements ServerTickEvents.StartTick{
    public static int tick = 0;
    public static float a = 1f;
    public static float b = 1f;
    public static float c = 1f;
    private static final Predicate<Entity> PREDICATE = Entity::isAlive;
    public static List<ServerPlayerEntity> AllPlayer;

    @Override
    public void onStartTick(MinecraftServer server) {
        ModSetting.server = server;
        AllPlayer = server.getPlayerManager().getPlayerList();
        SetABC();
        GivePlayerEffect(server);
        if(ModSetting.b) {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                ModSetting.sendOffTitle(player,"危险等级:  "+ModSetting.level+"    当前波次:  "+ModSetting.level_now+"/"+ModSetting.level_time+"    距离下波还有:  "+ SuperVillagerEntity.tick/20+"s/"+ModSetting.mob_time/20+"s", Formatting.LIGHT_PURPLE);
            }
        }
    }

    public void SetABC(){
        if(Last != ModSetting.pian_xin){
            tick++;
            if((tick < 5) || (15 <= tick && tick < 25) || (35 <= tick && tick < 40)) {
                a += 0.05f;
                b -= 0.05f;
                c += 0.05f;
            }
            if((5 <= tick && tick < 15) || (25 <= tick && tick < 35)){
                a -= 0.05f;
                b += 0.05f;
                c -= 0.05f;
            }
            if(tick >= 40) {
                a = b = c = 1f;
                Last = ModSetting.pian_xin;
                tick = 0;
            }
        }
    }

    public static void GivePlayerEffect(MinecraftServer server){
        for(PlayerEntity player:server.getPlayerManager().getPlayerList()){
            if(ModSetting.player_heath_boost-1>=0)
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST,200,ModSetting.player_heath_boost-1));
            if(ModSetting.player_power-1>=0)
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH,200,ModSetting.player_power-1));
            if(ModSetting.player_register-1>=0)
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE,200,ModSetting.player_register-1));
        }
    }

    public static void SetArmor(World world, BlockPos blockPos){
        //僵尸
        Box box = new Box(blockPos.add(-1,-1,-1).toCenterPos(),blockPos.add(1,1,1).toCenterPos());
        List<ZombieEntity> zombies = world.getEntitiesByClass(ZombieEntity.class, box, PREDICATE);
        for(ZombieEntity zombieEntity:zombies){
            zombieEntity.equipStack(EquipmentSlot.HEAD, ModSetting.GetArmorByInt(EquipmentSlot.HEAD,(level+5)/5).getDefaultStack());
            zombieEntity.equipStack(EquipmentSlot.CHEST, ModSetting.GetArmorByInt(EquipmentSlot.CHEST,(level+2)/5).getDefaultStack());
            zombieEntity.equipStack(EquipmentSlot.LEGS, ModSetting.GetArmorByInt(EquipmentSlot.LEGS,(level+3)/5).getDefaultStack());
            zombieEntity.equipStack(EquipmentSlot.FEET, ModSetting.GetArmorByInt(EquipmentSlot.FEET,(level+4)/5).getDefaultStack());
            Objects.requireNonNull(zombieEntity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(20+((double) ModSetting.level /1.5));
        }
        //骷髅
        List<SkeletonEntity> skeletonEntities = world.getEntitiesByClass(SkeletonEntity.class, box, PREDICATE);
        for(SkeletonEntity skeletonEntity:skeletonEntities){
            skeletonEntity.equipStack(EquipmentSlot.HEAD, ModSetting.GetArmorByInt(EquipmentSlot.HEAD,(level+5)/5).getDefaultStack());
            skeletonEntity.equipStack(EquipmentSlot.CHEST, ModSetting.GetArmorByInt(EquipmentSlot.CHEST,(level+2)/5).getDefaultStack());
            skeletonEntity.equipStack(EquipmentSlot.LEGS, ModSetting.GetArmorByInt(EquipmentSlot.LEGS,(level+3)/5).getDefaultStack());
            skeletonEntity.equipStack(EquipmentSlot.FEET, ModSetting.GetArmorByInt(EquipmentSlot.FEET,(level+4)/5).getDefaultStack());
            Objects.requireNonNull(skeletonEntity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).setBaseValue(skeletonEntity.getAttributeBaseValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)+ (float) level /10);
        }
    }
}
