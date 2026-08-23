package dev.nohmobs.redstonebattery.client;

import dev.nohmobs.redstonebattery.block.ModBlocks;
import dev.nohmobs.redstonebattery.block.custom.RedstoneBatteryBlock;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;


public class RedstoneBatteryClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockColorRegistry.register(List.of(new BlockTintSource() {
			@Override
			public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
				return getRedstoneColor(state);
			}

			@Override
			public int color(BlockState state) {
				return 0x3D0000;
			}
		}), ModBlocks.REDSTONE_BATTERY);
	}

	private static int getRedstoneColor(BlockState state) {
		int power = state.getValue(RedstoneBatteryBlock.POWER);

		List<Integer> possible_power_values = RedstoneBatteryBlock.POWER.getPossibleValues();
		int max_power = possible_power_values.getLast();

		int unlit = 0x3D0000;
		int lit = 0xFF0000;

		float amount = (float) power / (float) max_power;

		int unlitR = (unlit >> 16) & 0xFF;
		int unlitG = (unlit >> 8) & 0xFF;
		int unlitB = unlit & 0xFF;

		int litR = (lit >> 16) & 0xFF;
		int litG = (lit >> 8) & 0xFF;
		int litB = lit & 0xFF;

		int r = (int) (unlitR + (litR - unlitR) * amount);
		int g = (int) (unlitG + (litG - unlitG) * amount);
		int b = (int) (unlitB + (litB - unlitB) * amount);

		return 0xFF000000 | (r << 16) | (g << 8) | b;
	}
}