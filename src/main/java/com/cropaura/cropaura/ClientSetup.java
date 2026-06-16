package com.cropaura.cropaura;

import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

public class ClientSetup {

    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(KeyBindings.TOGGLE_AURA);
    }
}
