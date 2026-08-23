package dev.nohmobs.redstonebattery.datagen;

import dev.nohmobs.redstonebattery.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new RecipeProvider(registries, output) {
            @Override
            public void buildRecipes() {
                HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);

                shaped(RecipeCategory.REDSTONE, ModBlocks.REDSTONE_BATTERY)
                        .pattern("sds")
                        .pattern("dtd")
                        .pattern("sds")
                        .define('s', Blocks.STONE)
                        .define('d', Blocks.REDSTONE_WIRE)
                        .define('t', Items.REDSTONE_TORCH)
                        .group("multi_bench")
                        .unlockedBy(getHasName(Blocks.REDSTONE_WIRE), has(Blocks.REDSTONE_WIRE))
                        .save(output);
            }
        };
    }

    @Override
    public String getName() {
        return "RedstoneBatteryRecipeProvider";
    }
}
