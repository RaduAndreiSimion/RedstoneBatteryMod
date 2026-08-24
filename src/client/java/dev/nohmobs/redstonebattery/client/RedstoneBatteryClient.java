package dev.nohmobs.redstonebattery.client;

import dev.nohmobs.redstonebattery.RedstoneBattery;
import dev.nohmobs.redstonebattery.block.ModBlocks;
import dev.nohmobs.redstonebattery.client.tintsource.block.RedstoneBatteryBlockTintSource;
import dev.nohmobs.redstonebattery.client.tintsource.item.RedstoneBatteryItemTintSource;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.resources.Identifier;

import java.util.List;


public class RedstoneBatteryClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockColorRegistry.register(List.of(new RedstoneBatteryBlockTintSource()), ModBlocks.REDSTONE_BATTERY);
		ItemTintSources.ID_MAPPER.put(Identifier.fromNamespaceAndPath(RedstoneBattery.MOD_ID, "redstone_power"), RedstoneBatteryItemTintSource.CODEC);
	}
}