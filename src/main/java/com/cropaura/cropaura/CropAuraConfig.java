package com.cropaura.cropaura;

import net.neoforged.neoforge.common.ModConfigSpec;

public class CropAuraConfig {

    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.IntValue RADIUS;
    public static final ModConfigSpec.IntValue TICK_INTERVAL;

    static {
        BUILDER.comment("Crop Aura Configuration");

        RADIUS = BUILDER
            .comment("Horizontal radius (in blocks) around the player to search for crops")
            .defineInRange("radius", 5, 1, 16);

        TICK_INTERVAL = BUILDER
            .comment("How often (in ticks) to attempt to grow crops. 20 ticks = 1 second")
            .defineInRange("tickInterval", 20, 1, 200);

        SPEC = BUILDER.build();
    }
}
