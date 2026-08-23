package dev.nohmobs.redstonebattery.datagen.loot;

import dev.nohmobs.redstonebattery.block.ModBlocks;
import dev.nohmobs.redstonebattery.block.custom.RedstoneBatteryBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.functions.CopyBlockState;

import java.util.concurrent.CompletableFuture;

public class ModBlockLootTableProvider extends FabricBlockLootSubProvider {
    public ModBlockLootTableProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(packOutput, registriesFuture);
    }

    @Override
    public void generate() {
        dropSelfWithStates(ModBlocks.REDSTONE_BATTERY, RedstoneBatteryBlock.LOCKED, RedstoneBatteryBlock.POWER);
    }

    public void dropSelfWithStates(Block block, Property<?>... properties) {
        CopyBlockState.Builder function = CopyBlockState.copyState(block);
        for (Property<?> property : properties) {
            function.copy(property);
        }
        this.add(block, this.createSingleItemTable(block).apply(function));
    }
}
