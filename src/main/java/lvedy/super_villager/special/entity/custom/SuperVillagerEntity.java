package lvedy.super_villager.special.entity.custom;

import lvedy.super_villager.ModSetting;
import lvedy.super_villager.special.event.OverWorld;
import lvedy.super_villager.special.networking.DeadC2SPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;

import java.util.*;
import java.util.function.Predicate;

public class SuperVillagerEntity extends PathAwareEntity implements GeoEntity {
    private AnimatableInstanceCache cache =new SingletonAnimatableInstanceCache(this);
    private final ServerBossBar bossBar = (ServerBossBar)new ServerBossBar(this.getDisplayName(), BossBar.Color.YELLOW, BossBar.Style.PROGRESS).setDarkenSky(false);
    public boolean being_picked_up = false;
    public PlayerEntity picked_player = null;
    public static int tick = 0;
    private static final Predicate<Entity> PREDICATE = Entity::isAlive;
    public boolean send = false;

    public SuperVillagerEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (this.hasCustomName()) {
            this.bossBar.setName(this.getDisplayName());
        }
    }

    @Override
    public void setCustomName(@Nullable Text name) {
        super.setCustomName(name);
        this.bossBar.setName(this.getDisplayName());
    }

    @Override
    protected void mobTick() {
        if(ModSetting.start)
            tick++;
        //村民属性设置
        Objects.requireNonNull(this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(20+ModSetting.villager_health);
        Objects.requireNonNull(this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR)).setBaseValue(ModSetting.villager_armor*0.5);
        Objects.requireNonNull(this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS)).setBaseValue(ModSetting.villager_armor*0.4);
        //血条设置
        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
        //加载
        this.setPersistent();
        //村民被抱起判断
        if(picked_player != null){
            Vec3d vec3d = picked_player.getPos();
            Vec3d vec3d2 = picked_player.getRotationVector();
            Vec3d vec3d3 = vec3d2.normalize();
            Vec3d vec3d4 = vec3d.add(vec3d3.multiply(1.5));
            Set<PositionFlag> set = Set.of();
            this.teleport(Objects.requireNonNull(picked_player.getServer()).getWorld(picked_player.getWorld().getRegistryKey()),vec3d4.x,picked_player.getY()+1,vec3d4.z,set,picked_player.headYaw,0);
            this.setNoGravity(true);
            picked_player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,20,0));
        }
        //刷怪计时器计时结束时触发事件
        if(ModSetting.start && tick>=ModSetting.mob_time){
            Random random = new Random();
            BlockPos randomBlockpos = ModSetting.getRandomBlockPos(this.getWorld(),this.getBlockPos().add(-ModSetting.super_villager_mob_r,-5,-ModSetting.super_villager_mob_r),this.getBlockPos());
            //计算危险等级
            if(ModSetting.level_now==ModSetting.level_time && ModSetting.b) {
                ModSetting.level++;
                ModSetting.level_now = 0;
                for(ServerPlayerEntity player:ModSetting.server.getPlayerManager().getPlayerList()){
                    if(!ModSetting.GetStringByLevel().equals(" "))
                        player.sendMessage(Text.literal(ModSetting.GetStringByLevel()).formatted(Formatting.RED));
                }
            }
            //放置怪潮并设置装备
            for(int i=0; i<ModSetting.mob_number+ModSetting.GetLevelNumber() ; i++) {
                if(ModSetting.b)
                    ModSetting.setOverWorldEntities(this.getWorld());
                EntityType<? extends Entity> entityType = ModSetting.overWorldEntities.get(random.nextInt(ModSetting.overWorldEntities.size()));
                entityType.spawn((ServerWorld)this.getWorld(),randomBlockpos, SpawnReason.TRIGGERED);
                if(entityType == EntityType.CREEPER && ModSetting.level>=10 && random.nextInt(10)==1) {
                    EntityType.LIGHTNING_BOLT.spawn((ServerWorld)this.getWorld(),randomBlockpos, SpawnReason.TRIGGERED);
                }
                OverWorld.SetArmor(this.getWorld(),randomBlockpos);
                randomBlockpos = ModSetting.getRandomBlockPos(this.getWorld(),randomBlockpos,this.getBlockPos());
            }
            //怪物目标设置
            List<MobEntity> mobEntities = this.getWorld().getEntitiesByClass(MobEntity.class, this.getBoundingBox().expand(ModSetting.super_villager_mob_r), PREDICATE);
            for(MobEntity mobEntity:mobEntities){
                //僵尸控制
                if(mobEntity instanceof ZombieEntity && ModSetting.level>=7)
                    mobEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 3000000, 0));
                //骷髅控制
                if(mobEntity instanceof SkeletonEntity && !ModSetting.a)
                    break;
                if(ModSetting.level>=20)
                    mobEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 3000000, 0));
                if(ModSetting.level>=30)
                    mobEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 3000000, 0));
                if(mobEntity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE) != null)
                    mobEntity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(mobEntity.getAttributeBaseValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)+((double) ModSetting.level /4));
                mobEntity.canTarget(this);
                mobEntity.setTarget(this);
            }
            if(ModSetting.b)
                ModSetting.level_now++;
            tick = 0;
        }
        //末地杀龙控制
        if(this.getWorld().getRegistryKey() == World.END){
            List<? extends EnderDragonEntity> list = Objects.requireNonNull(Objects.requireNonNull(getServer()).getWorld(World.END)).getAliveEnderDragons();
            for(EnderDragonEntity enderDragonEntity:list) {
                if (enderDragonEntity.getFight() != null && enderDragonEntity.getFight().getAliveEndCrystals() == 0 && Math.abs(this.getBlockPos().getX()) <= 5 && Math.abs(this.getBlockPos().getZ()) <= 5 && enderDragonEntity.isAlive()) {
                    ModSetting.pian_xin = true;
                    if(ModSetting.Last) {
                        enderDragonEntity.setHealth(0f);
                        //发送成就信息
                        for (PlayerEntity player : this.getWorld().getPlayers()) {
                            Text text = Text.of("解放末地");
                            Text text2 = Texts.setStyleIfAbsent(text.copy(), Style.EMPTY.withColor(Formatting.DARK_PURPLE)).append("\n").append("恭喜你成功护送麦块队长到达末地，击败末影龙");
                            Text text3 = text.copy().styled((style) -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, text2)));
                            Collection<? extends Text> texts = new ArrayList<>() {{
                                add(Text.literal("麦块队长取得了成就:"));
                                add(Texts.bracketed(text3).formatted(Formatting.DARK_PURPLE));
                            }};
                            player.sendMessage(Texts.join(texts, Text.of(" ")));
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void pushAway(Entity entity) {
        if(picked_player != null && entity == picked_player && ModSetting.e)
            return;
        super.pushAway(entity);
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        //死亡时计算玩家失败
        if(!send) {
            ClientPlayNetworking.send(new DeadC2SPayload());
            ModSetting.start = false;
            send = true;
        }
        super.onDeath(damageSource);
    }

    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        this.bossBar.addPlayer(player);
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
    }

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 0.75f, 1));
        this.goalSelector.add(8, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder setAttributes(){
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH,20)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if(source.isOf(DamageTypes.FALL) || source.isOf(DamageTypes.IN_WALL) || source.getSource() instanceof PlayerEntity)
            return false;
        return super.damage(source, amount);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this,"controller",0,this::predicate));
    }

    private PlayState predicate(AnimationState<SuperVillagerEntity> superVillagerEntityAnimationState) {
/*        if(this.isSummon()){
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("summon", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }
        if(this.isAttacking1()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("attack1", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }
        if(this.isAttacking2()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("attack2", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }
        if(tAnimationState.isMoving() && !this.isAttacking2()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("move", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("rest", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;*/
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
