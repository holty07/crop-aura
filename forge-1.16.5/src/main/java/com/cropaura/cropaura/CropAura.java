package com.cropaura.cropaura;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CropAura.MODID)
public class CropAura {

    public static final String MODID = "cropaura";

    public CropAura() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CropAuraConfig.SPEC, "cropaura-common.toml");

        MinecraftForge.EVENT_BUS.register(new CropAuraEventHandler());
        CropAuraNetwork.register();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::onClientSetup);
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ClientSetup.registerKeyMappings();
            MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        });
    }
}
