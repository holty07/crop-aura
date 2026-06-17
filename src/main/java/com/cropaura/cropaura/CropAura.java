package com.cropaura.cropaura;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;

@Mod(CropAura.MODID)
public class CropAura {

    public static final String MODID = "cropaura";

    public CropAura(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, CropAuraConfig.SPEC, "cropaura-common.toml");

        NeoForge.EVENT_BUS.register(new CropAuraEventHandler());
        modEventBus.addListener(CropAuraEventHandler::onRegisterPayloads);

        if (net.neoforged.fml.loading.FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.addListener(ClientSetup::onRegisterKeyMappings);
            NeoForge.EVENT_BUS.register(new ClientEventHandler());
        }
    }
}
