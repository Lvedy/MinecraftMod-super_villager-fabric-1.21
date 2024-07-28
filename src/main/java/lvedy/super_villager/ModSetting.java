package lvedy.super_villager;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lvedy.super_villager.special.GUI.TradeScreen;
import lvedy.super_villager.special.networking.SettingC2Spayload_2;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.OverlayMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleFadeS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class ModSetting {
    public static boolean look_screen = false;
    public static boolean open_screen = false;
    public static boolean look_trade_screen = false;
    public static boolean open_trade_screen = false;
    public static boolean Last = false;
    public static boolean pian_xin = false;
    public static MinecraftServer server;
    public static List<EntityType<? extends Entity>> overWorldEntities = new ArrayList<>(){{
        add(EntityType.ZOMBIE);
        add(EntityType.SKELETON);
    }};

    public static int level = 0;    //危险等级
    public static int level_time = 2;   //多少波次提升一次危险等级
    public static int level_now = 0;   //当前为第几波次
    public static boolean start = false;

    public static int villager_health = 0;
    public static int villager_armor = 0;
    public static int villager_armor_toughness = 0;
    public static int player_heath_boost = 0;
    public static int player_power = 0;
    public static int player_register = 0;

    public static boolean a = false;                //骷髅是主动否射击村民
    public static boolean b = false;                //怪潮指令是否随时间推移而变高
    public static boolean c = false;                //村民是否有特殊恢复效果
    public static boolean d = false;                //是否启用村民交易
    public static int super_villager_safe_r = 9;    //村民安全半径
    public static int super_villager_mob_r = 20;    //村民刷怪半径
    public static int mob_time = 200; //600;        //多久刷一次怪
    public static int mob_number = 2;               //每波的基础刷怪数
    public static int regeneration_level = 0;       //村民的生命恢复等级
    public static int regeneration_time = 100;      //村民的生命恢复时间
    public static boolean e = false;                //是否抱起时关闭村民碰撞箱

    public static int[] getSettingIntArray(){
        int[] int_s = new int[100];
        int_s[0] = a ? 1 : 0;
        int_s[1] = b ? 1 : 0;
        int_s[2] = c ? 1 : 0;
        int_s[3] = d ? 1 : 0;
        int_s[4] = super_villager_safe_r;
        int_s[5] = super_villager_mob_r;
        int_s[6] = mob_time;
        int_s[7] = mob_number;
        int_s[8] = regeneration_level;
        int_s[9] = regeneration_time;
        int_s[10] = level_time;
        int_s[11] = villager_health;
        int_s[12] = villager_armor;
        int_s[13] = villager_armor_toughness;
        int_s[14] = player_heath_boost;
        int_s[15] = player_power;
        int_s[16] = player_register;
        int_s[17] = e ? 1 : 0;
        for(int i = 18;i<100;i++){
            int_s[i] = 0;
        }
        return int_s;
    }

    public static void setSetting(int[] int_s){
        a = int_s[0] == 1;
        b = int_s[1] == 1;
        c = int_s[2] == 1;
        d = int_s[3] == 1;
        super_villager_safe_r = int_s[4];
        super_villager_mob_r = int_s[5];
        mob_time = int_s[6];
        mob_number = int_s[7];
        regeneration_level = int_s[8];
        regeneration_time = int_s[9];
        level_time = int_s[10];
        villager_health = int_s[11];
        villager_armor = int_s[12];
        villager_armor_toughness = int_s[13];
        player_heath_boost = int_s[14];
        player_power = int_s[15];
        player_register = int_s[16];
        e = int_s[17] == 1;
    }

    //获取可以放置怪潮的位置
    public static BlockPos getRandomBlockPos(World world, BlockPos record, BlockPos blockPos){
        Random random = new Random();
        int x = record.getX();
        int y = record.getY();
        int z = record.getZ();
        int g = 0;
        do {
            BlockPos blockPos1 = new BlockPos(x, y, z);
            if ((blockPos.getSquaredDistance(blockPos1) > MathHelper.square(super_villager_safe_r) && getBlockSafe(world, blockPos1) && (random.nextInt(10) == 1 || g == 200)))
                return blockPos1;
            if (!getBlockSafe(world, blockPos1))
                g++;
            if (x == blockPos.getX() + super_villager_mob_r) {
                x = blockPos.getX() - super_villager_mob_r;
                if (z == blockPos.getZ() + super_villager_mob_r) {
                    z = blockPos.getZ() - super_villager_mob_r;
                    if(y>=blockPos.getY()-5)
                        y++;
                    else
                        y--;
                    if(y>298)
                        y = blockPos.getY()-7;
                    //System.out.println(y);
                } else
                    z++;
            } else
                x++;
            //System.out.println(blockPos1.toString() + getBlockSafe(world, blockPos1));
        } while (y > -65);
        return new BlockPos(0,-70,0);
    }

    public static boolean getBlockSafe(World world,BlockPos blockPos){
        BlockState blockState = world.getBlockState(blockPos.add(0,-1,0));
        Block block = blockState.getBlock();
        if(block == Blocks.LAVA || block == Blocks.AIR)
            return false;
        for(int i=0;i<=2;i++){
            BlockState blockState1 = world.getBlockState(blockPos.add(0,i,0));
            Block block1 = blockState1.getBlock();
            if(block1 != Blocks.AIR && block1 != Blocks.WATER){
                return false;
            }
        }
        return true;
    }

    public static void sendTitle(ServerPlayerEntity player, String string, Formatting formatting){
        Function<Text, Packet<?>> constructor = TitleS2CPacket::new;
        TitleFadeS2CPacket titleFadeS2CPacket = new TitleFadeS2CPacket(10, 20, 10);
        try {
            player.networkHandler.sendPacket(titleFadeS2CPacket);
            player.networkHandler.sendPacket(constructor.apply(Texts.parse(null, Text.literal(string).formatted(formatting), player, 0)));
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendOffTitle(ServerPlayerEntity player, String string, Formatting formatting){
        OverlayMessageS2CPacket overlayMessageS2CPacket = new OverlayMessageS2CPacket(Text.literal(string).formatted(formatting));
        player.networkHandler.sendPacket(overlayMessageS2CPacket);
    }

    public static void resetSetting() {
        start = false;
        a = false;
        b = false;
        c = false;
        d = false;
        super_villager_safe_r = 9;  //村民安全距离
        super_villager_mob_r = 20;  //刷怪距离
        mob_time = 800;             //多少秒刷一波怪
        mob_number = 2;             //每波怪刷多少
        level = 0;                  //危险等级
        level_now = 0;              //当前危险等级的第n波
        level_time = 8;             //提升一次危险等级要多少波
    }

    public static void resetTrade() {
        villager_health = 0;
        villager_armor = 0;
        villager_armor_toughness = 0;
        player_heath_boost = 0;
        player_power = 0;
        player_register = 0;
    }

    public static void setOverWorldEntities(World world){
        overWorldEntities = new ArrayList<>(){{
            if(ModSetting.b) {
                for(int i=0;i<4;i++)
                    add(EntityType.ZOMBIE);
                if(level>=20)
                    for(int i=0;i<8;i++)
                        add(EntityType.SKELETON);
                else
                    for(int i=0;i<4;i++)
                        add(EntityType.SKELETON);
                if(level >= 3)
                    if(level<=25)
                        for(int i=0;i<4;i++)
                            add(EntityType.SPIDER);
                    else
                        for(int i=0;i<4;i++)
                            add(EntityType.CAVE_SPIDER);
                if(level >= 5)
                    if(world.getRegistryKey() != World.NETHER)
                        for(int i=0;i<2;i++)
                            add(EntityType.CREEPER);
                    else
                        for(int i=0;i<2;i++)
                            add(EntityType.WITHER_SKELETON);
                if(level>=15)
                    for(int i=0;i<2;i++)
                        add(EntityType.WITCH);
            }
        }};
    }

    public static int GetLevelNumber(){
        if(b) {
            if (level <= 10)
                return level;
            if (level <= 20) {
                return (level - 10) / 2 + 10;
            }
            if (level <= 30) {
                return (level - 20) / 3 + 15;
            }
            return (level - 30) / 5 + 18;
        }
        return 0;
    }

    public static String GetStringByLevel(){
        if(level == 3)
            return "蜘蛛会被刷出";
        if(level == 5)
           return "苦力怕会被刷出/在地狱时被替换为凋零骷髅";
        if(level == 7)
            return "僵尸获得生命恢复I";
        if(level == 10)
            return "苦力怕有概率生成闪电苦力怕\n 在地狱时被替换为凋零骷髅会破坏方块(有冷却)";
        if(level == 15)
            return "女巫会被刷出";
        if(level == 20)
            return "骷髅刷新占比变高,所有怪获得抗性提升I";
        if(level == 25)
            return "蜘蛛会被替换为洞穴蜘蛛";
        if(level == 30)
            return "所有怪获得力量I";
        return " ";
    }

    public static void getExEffect(LivingEntity livingEntity, ItemStack itemStack){
        if(itemStack.getItem() == Items.GOLDEN_APPLE)
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 1200, 1));
        if(itemStack.getItem() == Items.GOLDEN_CARROT)
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, regeneration_time, regeneration_level+1));
        if(itemStack.getItem() == Items.ROTTEN_FLESH) {
            Random random = new Random();
            int i = random.nextInt(2);
            if(i==1)
                livingEntity.damage(livingEntity.getDamageSources().mobAttack(livingEntity),8);
            else
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, regeneration_time, regeneration_level));
        }
        if(itemStack.getItem() == Items.SPIDER_EYE || itemStack.getItem() == Items.POISONOUS_POTATO)
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 300, 0));
    }

    public static void TradeForVillager(PlayerEntity player, Item item, int number, int select){
        int i = player.getInventory().getSlotWithStack(item.getDefaultStack());
        if(i == -1 && player.getOffHandStack().getItem() == item && player.getOffHandStack().getCount() >= number){
            if (select == 0) {
                ModSetting.villager_health++;
                player.getOffHandStack().setCount(player.getOffHandStack().getCount() - number);
            }
            if (select == 1 && ModSetting.villager_armor < 60) {
                ModSetting.villager_armor++;
                player.getOffHandStack().setCount(player.getOffHandStack().getCount() - number);
            }
            if (select == 2 && ModSetting.villager_armor_toughness < 50) {
                ModSetting.villager_armor_toughness++;
                player.getOffHandStack().setCount(player.getOffHandStack().getCount() - number);
            }
            if (select == 3 && ModSetting.player_heath_boost < 5) {
                ModSetting.player_heath_boost++;
                player.getOffHandStack().setCount(player.getOffHandStack().getCount() - number);
            }
            if (select == 4 && ModSetting.player_power < 3) {
                ModSetting.player_power++;
                player.getOffHandStack().setCount(player.getOffHandStack().getCount() - number);
            }
            if (select == 5 && ModSetting.player_register < 2) {
                ModSetting.player_register++;
                player.getOffHandStack().setCount(player.getOffHandStack().getCount() - number);
            }
        }
        if(i != -1) {
            for (int j = 0; j <= 35; j++) {
                if(player.getInventory().getStack(j).getItem() == item && player.getInventory().getStack(j).getCount() >= number){
                    if (select == 0) {
                        ModSetting.villager_health++;
                        player.getInventory().getStack(j).setCount(player.getInventory().getStack(j).getCount() - number);
                        break;
                    }
                    if (select == 1 && ModSetting.villager_armor < 60) {
                        ModSetting.villager_armor++;
                        player.getInventory().getStack(j).setCount(player.getInventory().getStack(j).getCount() - number);
                        break;
                    }
                    if (select == 2 && ModSetting.villager_armor_toughness < 50) {
                        ModSetting.villager_armor_toughness++;
                        player.getInventory().getStack(j).setCount(player.getInventory().getStack(j).getCount() - number);
                        break;
                    }
                    if (select == 3 && ModSetting.player_heath_boost < 5) {
                        ModSetting.player_heath_boost++;
                        player.getInventory().getStack(j).setCount(player.getInventory().getStack(j).getCount() - number);
                        break;
                    }
                    if (select == 4 && ModSetting.player_power < 3) {
                        ModSetting.player_power++;
                        player.getInventory().getStack(j).setCount(player.getInventory().getStack(j).getCount() - number);
                        break;
                    }
                    if (select == 5 && ModSetting.player_register < 2) {
                        ModSetting.player_register++;
                        player.getInventory().getStack(j).setCount(player.getInventory().getStack(j).getCount() - number);
                        break;
                    }
                }
            }
        }
    }

    //其实是用来执行交易的方法
    public static void getSlotByItem(PlayerEntity player, ItemStack itemStack1, ItemStack itemStack2, int number1, int number2){
        int i = player.getInventory().getSlotWithStack(itemStack2);
        if(i == -1 && player.getOffHandStack().getItem() == itemStack2.getItem() && player.getOffHandStack().getCount() >= number2){
            player.getOffHandStack().setCount(player.getOffHandStack().getCount() - number2);
            itemStack1.setCount(number1);
            boolean bl = player.getInventory().insertStack(itemStack1);
            if(!bl || !itemStack1.isEmpty()) {
                ItemEntity itemEntity = player.dropItem(itemStack1, false);
                if (itemEntity != null) {
                    itemEntity.resetPickupDelay();
                    itemEntity.setOwner(player.getUuid());
                }
            }
        }
        if(i != -1) {
            for (int j = 0; j <= 35; j++) {
                if(player.getInventory().getStack(j).getItem() == itemStack2.getItem() && player.getInventory().getStack(j).getCount() >= number2){
                    player.getInventory().getStack(j).setCount(player.getInventory().getStack(j).getCount() - number2);
                    itemStack1.setCount(number1);
                    boolean bl = player.getInventory().insertStack(itemStack1);
                    if(!bl || !itemStack1.isEmpty()) {
                        ItemEntity itemEntity = player.dropItem(itemStack1, false);
                        if (itemEntity != null) {
                            itemEntity.resetPickupDelay();
                            itemEntity.setOwner(player.getUuid());
                        }
                    }
                    break;
                }
            }
        }
    }

    public static Item GetArmorByInt(EquipmentSlot equipmentSlot, int level){
        if(equipmentSlot == EquipmentSlot.HEAD) {
            if (level == 1)
                return Items.LEATHER_HELMET;
            if (level == 2)
                return Items.GOLDEN_HELMET;
            if (level == 3)
                return Items.CHAINMAIL_HELMET;
            if (level == 4)
                return Items.IRON_HELMET;
            if (level == 5)
                return Items.DIAMOND_HELMET;
            if (level >= 6)
                return Items.NETHERITE_HELMET;
        }
        if(equipmentSlot == EquipmentSlot.CHEST) {
            if (level == 1)
                return Items.LEATHER_CHESTPLATE;
            if (level == 2)
                return Items.GOLDEN_CHESTPLATE;
            if (level == 3)
                return Items.CHAINMAIL_CHESTPLATE;
            if (level == 4)
                return Items.IRON_CHESTPLATE;
            if (level == 5)
                return Items.DIAMOND_CHESTPLATE;
            if (level >= 6)
                return Items.NETHERITE_CHESTPLATE;
        }
        if(equipmentSlot == EquipmentSlot.LEGS) {
            if (level == 1)
                return Items.LEATHER_LEGGINGS;
            if (level == 2)
                return Items.GOLDEN_LEGGINGS;
            if (level == 3)
                return Items.CHAINMAIL_LEGGINGS;
            if (level == 4)
                return Items.IRON_LEGGINGS;
            if (level == 5)
                return Items.DIAMOND_LEGGINGS;
            if (level >= 6)
                return Items.NETHERITE_LEGGINGS;
        }
        if(equipmentSlot == EquipmentSlot.FEET) {
            if (level == 1)
                return Items.LEATHER_BOOTS;
            if (level == 2)
                return Items.GOLDEN_BOOTS;
            if (level == 3)
                return Items.CHAINMAIL_BOOTS;
            if (level == 4)
                return Items.IRON_BOOTS;
            if (level == 5)
                return Items.DIAMOND_BOOTS;
            if (level >= 6)
                return Items.NETHERITE_BOOTS;
        }
        return Items.AIR;
    }
}
