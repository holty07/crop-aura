package com.cropaura.cropaura;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEventHandler {

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        while (KeyBindings.TOGGLE_AURA.isPressed()) {
            CropAuraNetwork.CHANNEL.sendToServer(new ToggleAuraPacket());
        }
    }
}
