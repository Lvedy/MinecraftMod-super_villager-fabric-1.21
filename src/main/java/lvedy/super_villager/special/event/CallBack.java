package lvedy.super_villager.special.event;

import lvedy.super_villager.ModSetting;
import lvedy.super_villager.special.entity.custom.SuperVillagerEntity;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.TypedActionResult;

import java.util.List;
import java.util.function.Predicate;

public class CallBack {
    private static final Predicate<Entity> IS_NOT_SELF = entity -> entity.isAlive() && !(entity instanceof PlayerEntity);

    public static void registry(){
        UseEntityCallback.EVENT.register((player, world, hand, pos, direction) -> {
            if (pos instanceof SuperVillagerEntity && !((SuperVillagerEntity) pos).being_picked_up && player.isSneaking()) {
                ((SuperVillagerEntity) pos).being_picked_up = true;
                ((SuperVillagerEntity) pos).picked_player = player;
                return ActionResult.SUCCESS;
            }
            //喂食村民
            if(pos instanceof SuperVillagerEntity && !player.isSneaking() && player.getMainHandStack().getComponents().contains(DataComponentTypes.FOOD)){
                if(ModSetting.c)
                    ModSetting.getExEffect(((SuperVillagerEntity) pos), player.getMainHandStack());
                ((SuperVillagerEntity)pos).addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION,400,0));
                if(player.getWorld().isClient)
                    player.getMainHandStack().setCount(player.getMainHandStack().getCount()-1);
            }
            if (pos instanceof SuperVillagerEntity && ((SuperVillagerEntity) pos).being_picked_up && player.isSneaking()) {
                if(((SuperVillagerEntity) pos).picked_player == player) {
                    ((SuperVillagerEntity) pos).being_picked_up = false;
                    ((SuperVillagerEntity) pos).picked_player = null;
                    pos.setNoGravity(false);
                }
                else{
                    ((SuperVillagerEntity) pos).being_picked_up = true;
                    ((SuperVillagerEntity) pos).picked_player = player;
                }
                return ActionResult.SUCCESS;
            }
            //取消判定
            List<SuperVillagerEntity> list = player.getWorld().getEntitiesByClass(SuperVillagerEntity.class, player.getBoundingBox().expand(2.0), IS_NOT_SELF);
            for(SuperVillagerEntity target:list){
                if(target.picked_player == player)
                    return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            List<SuperVillagerEntity> list = player.getWorld().getEntitiesByClass(SuperVillagerEntity.class, player.getBoundingBox().expand(2.0), IS_NOT_SELF);
            for(SuperVillagerEntity target:list){
                if(target.picked_player == player){
                    if(player.isSneaking()) {
                        target.being_picked_up = false;
                        target.picked_player = null;
                        target.setNoGravity(false);
                    }
                    else
                        return ActionResult.FAIL;
                }
            }
            return ActionResult.PASS;
        });

        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack itemStack = player.getStackInHand(hand);
            List<SuperVillagerEntity> list = player.getWorld().getEntitiesByClass(SuperVillagerEntity.class, player.getBoundingBox().expand(2.0), IS_NOT_SELF);
            for(SuperVillagerEntity target:list){
                if(target.picked_player == player)
                    return TypedActionResult.fail(itemStack);
            }
            return TypedActionResult.pass(itemStack);
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) ->{
            handler.player.sendMessageToClient(Text.literal("感谢下载本模组并游玩  当前版本beta-0.0.4\n感谢各位老板的支持!").formatted(Formatting.GOLD), false);
            if(server.getPlayerManager().getPlayerList().isEmpty()){
                ModSetting.resetSetting();
            }
        });
    }
}
