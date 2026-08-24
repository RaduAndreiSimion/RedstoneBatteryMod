package dev.nohmobs.redstonebattery.client.tintsource.item;

import com.mojang.serialization.MapCodec;
import dev.nohmobs.redstonebattery.block.custom.RedstoneBatteryBlock;
import dev.nohmobs.redstonebattery.client.tintsource.RedstoneTintSource;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public class RedstoneBatteryItemTintSource extends RedstoneTintSource implements ItemTintSource {
    public static final MapCodec<RedstoneBatteryItemTintSource> CODEC =
            MapCodec.unit(new RedstoneBatteryItemTintSource());

    @Override
    public int calculate(ItemStack itemStack, @Nullable ClientLevel level, @Nullable LivingEntity owner) {
        var component = itemStack.get(DataComponents.BLOCK_STATE);

        int color = UNLIT;

        if (component != null) {
            var powerState = component.get(RedstoneBatteryBlock.POWER);
            if (powerState != null) {
                color = getRedstoneColor(powerState);
            }
        }

        return color;
    }

    @Override
    public MapCodec<? extends ItemTintSource> type() {
        return CODEC;
    }
}
