package dev.nohmobs.redstonebattery.block;

import dev.nohmobs.redstonebattery.RedstoneBattery;
import dev.nohmobs.redstonebattery.block.custom.DirectionalRedstoneBatteryBlock;
import dev.nohmobs.redstonebattery.block.custom.RedstoneBatteryBlock;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class ModBlocks {
    public static void initialise() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.REDSTONE_BLOCKS)
                .register((creativeTab) -> {
                    creativeTab.accept(ModBlocks.REDSTONE_BATTERY);
                    creativeTab.accept(ModBlocks.DIRECTIONAL_REDSTONE_BATTERY);
                });
    }

    public static final Block REDSTONE_BATTERY = register(
            "redstone_battery",
            RedstoneBatteryBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.SMOOTH_STONE)
            );

    public static final Block DIRECTIONAL_REDSTONE_BATTERY = register(
            "directional_redstone_battery",
            DirectionalRedstoneBatteryBlock::new,
            BlockBehaviour.Properties.ofFullCopy(ModBlocks.REDSTONE_BATTERY)
    );

    private static Block register(String name, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties properties) {
        ResourceKey<Block> blockKey = keyOfBlock(name);

        Block block = blockFactory.apply(properties.setId(blockKey));

        ResourceKey<Item> itemKey = keyOfItem(name);

        BlockItem blockItem = new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix());
        Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);

        return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
    }

    private static Block register(String name, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties blockProperties, Item.Properties itemProperties) {
        ResourceKey<Block> blockKey = keyOfBlock(name);

        Block block = blockFactory.apply(blockProperties.setId(blockKey));

        ResourceKey<Item> itemKey = keyOfItem(name);

        BlockItem blockItem = new BlockItem(block, itemProperties.setId(itemKey).useBlockDescriptionPrefix());
        Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);

        return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
    }

    private static Block registerNoItem(String name, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties properties) {
        ResourceKey<Block> blockKey = keyOfBlock(name);

        Block block = blockFactory.apply(properties.setId(blockKey));

        return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
    }

    private static ResourceKey<Block> keyOfBlock(String name) {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(RedstoneBattery.MOD_ID, name));
    }

    private static ResourceKey<Item> keyOfItem(String name) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(RedstoneBattery.MOD_ID, name));
    }
}
