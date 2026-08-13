package com.lazrproductions.lazrslib.common.config;

/**
 * The base for a config property, should be overridden by mod loader to register property values.
 * @param <T> The value type that this property stores.
 */
public interface IConfigProperty<T> extends IConfigField {
    /**
     * Get the name of this property.
     * @return The name of this property.
     */
    String getName();

    /**
     * Get the description of this property.
     * @return The desctription of this property.
     */
    String getDescription();

    /**
     * Get the value of this property.
     * @return The value of this property.
     */
    T get();
}