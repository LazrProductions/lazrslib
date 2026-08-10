package com.lazrproductions.lazrslib.common.config;

/**
 * Called when creating a config category to add its properties to it.
 */
public interface ICreatePropertiesFunction {
    /**
     * Called when creating a config category to add its properties to it.
     * @param category The category properties should be added to.
     */
    void call(IConfigCategory category);
}
