package lvedy.super_villager.mixin;

import lvedy.super_villager.ModSetting;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractSkeletonEntity.class)
public abstract class AbstractSkeletonEntityMixin extends HostileEntity {
    public int tick = 0;

    protected AbstractSkeletonEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(method = "tickMovement",at = @At("HEAD"))
    protected void InjectTickMovement(CallbackInfo ci){
        if(this.getClass().equals(WitherSkeletonEntity.class)){
            tick++;
            if (tick >= 400 && ModSetting.level>=10) {
                Box box = this.getBoundingBox().expand(1, 0, 1);
                for (BlockPos blockPos : BlockPos.iterate(MathHelper.floor(box.minX), MathHelper.floor(box.minY), MathHelper.floor(box.minZ), MathHelper.floor(box.maxX), MathHelper.floor(box.maxY), MathHelper.floor(box.maxZ))) {
                    if(this.getWorld().getBlockState(blockPos).getBlock() == Blocks.BEDROCK)
                        continue;
                    this.getWorld().breakBlock(blockPos, true);
                    tick = 0;
                }
            }
        }
    }
}
