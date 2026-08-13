package com.lazrproductions.lazrslib.common.tag;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.StringTag;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class TagUtilities {
    public static final String TAG_POSITION = "Position";

    public static BlockPos blockPosFromTag(CompoundTag tag) {
        int x = tag.getIntArray(TAG_POSITION)[0];
        int y = tag.getIntArray(TAG_POSITION)[1];
        int z = tag.getIntArray(TAG_POSITION)[2];
        return new BlockPos(x, y, z);
    }
    public static CompoundTag blockPosToTag(BlockPos pos) {
        CompoundTag compoundtag1 = new CompoundTag();
        compoundtag1.putIntArray(TAG_POSITION, new int[] { pos.getX(), pos.getY(), pos.getZ() });
        return compoundtag1;
    }

    public static NonNullList<ItemStack> itemStackFromTag(ListTag tag) {
        NonNullList<ItemStack> items = NonNullList.withSize(tag.size(), ItemStack.EMPTY);
        for (int i = 0; i < tag.size(); i++) {
            items.set(i, ItemStack.of(tag.getCompound(i)));
        }
        return items;
    }
    public static ListTag itemStackToTag(NonNullList<ItemStack> items) {
        ListTag list = new ListTag();
        items.forEach((c) -> {
            CompoundTag t = new CompoundTag();
            c.save(t);
            list.add(t);
        });
        return list;
    }

    public static String[] stringListFromTag(ListTag list) {
        String[] s = new String[list.size()];
        for (int i = 0; i < list.size(); i++)
            s[i] = list.getString(i);
        return s;
    }
    public static ListTag stringListToTag(String[] value) {
        ListTag list = new ListTag();
        for (String i : value)
            list.add(StringTag.valueOf(i));
        return list;
    }

    public static UUID[] UUIDFromTag(ListTag list) {
        UUID[] s = new UUID[list.size()];
        for (int i = 0; i < list.size(); i++)
            s[i] = NbtUtils.loadUUID(list.get(i));
        return s;
    }
    public static ListTag UUIDToTag(UUID[] value) {
        ListTag list = new ListTag();
        for (UUID i : value)
            list.add(NbtUtils.createUUID(i));
        return list;
    }

    public static boolean getOrDefault(CompoundTag tag, String key, boolean defaultValue) {
        return tag.contains(key) ? tag.getBoolean(key) : defaultValue;
    }
    public static int getOrDefault(CompoundTag tag, String key, int defaultValue) {
        return tag.contains(key) ? tag.getInt(key) : defaultValue;
    }
    public static double getOrDefault(CompoundTag tag, String key, double defaultValue) {
        return tag.contains(key) ? tag.getDouble(key) : defaultValue;
    }
    public static float getOrDefault(CompoundTag tag, String key, float defaultValue) {
        return tag.contains(key) ? tag.getFloat(key) : defaultValue;
    }
    public static long getOrDefault(CompoundTag tag, String key, long defaultValue) {
        return tag.contains(key) ? tag.getLong(key) : defaultValue;
    }
    public static String getOrDefault(CompoundTag tag, String key, String defaultValue) {
        return tag.contains(key) ? tag.getString(key) : defaultValue;
    }
}