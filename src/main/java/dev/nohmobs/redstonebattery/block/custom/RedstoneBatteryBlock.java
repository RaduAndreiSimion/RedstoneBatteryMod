package dev.nohmobs.redstonebattery.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

public class RedstoneBatteryBlock extends Block {
    public static final BooleanProperty LOCKED = BooleanProperty.create("locked");
    public static final IntegerProperty POWER = IntegerProperty.create("power", 0, 15);
    public RedstoneBatteryBlock(Properties properties) {
        super(properties);

        registerDefaultState(defaultBlockState()
                .setValue(LOCKED, false)
                .setValue(POWER, 0)
        );
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!player.getAbilities().mayBuild) {
            return InteractionResult.PASS;
        } else {
            boolean locked = state.getValue(LOCKED);

            level.setBlockAndUpdate(pos, state.setValue(LOCKED, !locked));

            level.playSound(player, pos, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 1.0F, 1.0F);

            if (!level.isClientSide() && !state.getValue(LOCKED)) {
                level.scheduleTick(pos, this, 1);
            }

            return InteractionResult.SUCCESS;
        }
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if (!level.isClientSide()) {
            if (!state.getValue(LOCKED)) {
                level.scheduleTick(pos, this, 1);
            }
            level.updateNeighborsAt(pos, this);
        }
    }

    @Override
    protected void neighborChanged(final BlockState state, final Level level, final BlockPos pos, final Block block, final @Nullable Orientation orientation, final boolean movedByPiston) {
        if (!level.isClientSide() && !state.getValue(LOCKED)) {
            level.scheduleTick(pos, this, 1);
        }
    }

    @Override
    protected void tick(final BlockState state, final ServerLevel level, final BlockPos pos, final RandomSource random) {
        if (!state.getValue(LOCKED)) {
            level.setBlock(pos, getSignalFromNeighbours(state, level, pos), 3);
        }
    }

    @Override
    protected boolean isSignalSource(final BlockState state) {
        return true;
    }

    @Override
    protected int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return state.getValue(LOCKED) ? state.getValue(POWER) : 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(
                LOCKED,
                POWER
        );
    }

    private BlockState getSignalFromNeighbours(final BlockState state, final Level level, final BlockPos pos) {
        int totalSignal = 0;

        for (Direction direction : Direction.values()) {
            BlockPos neighbourPos = pos.relative(direction);

            totalSignal += level.getSignal(neighbourPos, direction.getOpposite());
        }

        int finalPower = Math.min(totalSignal, POWER.getPossibleValues().getLast());

        return state.setValue(POWER, finalPower);
    }
}
