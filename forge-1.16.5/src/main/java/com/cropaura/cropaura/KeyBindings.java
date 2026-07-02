package com.cropaura.cropaura;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {

    public static final KeyBinding TOGGLE_AURA = new KeyBinding(
            "key.cropaura.toggle",
            KeyConflictContext.IN_GAME,
            InputMappings.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_K),
            "key.categories.cropaura"
    );
}
