package lvedy.super_villager.special.GUI;

import lvedy.super_villager.ModSetting;
import lvedy.super_villager.registry.ModNetWorking;
import lvedy.super_villager.special.networking.SettingC2Spayload_2;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Environment(EnvType.CLIENT)
public class SettingScreen extends Screen{
    public SettingScreen(Text title) {
        super(Text.literal("设置"));
    }

    public ButtonWidget button1;
    public ButtonWidget button2;
    public ButtonWidget button3;
    public ButtonWidget button4;
    public ButtonWidget button5;
    public ButtonWidget button6;
    public ButtonWidget button7;
    public ButtonWidget button8;
    public ButtonWidget button9;
    public ButtonWidget button10;
    public ButtonWidget button11;
    public ButtonWidget button12;
    public ButtonWidget button13;
    public ButtonWidget button14;
    public static boolean safe_r;
    public static boolean mob_r;
    public static boolean mob_time;
    public static boolean mob_number;
    public static boolean re_level;
    public static boolean re_time;
    public static boolean level_time;

    @Override
    protected void init() {
        button1 = ButtonWidget.builder(setText("骷髅是否会射击村民:  " + setString(ModSetting.a),ModSetting.a), button -> {
                    ModSetting.a = !ModSetting.a;
                    All();
                })
                .dimensions(width / 2 - 205, 10, 200, 20)
                .tooltip(Tooltip.of(Text.literal("骷髅是否会主动攻击村民")))
                .build();
        button2 = ButtonWidget.builder(setText("怪物质量是否随时间推移而变高:  " + setString(ModSetting.b),ModSetting.b),button -> {
                    ModSetting.b = !ModSetting.b;
                    All();
                })
                .dimensions(width / 2 + 5, 10, 200, 20)
                .tooltip(Tooltip.of(Text.literal("""
                        将会显示危险等级，每刷一定波次的怪后危险等级将会提高一级,该选项会增加每波刷怪数量
                        输入  /危险等级  查看更多详情
                        """)))
                .build();
        button3 = ButtonWidget.builder(setText("村民特殊回血:  " + setString(ModSetting.c),ModSetting.c), button -> {
                    ModSetting.c = !ModSetting.c;
                    All();
                })
                .dimensions(width / 2 - 205, 35, 200, 20)
                .tooltip(Tooltip.of(Text.literal("一些特殊的食物可以让村民获得额外的效果\n金苹果:村民将获得一分钟的抗性提升2\n金胡萝卜:村民将拥有更快的回血速度\n腐肉:村民有50%概率受到8点伤害\n蜘蛛眼 和 毒马铃薯:村民会中毒")))
                .build();
        button4 = ButtonWidget.builder(setText("村民向你展示货物:  " + setString(ModSetting.d),ModSetting.d), button -> {
                    ModSetting.d = !ModSetting.d;
                    All();
                })
                .dimensions(width / 2 + 5, 35, 200, 20)
                .tooltip(Tooltip.of(Text.literal("开启后可以通过在村民附近按P键打开交易面板")))
                .build();
        button14 = ButtonWidget.builder(setText("抱起时村民与玩家是否有碰撞:  " + setString(ModSetting.e),ModSetting.e), button -> {
                    ModSetting.e = !ModSetting.e;
                    All();
                })
                .dimensions(width / 2 - 205, 60, 200, 20)
                .tooltip(Tooltip.of(Text.literal("开启后搬村民的人游戏体验大幅提高")))
                .build();
        button5 = ButtonWidget.builder(Text.of("+"), button -> {
                    SetInt(1);
                    All();
                })
                .dimensions(width / 2 - 95, 85, 90, 20)
                .tooltip(Tooltip.of(Text.literal("点击给所选值加1")))
                .build();
        button6 = ButtonWidget.builder(Text.of("-"), button -> {
                    SetInt(-1);
                    All();
                })
                .dimensions(width / 2 - 205, 85, 90, 20)
                .tooltip(Tooltip.of(Text.literal("点击给所选值减1")))
                .build();
        button7 = ButtonWidget.builder(setText("设置安全距离:  " + setString(safe_r),safe_r), button -> {
                    setAllFalse();
                    safe_r = true;
                    All();
                })
                .dimensions(width / 2 - 205, 110, 200, 20)
                .tooltip(Tooltip.of(Text.literal("当前值:" + ModSetting.super_villager_safe_r + "\n该值决定了怪潮不会在村民多少距离内生成")))
                .build();
        button8 = ButtonWidget.builder(setText("设置刷怪距离:  " + setString(mob_r),mob_r), button -> {
                    setAllFalse();
                    mob_r = true;
                    All();
                })
                .dimensions(width / 2 - 205, 135, 200, 20)
                .tooltip(Tooltip.of(Text.literal("当前值:" + ModSetting.super_villager_mob_r + "\n该值决定了怪潮会在村民多少距离内生成")))
                .build();
        button9 = ButtonWidget.builder(setText("设置刷怪时间:  " + setString(mob_time),mob_time), button -> {
                    setAllFalse();
                    mob_time = true;
                    All();
                })
                .dimensions(width / 2 - 205, 160, 200, 20)
                .tooltip(Tooltip.of(Text.literal("当前值:" + ModSetting.mob_time/20 + "\n该值决定了怪潮会每隔多少时间尝试生成一次\n单位为秒")))
                .build();
        button10 = ButtonWidget.builder(setText("设置刷怪数量:  " + setString(mob_number),mob_number), button -> {
                    setAllFalse();
                    mob_number = true;
                    All();
                })
                .dimensions(width / 2 - 205, 185, 200, 20)
                .tooltip(Tooltip.of(Text.literal("当前值:" + ModSetting.mob_number + "\n该值决定了怪潮每次生成会尝试生成多少只")))
                .build();
        button11 = ButtonWidget.builder(setText("设置村民生命恢复等级:  " + setString(re_level),re_level), button -> {
                    setAllFalse();
                    re_level = true;
                    All();
                })
                .dimensions(width / 2 + 5, 110, 200, 20)
                .tooltip(Tooltip.of(Text.literal("当前值:" + (ModSetting.regeneration_level+1) + "\n该值决定了村民被喂食后获得的生命恢复的等级\n请不要将该值设置为1以下的数字")))
                .build();
        button12 = ButtonWidget.builder(setText("设置村民生命恢复持续时间:  " + setString(re_time),re_time), button -> {
                    setAllFalse();
                    re_time = true;
                    All();
                })
                .dimensions(width / 2 + 5, 135, 200, 20)
                .tooltip(Tooltip.of(Text.literal("当前值:" + ModSetting.regeneration_time/20 + "\n该值决定了村民被喂食后获得的生命恢复的持续时间\n单位为秒")))
                .build();
        button13 = ButtonWidget.builder(setText("设置多少波次提升一次危险等级:  " + setString(level_time),level_time), button -> {
                    setAllFalse();
                    level_time = true;
                    All();
                })
                .dimensions(width / 2 + 5, 160, 200, 20)
                .tooltip(Tooltip.of(Text.literal("当前值:" + ModSetting.level_time + "\n该值决定了每生成了多少次怪潮会提升一次危险等级")))
                .build();
        addDrawableChild(button1);addDrawableChild(button2);
        addDrawableChild(button3);addDrawableChild(button4);
        addDrawableChild(button5);addDrawableChild(button6);
        addDrawableChild(button7);addDrawableChild(button8);
        addDrawableChild(button9);addDrawableChild(button10);
        addDrawableChild(button11);addDrawableChild(button12);
        addDrawableChild(button13);addDrawableChild(button14);
    }

