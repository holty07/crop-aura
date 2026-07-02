package com.cropaura.cropaura;

import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientSetup {

    public static void registerKeyMappings() {
        ClientRegistry.registerKeyBinding(KeyBindings.TOGGLE_AURA);
    }
}
