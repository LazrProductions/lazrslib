package com.lazrproductions.lazrslib.common.tag;


import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;

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


    public static NonNullList<ItemStack> fromTag(ListTag tag) {
        NonNullList<ItemStack> items = NonNullList.withSize(tag.size(), ItemStack.EMPTY);
        for (int i = 0; i < tag.size(); i++) {
            items.set(i, ItemStack.of(tag.getCompound(i)));
        } 
        return items;
    }
    public static ListTag toTag(NonNullList<ItemStack> items) {
        ListTag list = new ListTag();
        items.forEach((c) -> {
            CompoundTag t = new CompoundTag();
            c.save(t); 
            list.add(t);
        });
        return list;
    }
}