    @Override
    public void close() {
        ModSetting.look_screen = false;
        super.close();
    }

    public String setString(boolean a){
        if(a)
            return "开";
        else
            return "关";
    }

    public Text setText(String string,boolean a){
        if(a)
            return Text.literal(string).formatted(Formatting.GREEN);
        else
            return Text.literal(string).formatted(Formatting.RED);
    }

    public void setAllFalse(){
        safe_r = false;
        mob_r = false;
        mob_time = false;
        mob_number = false;
        re_level = false;
        re_time = false;
        level_time = false;
    }

    public void SetInt(int add){
        if(safe_r)
            ModSetting.super_villager_safe_r += add;
        if(mob_r)
            ModSetting.super_villager_mob_r += add;
        if(mob_time)
            ModSetting.mob_time += add*20;
        if(mob_number)
            ModSetting.mob_number += add;
        if(re_level)
            ModSetting.regeneration_level += add;
        if(re_time)
            ModSetting.regeneration_time += add*20;
        if(level_time)
            ModSetting.level_time += add;
    }

    public void All(){
        if (client != null)
            client.setScreen(new SettingScreen(Text.literal("设置")));
        ClientPlayNetworking.send(new SettingC2Spayload_2(ModSetting.getSettingIntArray(),true));
    }
}
