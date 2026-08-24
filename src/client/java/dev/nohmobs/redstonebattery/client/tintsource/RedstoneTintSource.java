package dev.nohmobs.redstonebattery.client.tintsource;

import dev.nohmobs.redstonebattery.block.custom.RedstoneBatteryBlock;

import java.util.List;

public abstract class RedstoneTintSource {
    public static final int UNLIT = 0x3D0000;
    public static final int LIT = 0xFF0000;

    public static int getRedstoneColor(int power) {
        List<Integer> possible_power_values = RedstoneBatteryBlock.POWER.getPossibleValues();
        int max_power = possible_power_values.getLast();

        float amount = (float) power / (float) max_power;

        int unlitR = (UNLIT >> 16) & 0xFF;
        int unlitG = (UNLIT >> 8) & 0xFF;
        int unlitB = UNLIT & 0xFF;

        int litR = (LIT >> 16) & 0xFF;
        int litG = (LIT >> 8) & 0xFF;
        int litB = LIT & 0xFF;

        int r = (int) (unlitR + (litR - unlitR) * amount);
        int g = (int) (unlitG + (litG - unlitG) * amount);
        int b = (int) (unlitB + (litB - unlitB) * amount);

        return 0xFF000000 | (r << 16) | (g << 8) | b;
    }
}
