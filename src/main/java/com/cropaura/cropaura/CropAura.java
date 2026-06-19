package com.cropaura.cropaura;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CropAura.MODID)
public class CropAura {

    public static final String MODID = "cropaura";

    public CropAura() {
        var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        CropAuraConfig.register(modEventBus);
        PacketHandler.register();

        MinecraftForge.EVENT_BUS.register(new CropAuraEventHandler());

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            modEventBus.addListener(ClientSetup::onRegisterKeyMappings);
            MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        });
    }
}
