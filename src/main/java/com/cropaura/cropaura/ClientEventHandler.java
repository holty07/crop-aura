package com.cropaura.cropaura;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public class ClientEventHandler {

    @SubscribeEvent
    public void onClientTick(ClientTickEvent.Post event) {
        while (KeyBindings.TOGGLE_AURA.consumeClick()) {
            PacketDistributor.sendToServer(new ToggleAuraPacket());
        }
    }
}
