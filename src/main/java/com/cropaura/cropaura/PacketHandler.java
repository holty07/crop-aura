package com.cropaura.cropaura;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {

    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(CropAura.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        INSTANCE.registerMessage(0, ToggleAuraPacket.class,
                ToggleAuraPacket::encode,
                ToggleAuraPacket::new,
                ToggleAuraPacket::handle,
                java.util.Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );
    }
}
