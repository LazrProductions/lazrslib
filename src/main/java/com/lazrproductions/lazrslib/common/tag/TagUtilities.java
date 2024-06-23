package com.lazrproductions.lazrslib.common.tag;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

public class TagUtilities {
    public static final String TAG_POSITION = "Position";

    public static BlockPos fromTag(CompoundTag tag) {
        int x = tag.getIntArray(TAG_POSITION)[0];
        int y = tag.getIntArray(TAG_POSITION)[1];
        int z = tag.getIntArray(TAG_POSITION)[2];
        return new BlockPos(x, y, z);
    }

    public static CompoundTag toTag(BlockPos pos) {
        CompoundTag compoundtag1 = new CompoundTag();
        compoundtag1.putIntArray(TAG_POSITION, new int[] { pos.getX(), pos.getY(), pos.getZ() });
        return compoundtag1;
    }
}
