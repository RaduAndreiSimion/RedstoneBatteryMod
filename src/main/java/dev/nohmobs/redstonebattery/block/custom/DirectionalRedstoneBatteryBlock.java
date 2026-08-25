package dev.nohmobs.redstonebattery.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jspecify.annotations.Nullable;

public class DirectionalRedstoneBatteryBlock extends RedstoneBatteryBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;

    public DirectionalRedstoneBatteryBlock(Properties properties) {
        super(properties);

        registerDefaultState(defaultBlockState()
                .setValue(LOCKED, false)
                .setValue(POWER, 0)
                .setValue(FACING, Direction.NORTH)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(
                LOCKED,
                POWER,
                FACING
        );
    }

    @Override
    public @Nullable BlockState getStateForPlacement(final BlockPlaceContext context) {
        Direction placementDirection = context.getClickedFace();
        return this.defaultBlockState().setValue(FACING, placementDirection);
    }

    protected BlockState rotate(final BlockState state, final Rotation rotation) {
        return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
    }

    protected BlockState mirror(final BlockState state, final Mirror mirror) {
        return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
    }

    @Override
    protected boolean isSignalSource(final BlockState state) {
        return true;
    }

    protected int getSignal(final BlockState state, final BlockGetter level, final BlockPos pos, final Direction direction) {
        return state.getValue(LOCKED) && state.getValue(FACING) == direction.getOpposite() ? state.getValue(POWER) : 0;
    }
}
