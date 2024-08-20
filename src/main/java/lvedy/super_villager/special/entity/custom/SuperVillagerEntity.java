package lvedy.super_villager.special.entity.custom;

import lvedy.super_villager.ModSetting;
import lvedy.super_villager.special.event.OverWorld;
import lvedy.super_villager.special.networking.DeadC2SPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
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
    public int strafeTime = 10;
    public int strafeTime2 = 10;
    public int strafeTimes = 0;
    public int killDragonStage = 0;
    public int killDragonInstance = 5;
    public int elbowStrikeDragon = 15;
    public int elbowStrikeInstance = 5;
    public int pitchStrikeInstance = 5;
    public int rotateTick = 0;
    public int rotateTimes = 0;
    public int fireWorkTick = 0;
    public Boolean elbowFollowDragon = true;
    public Boolean dodge = false;
    public Boolean elbow = false;
    public Boolean rotate = false;
    public Boolean pitch = false;

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
        //闪避龙息
        if(dodge){
            List<DragonFireballEntity> list = this.getWorld().getEntitiesByClass(DragonFireballEntity.class, this.getBoundingBox().expand(10), PREDICATE);
            if(!list.isEmpty()){
                while(true) {
                    Vec3d vec3d = randomTeleport();
                    BlockPos blockPos = this.getWorld().getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BlockPos.ofFloored(this.getPos().add(vec3d)));
                    List<AreaEffectCloudEntity> list2 = this.getWorld().getEntitiesByClass(AreaEffectCloudEntity.class, Box.from(blockPos.toCenterPos()).expand(0.5), PREDICATE);
                    if(list2.isEmpty()) {
                        this.teleport(blockPos.getX(), blockPos.getY(), blockPos.getZ(), true);
                        break;
                    }
                }
            }
        }
        //准备肘击
        if(elbow){
            List<EnderDragonEntity> list = this.getWorld().getEntitiesByClass(EnderDragonEntity.class, this.getBoundingBox().expand(elbowStrikeInstance), PREDICATE);
            if(!list.isEmpty()){
                killDragonStage = -1;
                for(EnderDragonEntity dragon:list) {
                    elbowStrikeDragon--;
                    if(elbowFollowDragon) {
                        this.requestTeleport(dragon.getX() + 1, dragon.getY() + 3, dragon.getZ() + 1);
                        this.setVelocity(0, 0, 0);
                    }
                    if(elbowStrikeDragon<=0) {
                        elbowFollowDragon = false;
                        elbowStrikeInstance = 100;
                        if(dragon.getWorld().getBlockState(new BlockPos((int) dragon.getX(), (int) (dragon.getY()-2), (int) dragon.getZ())).getBlock()==Blocks.AIR){
                            dragon.setVelocity(0,0,0);
                            dragon.requestTeleport(dragon.getX(), dragon.getY()-1, dragon.getZ());
                        }
                    }
                    if(elbowStrikeDragon<=-15) {
                        this.setNoGravity(false);
                        if(dragon.getWorld().getBlockState(new BlockPos((int) dragon.getX(), (int) (dragon.getY()-3), (int) dragon.getZ())).getBlock()==Blocks.AIR) {
                            this.setVelocity(0, -3, 0);
                            if (this.getY() <= 45) {
                                Box box = this.getBoundingBox().expand(2.0).withMaxY(this.getY()-5);
                                box.withMinY(this.getY()-10);
                                for (BlockPos blockPos : BlockPos.iterate(MathHelper.floor(box.minX), MathHelper.floor(box.minY), MathHelper.floor(box.minZ), MathHelper.floor(box.maxX), MathHelper.floor(box.maxY), MathHelper.floor(box.maxZ))) {
                                    BlockState blockState = dragon.getWorld().getBlockState(blockPos);
                                    Block block = blockState.getBlock();
                                    if(block==Blocks.AIR)
                                        dragon.getWorld().setBlockState(blockPos, Blocks.OBSIDIAN.getDefaultState());
                                }
                            }
                        }
                        else{
                            killDragonStage = 2;
                            elbow = false;
                        }
                    }
                }
            }
        }
        //大风车
        if(rotate){
            List<EnderDragonEntity> list = this.getWorld().getEntitiesByClass(EnderDragonEntity.class, this.getBoundingBox().expand(5), PREDICATE);
            if(!list.isEmpty()){
                killDragonStage=-1;
                Set<PositionFlag> set = Set.of();
                this.setVelocity(0,0,0);
                this.teleport((ServerWorld) this.getWorld(), this.getX(), this.getY(), this.getZ(), set, rotateTick * 20, 0);
                for (EnderDragonEntity dragon : list) {
                    Vec3d vec3d = this.getPos();
                    Vec3d vec3d2 = this.getRotationVector();
                    Vec3d vec3d3 = vec3d2.normalize();
                    Vec3d vec3d4 = vec3d.add(vec3d3.multiply(8));
                    dragon.teleport((ServerWorld) this.getWorld(), vec3d4.x, vec3d4.y, vec3d4.z, set, rotateTick * 20, 0);
                    if(rotateTimes>=8) {
                        rotate = false;
                        killDragonStage=3;
                    }
                }
                if(rotateTick==18){
                    rotateTick=0;
                    rotateTimes++;
                }
                rotateTick++;
            }
        }
        //末影龙上投
        if(pitch){
            List<EnderDragonEntity> list = this.getWorld().getEntitiesByClass(EnderDragonEntity.class, this.getBoundingBox().expand(pitchStrikeInstance), PREDICATE);
            List<EnderDragonEntity> list2 = this.getWorld().getEntitiesByClass(EnderDragonEntity.class, this.getBoundingBox().expand(100), PREDICATE);
            if(!list.isEmpty()){
                pitchStrikeInstance=100;
                for(EnderDragonEntity dragon:list){
                    Vec3d vec3d1 = dragon.getPos();
                    Vec3d vec3d2 = new Vec3d(0,this.getY()+40,0).subtract(vec3d1);
                    Vec3d vec3d3 = vec3d2.normalize();
                    dragon.setVelocity(0,0,0);
                    dragon.requestTeleport(dragon.getX()+vec3d3.x*3,dragon.getY()+vec3d3.y*3,dragon.getZ()+vec3d3.z*3);
                    if(fireWorkTick>=90){
                        dragon.setHealth(0);
                        killDragonStage = 4;
                        List<TntEntity> list3 = this.getWorld().getEntitiesByClass(TntEntity.class, this.getBoundingBox().expand(pitchStrikeInstance), PREDICATE);
                        for(TntEntity tnt:list3){
                            tnt.setVelocity(random.nextInt(10)*randomAdd(0.1),tnt.getVelocity().y,random.nextInt(10)*randomAdd(0.1));
                            tnt.setFuse(random.nextInt(80)+30);
                        }
                    }
                }
            }
            else{
                for (EnderDragonEntity dragon:list2){
                    Vec3d vec3d5 = dragon.getPos();
                    BlockPos blockPos = this.getWorld().getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(0,0,0));
                    Vec3d vec3d6 = new Vec3d(0,blockPos.getY(),0).subtract(vec3d5);
                    Vec3d vec3d7 = vec3d6.normalize();
                    dragon.requestTeleport(dragon.getX()+vec3d7.x*2,dragon.getY()+vec3d7.y*2,dragon.getZ()+vec3d7.z*2);
                }
            }
            if(fireWorkTick==70){
                for(int i = 0;i<=50;i++) {
                    BlockPos blockPos = new BlockPos(0, (int) (this.getY() + 70), 0);
                    EntityType.TNT.spawn((ServerWorld) this.getWorld(), blockPos, SpawnReason.TRIGGERED);
                    List<TntEntity> list3 = this.getWorld().getEntitiesByClass(TntEntity.class, this.getBoundingBox().expand(pitchStrikeInstance), PREDICATE);
                    for(TntEntity tnt:list3){
                        tnt.setVelocity(random.nextInt(10)*randomAdd(0.1),tnt.getVelocity().y,random.nextInt(10)*randomAdd(0.1));
                        tnt.setFuse(random.nextInt(8)+3);
                    }
                }
            }
            if(fireWorkTick==75){
                for(int i = 0;i<=100;i++) {
                    BlockPos blockPos = new BlockPos(0, (int) (this.getY() + 50), 0);
                    EntityType.TNT.spawn((ServerWorld) this.getWorld(), blockPos, SpawnReason.TRIGGERED);
                }
                List<TntEntity> list3 = this.getWorld().getEntitiesByClass(TntEntity.class, this.getBoundingBox().expand(pitchStrikeInstance), PREDICATE);
                for(TntEntity tnt:list3){
                    tnt.setVelocity(random.nextInt(10)*randomAdd(0.1),tnt.getVelocity().y,random.nextInt(10)*randomAdd(0.1));
                    tnt.setFuse(random.nextInt(8)+3);
                }
            }
            if(fireWorkTick==80){
                for(int i = 0;i<=500;i++) {
                    BlockPos blockPos = new BlockPos(random.nextInt(2), (int) (this.getY() + 30), random.nextInt(2));
                    EntityType.TNT.spawn((ServerWorld) this.getWorld(), blockPos, SpawnReason.TRIGGERED);
                }
            }
        }
        //末地杀龙控制
        if(this.getWorld().getRegistryKey() == World.END){
            List<? extends EnderDragonEntity> list = Objects.requireNonNull(Objects.requireNonNull(getServer()).getWorld(World.END)).getAliveEnderDragons();
            for(EnderDragonEntity enderDragonEntity:list) {
                if (enderDragonEntity.getFight() != null && enderDragonEntity.getFight().getAliveEndCrystals() == 0 && Math.abs(this.getBlockPos().getX()) <= killDragonInstance && Math.abs(this.getBlockPos().getZ()) <= killDragonInstance && enderDragonEntity.isAlive()) {
                    ModSetting.pian_xin = true;
                    this.being_picked_up=false;
                    this.picked_player=null;
                    this.setNoGravity(false);
                }
                if(ModSetting.Last) {
                    if(killDragonStage == 0) {
                        killDragonInstance = 100;
                        dodge = true;
                        strafeVillager(this, enderDragonEntity);
                        if(strafeTimes>=10)
                            killDragonStage = 1;
                    }
                    if(killDragonStage == 1){
                        this.setNoGravity(true);
                        Vec3d vec3d = this.getPos();
                        Vec3d vec3d2 = enderDragonEntity.getPos().subtract(vec3d);
                        Vec3d vec3d3 = vec3d2.normalize().multiply(3);
                        Vec3d vec3d4 = new Vec3d(this.getX()+vec3d3.x,this.getY()+vec3d3.y,this.getZ()+vec3d3.z);
                        this.requestTeleport(vec3d4.x,vec3d4.y,vec3d4.z);
                        elbow = true;
                    }
                    if(killDragonStage==2){
                        Vec3d vec3d = this.getPos();
                        Vec3d vec3d2 = enderDragonEntity.getPos().subtract(vec3d);
                        Vec3d vec3d3 = vec3d2.normalize().multiply(2);
                        Vec3d vec3d4 = new Vec3d(this.getX()+vec3d3.x,this.getY()+vec3d3.y,this.getZ()+vec3d3.z);
                        this.requestTeleport(vec3d4.x,vec3d4.y,vec3d4.z);
                        rotate = true;
                    }
                    if(killDragonStage==3){
                        BlockPos blockPos = this.getWorld().getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(0,0,0));
                        this.teleport(0,blockPos.getY(),0,true);
                        this.setVelocity(0,0,0);
                        fireWorkTick++;
                        ((ServerWorld)this.getWorld()).spawnParticles(ParticleTypes.FIREWORK,0,this.getY()+fireWorkTick*0.8,0,4,0,0,0,0);
                        pitch = true;
                    }
                }
            }
            //发送成就信息
            if(killDragonStage == 4) {
                for (PlayerEntity player : this.getWorld().getPlayers()) {
                    Text text = Text.of("解放末地");
                    Text text2 = Texts.setStyleIfAbsent(text.copy(), Style.EMPTY.withColor(Formatting.DARK_PURPLE)).append("\n").append("恭喜你成功护送麦块队长到达末地，击败末影龙");
                    Text text3 = text.copy().styled((style) -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, text2)));
                    Collection<? extends Text> texts = new ArrayList<>() {{
                        add(Text.literal("麦块队长取得了成就:"));
                        add(Texts.bracketed(text3).formatted(Formatting.DARK_PURPLE));
                    }};
                    if(!this.getWorld().isClient)
                        player.sendMessage(Texts.join(texts, Text.of(" ")));
                }
                killDragonStage = 5;
            }
            if(killDragonStage == 3 || killDragonStage == 5){
                BlockPos blockPos = this.getWorld().getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(0,0,0));
                this.teleport(0,blockPos.getY(),0,false);
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
        if(dodge)
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

    public void strafeVillager(SuperVillagerEntity villager,EnderDragonEntity dragon){
        strafeTime--;
        if (strafeTime <= 0) {
            Vec3d vec3d3 = dragon.getRotationVec(1.0f);
            double l = dragon.head.getX() - vec3d3.x;
            double m = dragon.head.getBodyY(0.5) + 0.5;
            double n = dragon.head.getZ() - vec3d3.z;
            double o = villager.getX() - l;
            double p = villager.getBodyY(0.5) - m;
            double q = villager.getZ() - n;
            Vec3d vec3d = new Vec3d(o,p,q);
            DragonFireballEntity dragonFireballEntity = new DragonFireballEntity(dragon.getWorld(), dragon, vec3d);
            dragonFireballEntity.refreshPositionAndAngles(l, m, n, 0.0f, 0.0f);
            dragon.getWorld().spawnEntity(dragonFireballEntity);
            strafeTime = strafeTime2;
            strafeTimes++;
        }
    }

    public Vec3d randomTeleport(){
        Random random = new Random();
        int x,y;
        if(random.nextInt(2) == 0)
            x = 4 + random.nextInt(3);
        else
            x = - 4 - random.nextInt(3);
        if(random.nextInt(2) == 0)
            y = 4 + random.nextInt(3);
        else
            y = - 4 - random.nextInt(3);
        return new Vec3d(x,0,y);
    }

    public double randomAdd(double a){
        int b = random.nextInt(2);
        if(b==0)
            return a;
        else
            return -a;
    }
}
