package com.lazrproductions.lazrslib.common.config;

import java.util.List;

/**
 * The base for a config category, should be overridden by mod loader to register property values.
 */
public interface IConfigCategory extends IConfigField {
    /**
     * Populate this category with the given list of properties and return it.
     * @param properties The properties to populate this config with.
     * @return This category, now populated with the given properties.
     */
    IConfigCategory withProperties(List<IConfigProperty<?>> properties);

    /**
     * Add a new property to this category.
     * @param property The property to add.
     * @return The created property.
     * @param <T> The value type of the property/
     */
    <T> IConfigProperty<T> putProperty(IConfigProperty<T> property);
}