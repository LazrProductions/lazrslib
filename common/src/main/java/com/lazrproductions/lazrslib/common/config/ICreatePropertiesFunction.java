package com.lazrproductions.lazrslib.common.config;

import javax.annotation.Nonnull;

/**
 * Called when creating a config category to add its properties to it.
 */
public interface ICreatePropertiesFunction {
    /**
     * Called when creating a config category to add its properties to it.
     * @param category The category properties should be added to.
     */
    void call(@Nonnull IConfigCategory category);
}
