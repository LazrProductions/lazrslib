package com.lazrproductions.lazrslib.common.config;

import org.jetbrains.annotations.NotNull;

public interface ILazrConfig{
    /**
     * Register this config's properties.
     */
    void registerProperties();

    /**
     * Create a generic property to be listed under no parent category.
     * @param property The property to create.
     * @return The created and populated property.
     * @param <T> The value type of the property.
     */
    <T> IConfigProperty<T> createGenericProperty(@NotNull IConfigProperty<T> property);

    /**
     * Create a category in the config then add properties to be listed under it.
     * @param category The category to create
     * @param createProperties The properties to be added under the given category.
     * @return The created and populated category.
     */
    IConfigCategory createCategory(@NotNull IConfigCategory category, ICreatePropertiesFunction createProperties);

    void buildGenericProperties();
    void buildCategories();

    String getName();
    ConfigSide getSide();

    default String getConfigName() {
        String typeString = switch (getSide()) {
            case SERVER -> "-server.toml";
            case COMMON -> "-common.toml";
            default -> "-client.toml";
        };
        return getName() + typeString;
    }
}
