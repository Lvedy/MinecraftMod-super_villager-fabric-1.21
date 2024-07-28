package lvedy.super_villager.special.GUI;

import lvedy.super_villager.ModSetting;
import lvedy.super_villager.special.networking.TradeC2SPayload;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class TradeScreen extends Screen {
    public TradeScreen(Text title) {
        super(Text.literal("交易"));
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
    public ButtonWidget button15;
    public ButtonWidget button16;
    public ButtonWidget button17;
    public ButtonWidget button18;
    public ButtonWidget button19;
    public ButtonWidget button20;
    public ButtonWidget button21;

    @Override
    protected void init() {
        button1 = ButtonWidget.builder(Text.literal("购买绿宝石x1  花费: 腐肉x8"), button -> {
            if (client != null && client.player!=null) {
                ClientPlayNetworking.send(new TradeC2SPayload(1));
            }}).dimensions(width / 2 - 205, 10, 130, 20).build();
        button2 = ButtonWidget.builder(Text.literal("购买绿宝石x1  花费: 箭矢x8"), button -> {
            if (client != null && client.player!=null) {
                ClientPlayNetworking.send(new TradeC2SPayload(2));
            }}).dimensions(width / 2 - 65, 10, 130, 20).build();
        button3 = ButtonWidget.builder(Text.literal("购买绿宝石x1  花费: 骨头x8"), button -> {
            if (client != null && client.player!=null) {
                ClientPlayNetworking.send(new TradeC2SPayload(3));
            }}).dimensions(width / 2 + 75, 10, 130, 20).build();
        button4 = ButtonWidget.builder(Text.literal("购买绿宝石x2  花费: 线x7"), button -> {
            if (client != null && client.player!=null) {
                ClientPlayNetworking.send(new TradeC2SPayload(4));
            }}).dimensions(width /  2 - 205, 35, 130, 20).build();
        button5 = ButtonWidget.builder(Text.literal("购买绿宝石x1 花费: 蜘蛛眼x3"), button -> {
            if (client != null && client.player!=null) {
                ClientPlayNetworking.send(new TradeC2SPayload(5));
            }}).dimensions(width /  2 - 65, 35, 130, 20).build();
        button6 = ButtonWidget.builder(Text.literal("购买绿宝石x3  花费: 火药x5"), button -> {
            if (client != null && client.player!=null) {
                ClientPlayNetworking.send(new TradeC2SPayload(6));
            }}).dimensions(width /  2 + 75, 35, 130, 20).build();
        button7 = ButtonWidget.builder(Text.literal("购买村民最大生命值+1"), button -> {
            if (client != null && client.player!=null) {
                ClientPlayNetworking.send(new TradeC2SPayload(7));
            }}).dimensions(width /  2 - 205, 60, 130, 20)
                .tooltip(Tooltip.of(Text.literal("当前值:" + ModSetting.villager_health+"\n花费: 绿宝石x24"))).build();
        button8 = ButtonWidget.builder(Text.literal("购买村民基础护甲+0.5"), button -> {
            if (client != null && client.player!=null) {
                ClientPlayNetworking.send(new TradeC2SPayload(8));
            }}).dimensions(width /  2 - 65, 60, 130, 20)
                .tooltip(Tooltip.of(Text.literal("当前值:" + ModSetting.villager_armor*0.5+"  \n最大值为30"+"\n花费: 绿宝石x24"))).build();
        button9 = ButtonWidget.builder(Text.literal("购买村民基础护甲韧性+0.4"), button -> {
            if (client != null && client.player!=null) {
                ClientPlayNetworking.send(new TradeC2SPayload(9));
            }}).dimensions(width /  2 + 75, 60, 130, 20)
                .tooltip(Tooltip.of(Text.literal("当前值:" + ModSetting.villager_armor_toughness*0.4+"  \n最大值为20"+"\n花费: 绿宝石x24"))).build();
        button10 = ButtonWidget.builder(Text.literal("购买铁砧x1 花费:绿宝石x48"), button -> {
            if (client != null && client.player!=null) {
                ClientPlayNetworking.send(new TradeC2SPayload(10));
            }}).dimensions(width /  2 - 205, 85, 130, 20).build();
        button11 = ButtonWidget.builder(Text.literal("购买逗猫棒x1 花费:绿宝石x64"), button -> {
            if (client != null && client.player!=null) {
                ClientPlayNetworking.send(new TradeC2SPayload(11));
            }}).dimensions(width /  2 - 65, 85, 130, 20).build();
        button12 = ButtonWidget.builder(Text.literal("购买附魔之瓶x32"), button -> {
            if (client != null && client.player!=null) {
                ClientPlayNetworking.send(new TradeC2SPayload(12));
            }}).dimensions(width /  2 + 75, 85, 130, 20).tooltip(Tooltip.of(Text.literal("花费绿宝石x8"))).build();
        button13 = ButtonWidget.builder(Text.literal("购买力量I 花费:绿宝石x8"), button -> {
            if (client != null && client.player!=null) {
                ClientPlayNetworking.send(new TradeC2SPayload(13));
            }}).dimensions(width /  2 - 205, 110, 130, 20).build();
        button14 = ButtonWidget.builder(Text.literal("购买锋利I 花费:绿宝石x8"), button -> {
            if (client != null && client.player!=null) {
                ClientPlayNetworking.send(new TradeC2SPayload(14));
            }}).dimensions(width /  2 - 65, 110, 130, 20).build();
        button15 = ButtonWidget.builder(Text.literal("购买保护I 花费:绿宝石x4"), button -> {
            if (client != null && client.player!=null) {
                ClientPlayNetworking.send(new TradeC2SPayload(15));
            }}).dimensions(width /  2 + 75, 110, 130, 20).build();
        button16 = ButtonWidget.builder(Text.literal("购买生命提升 花费:绿宝石x32"), button -> {
            if (client != null && client.player!=null) {
                ClientPlayNetworking.send(new TradeC2SPayload(16));
            }}).dimensions(width /  2 - 205, 135, 130, 20).tooltip(Tooltip.of(Text.literal("当前值:" + ModSetting.player_heath_boost+"  \n最大值为5"))).build();
        button17 = ButtonWidget.builder(Text.literal("购买力量 花费:绿宝石x64"), button -> {
            if (client != null && client.player!=null) {
                ClientPlayNetworking.send(new TradeC2SPayload(17));
            }}).dimensions(width /  2 - 65, 135, 130, 20).tooltip(Tooltip.of(Text.literal("当前值:" + ModSetting.player_power+"  \n最大值为3"))).build();
        button18 = ButtonWidget.builder(Text.literal("购买抗性提升 花费:绿宝石x64"), button -> {
            if (client != null && client.player!=null) {
                ClientPlayNetworking.send(new TradeC2SPayload(18));
            }}).dimensions(width /  2 + 75, 135, 130, 20).tooltip(Tooltip.of(Text.literal("当前值:" + ModSetting.player_register+"  \n最大值为2"))).build();
        button19 = ButtonWidget.builder(Text.literal("购买下界合金锭x1"), button -> {
            if (client != null && client.player!=null) {
                ClientPlayNetworking.send(new TradeC2SPayload(19));
            }}).dimensions(width /  2 - 205, 160, 130, 20).tooltip(Tooltip.of(Text.literal("花费绿宝石x56"))).build();
        button20 = ButtonWidget.builder(Text.literal("购买足力健 花费:绿宝石x64"), button -> {
            if (client != null && client.player!=null) {
                ClientPlayNetworking.send(new TradeC2SPayload(20));
            }}).dimensions(width /  2 - 65, 160, 130, 20).build();
        button21 = ButtonWidget.builder(Text.literal("购买茄子 花费:绿宝石x16"), button -> {
            if (client != null && client.player!=null) {
                ClientPlayNetworking.send(new TradeC2SPayload(21));
            }}).dimensions(width /  2 + 75, 160, 130, 20).build();
        addDrawableChild(button1);addDrawableChild(button2);addDrawableChild(button3);
        addDrawableChild(button4);addDrawableChild(button5);addDrawableChild(button6);
        addDrawableChild(button7);addDrawableChild(button8);addDrawableChild(button9);
        addDrawableChild(button10);addDrawableChild(button11);addDrawableChild(button12);
        addDrawableChild(button13);addDrawableChild(button14);addDrawableChild(button15);
        addDrawableChild(button16);addDrawableChild(button17);addDrawableChild(button18);
        addDrawableChild(button19);addDrawableChild(button20);addDrawableChild(button21);
    }

    public void close() {
        ModSetting.look_trade_screen = false;
        super.close();
    }
}
