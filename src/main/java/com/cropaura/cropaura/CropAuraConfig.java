package com.cropaura.cropaura;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class CropAuraConfig {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.IntValue RADIUS;
    public static final ForgeConfigSpec.IntValue TICK_INTERVAL;

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

    public static void register(IEventBus modEventBus) {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SPEC, "cropaura-common.toml");
    }
}
