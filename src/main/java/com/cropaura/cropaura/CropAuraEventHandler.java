package com.cropaura.cropaura;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CropAuraEventHandler {

    public static final Set<UUID> DISABLED_PLAYERS = Collections.newSetFromMap(new ConcurrentHashMap<>());

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player.level() instanceof ServerLevel serverLevel)) return;

        ServerPlayer player = (ServerPlayer) event.player;
        if (DISABLED_PLAYERS.contains(player.getUUID())) return;

        long gameTick = serverLevel.getGameTime();
        int interval = CropAuraConfig.TICK_INTERVAL.get();
        if (gameTick % interval != 0) return;

        int radius = CropAuraConfig.RADIUS.get();
        BlockPos center = player.blockPosition();

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = -2; y <= 2; y++) {
                    BlockPos pos = center.offset(x, y, z);
                    BlockState state = serverLevel.getBlockState(pos);

                    if (state.getBlock() instanceof BonemealableBlock bonemealable) {
                        if (bonemealable.isValidBonemealTarget(serverLevel, pos, state, false)) {
                            bonemealable.performBonemeal(serverLevel, serverLevel.random, pos, state);
                        }
                    }
                }
            }
        }
    }

    public static void togglePlayer(ServerPlayer player) {
        UUID id = player.getUUID();
        if (DISABLED_PLAYERS.contains(id)) {
            DISABLED_PLAYERS.remove(id);
            player.sendSystemMessage(Component.literal("[Crop Aura] Enabled"));
        } else {
            DISABLED_PLAYERS.add(id);
            player.sendSystemMessage(Component.literal("[Crop Aura] Disabled"));
        }
    }
}
