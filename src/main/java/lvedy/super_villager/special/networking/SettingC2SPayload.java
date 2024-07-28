package lvedy.super_villager.special.networking;

import lvedy.super_villager.Super_villager;
import lvedy.super_villager.registry.ModNetWorking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record SettingC2SPayload(int[] a) implements CustomPayload {
    public static final Id<SettingC2SPayload> ID = new CustomPayload.Id<>(ModNetWorking.SET1);
    public static final PacketCodec<PacketByteBuf, SettingC2SPayload> CODEC = PacketCodec.of((value, buf) -> buf.writeIntArray(value.a), buf -> new SettingC2SPayload(buf.readIntArray()));
    //public static final PacketCodec<PacketByteBuf,SettingC2SPayload> CODEC = PacketCodecs.BYTE_ARRAY.xmap(SettingC2SPayload::new,SettingC2SPayload::a).cast();


    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
