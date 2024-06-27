package com.lazrproductions.lazrslib.common.level;

import javax.annotation.Nonnull;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LevelUtilities {
    /**
     * Move a block from one position to another. Only moves the block if the position to move to is occupied by air. Safely moves block entities without trouble.
     */
    public static boolean moveBlock(@Nonnull Level level, BlockPos fromPos, BlockPos toPos) {
        BlockState stateToMove = level.getBlockState(fromPos);
        
        if(stateToMove.isAir())
            return false;

        BlockState oldState = level.getBlockState(toPos);
        if(!oldState.isAir())
            return false;
        
        BlockEntity e = level.getBlockEntity(fromPos);
        
        CompoundTag tag = new CompoundTag();
        boolean hasBlockEntity = e != null;
        if(e != null) {
            tag = e.save(new CompoundTag());
            e.load(new CompoundTag());
        }



        level.destroyBlock(fromPos, false, null, Block.UPDATE_ALL);
        level.setBlock(toPos, stateToMove, Block.UPDATE_ALL);
        if(hasBlockEntity) {
            BlockEntity o = level.getBlockEntity(toPos);
            if(o != null)
                o.load(tag);
        }

        return true;
    }
}