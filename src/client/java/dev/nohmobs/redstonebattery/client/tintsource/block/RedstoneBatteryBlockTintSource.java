package dev.nohmobs.redstonebattery.client.tintsource.block;

import dev.nohmobs.redstonebattery.block.custom.RedstoneBatteryBlock;
import dev.nohmobs.redstonebattery.client.tintsource.RedstoneTintSource;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class RedstoneBatteryBlockTintSource extends RedstoneTintSource implements BlockTintSource {
    @Override
    public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
        return getRedstoneColor(state.getValue(RedstoneBatteryBlock.POWER));
    }

    @Override
    public int color(BlockState state) {
        return UNLIT;
    }
}
