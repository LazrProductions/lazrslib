package com.lazrproductions.lazrslib.common.entity;

import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

/**
 * A class of utilities for entities.
 */
public class EntityUtilities {
    /**
     * Get the current raw speed of the given entity, without modification.
     * @param entity (LivingEntity) the entity to get.
     * @return (double) The speed of the given entity.
     */
    public static double GetCurrentSpeed(@NotNull LivingEntity entity)
    {
        return EntityUtilities.GetCurrentSpeed(entity, 1);
    }

    /**
     * Get the current speed of the given entity.
     * @param entity (LivingEntity) The entity whose speed to get.
     * @param multiplier (double) The multiplier to apply to the raw speed.
     * @return (double) The speed of the given entity.
     */
    public static double GetCurrentSpeed(@NotNull LivingEntity entity, double multiplier)
    {
        double x = entity.getX()-entity.xOld;
        double y = entity.getY()-entity.yOld;
        double z = entity.getZ()-entity.zOld;
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2)) * multiplier;
    }
}
