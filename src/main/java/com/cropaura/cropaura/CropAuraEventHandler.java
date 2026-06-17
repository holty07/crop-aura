package com.cropaura.cropaura;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CropAuraEventHandler {

    private static final Set<UUID> DISABLED_PLAYERS = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public static void onRegisterPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(CropAura.MODID);
        registrar.playToServer(
                ToggleAuraPacket.TYPE,
                ToggleAuraPacket.STREAM_CODEC,
                (packet, context) -> {
                    ServerPlayer player = (ServerPlayer) context.player();
                    UUID id = player.getUUID();
                    if (DISABLED_PLAYERS.contains(id)) {
                        DISABLED_PLAYERS.remove(id);
                        player.sendSystemMessage(Component.literal("[Crop Aura] Enabled"));
                    } else {
                        DISABLED_PLAYERS.add(id);
                        player.sendSystemMessage(Component.literal("[Crop Aura] Disabled"));
                    }
                }
        );
    }

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity().level() instanceof ServerLevel serverLevel)) {
            return;
        }

        if (DISABLED_PLAYERS.contains(event.getEntity().getUUID())) {
            return;
        }

        long gameTick = serverLevel.getGameTime();
        int interval = CropAuraConfig.TICK_INTERVAL.get();
        if (gameTick % interval != 0) {
            return;
        }

        int radius = CropAuraConfig.RADIUS.get();
        BlockPos center = event.getEntity().blockPosition();

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = -2; y <= 2; y++) {
                    BlockPos pos = center.offset(x, y, z);
                    BlockState state = serverLevel.getBlockState(pos);

                    if (state.getBlock() instanceof BonemealableBlock bonemealable) {
                        if (bonemealable.isValidBonemealTarget(serverLevel, pos, state)) {
                            bonemealable.performBonemeal(serverLevel, serverLevel.random, pos, state);
                        }
                    }
                }
            }
        }
    }
}
