package lvedy.super_villager.special.networking;

import lvedy.super_villager.registry.ModNetWorking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record TradeC2SPayload(int a) implements CustomPayload {
    public static final CustomPayload.Id<TradeC2SPayload> ID = new CustomPayload.Id<>(ModNetWorking.TRADE);
    public static final PacketCodec<PacketByteBuf, TradeC2SPayload> CODEC = PacketCodec.of((value, buf) -> {buf.writeInt(value.a);}, buf -> new TradeC2SPayload(buf.readInt()));
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
