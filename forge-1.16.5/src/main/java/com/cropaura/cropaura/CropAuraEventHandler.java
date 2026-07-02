package com.cropaura.cropaura;

import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CropAuraEventHandler {

    static final Set<UUID> DISABLED_PLAYERS = Collections.newSetFromMap(new ConcurrentHashMap<UUID, Boolean>());

    static void toggle(PlayerEntity player) {
        UUID id = player.getUUID();
        if (DISABLED_PLAYERS.contains(id)) {
            DISABLED_PLAYERS.remove(id);
            player.sendStatusMessage(new StringTextComponent("[Crop Aura] Enabled"), false);
        } else {
            DISABLED_PLAYERS.add(id);
            player.sendStatusMessage(new StringTextComponent("[Crop Aura] Disabled"), false);
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        PlayerEntity player = event.player;
        World world = player.level;
        if (!(world instanceof ServerWorld)) {
            return;
        }
        ServerWorld serverWorld = (ServerWorld) world;

        if (DISABLED_PLAYERS.contains(player.getUUID())) {
            return;
        }

        long gameTick = serverWorld.getGameTime();
        int interval = CropAuraConfig.TICK_INTERVAL.get();
        if (gameTick % interval != 0) {
            return;
        }

        int radius = CropAuraConfig.RADIUS.get();
        BlockPos center = player.blockPosition();

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = -2; y <= 2; y++) {
                    BlockPos pos = center.offset(x, y, z);
                    BlockState state = serverWorld.getBlockState(pos);

                    if (state.getBlock() instanceof IGrowable) {
                        IGrowable growable = (IGrowable) state.getBlock();
                        if (growable.canGrow(serverWorld, pos, state, false)
                                && growable.canUseBonemeal(serverWorld, serverWorld.random, pos, state)) {
                            growable.grow(serverWorld, serverWorld.random, pos, state);
                        }
                    }
                }
            }
        }
    }
}
