package com.cropaura.cropaura;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ToggleAuraPacket() implements CustomPacketPayload {

    public static final Type<ToggleAuraPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(CropAura.MODID, "toggle_aura"));

    public static final StreamCodec<ByteBuf, ToggleAuraPacket> STREAM_CODEC =
            StreamCodec.unit(new ToggleAuraPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
