package com.cropaura.cropaura;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class CropAuraEventHandler {

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent.Post event) {
        // Only run server-side
        if (!(event.getEntity().level() instanceof ServerLevel serverLevel)) {
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
