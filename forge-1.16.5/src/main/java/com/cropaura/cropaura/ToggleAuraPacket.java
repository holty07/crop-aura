package com.cropaura.cropaura;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ToggleAuraPacket {

    public ToggleAuraPacket() {
    }

    public static void encode(ToggleAuraPacket packet, PacketBuffer buffer) {
    }

    public static ToggleAuraPacket decode(PacketBuffer buffer) {
        return new ToggleAuraPacket();
    }

    public static void handle(ToggleAuraPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayerEntity sender = context.getSender();
            if (sender == null) {
                return;
            }
            CropAuraEventHandler.toggle(sender);
        });
        context.setPacketHandled(true);
    }
}
