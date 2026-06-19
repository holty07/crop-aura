package com.cropaura.cropaura;

import net.minecraftforge.client.event.RegisterKeyMappingsEvent;

public class ClientSetup {

    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(KeyBindings.TOGGLE_AURA);
    }
}
