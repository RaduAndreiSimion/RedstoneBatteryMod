package dev.nohmobs.redstonebattery.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

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

            return InteractionResult.SUCCESS;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(
                LOCKED,
                POWER
        );
    }
}
