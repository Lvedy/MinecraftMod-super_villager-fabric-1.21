package lvedy.super_villager.special.networking;

import lvedy.super_villager.registry.ModNetWorking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record DeadC2SPayload() implements CustomPayload{
    public static final CustomPayload.Id<DeadC2SPayload> ID = new CustomPayload.Id<>(ModNetWorking.DEAD);
    public static final PacketCodec<PacketByteBuf, DeadC2SPayload> CODEC = PacketCodec.of((value, buf) -> {}, buf -> new DeadC2SPayload());
    //public static final PacketCodec<PacketByteBuf,SettingC2SPayload> CODEC = PacketCodecs.BYTE_ARRAY.xmap(SettingC2SPayload::new,SettingC2SPayload::a).cast();


    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}
