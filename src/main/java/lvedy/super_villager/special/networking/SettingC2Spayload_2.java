package lvedy.super_villager.special.networking;

import lvedy.super_villager.Super_villager;
import lvedy.super_villager.registry.ModNetWorking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record SettingC2Spayload_2(int[] a,boolean b) implements CustomPayload{
    public static final CustomPayload.Id<SettingC2Spayload_2> ID = new CustomPayload.Id<>(ModNetWorking.SET2);
    public static final PacketCodec<PacketByteBuf, SettingC2Spayload_2> CODEC = PacketCodec.of((value, buf) -> {buf.writeIntArray(value.a);buf.writeBoolean(value.b);}, buf -> new SettingC2Spayload_2(buf.readIntArray(),buf.readBoolean()));
    //public static final PacketCodec<PacketByteBuf,SettingC2Spayload_2> CODEC = PacketCodecs.BYTE_ARRAY.xmap(SettingC2Spayload_2::new,SettingC2Spayload_2::a).cast();

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}
