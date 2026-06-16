package com.lazrproductions.lazrslib.common.config;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import com.lazrproductions.lazrslib.LazrsLibMod;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;

public abstract class LazrConfig {
    final ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
    ModConfigSpec spec;


    final String name;
    final ModConfig.Type type;


    final ArrayList<ConfigProperty<?>> genericProperties = new ArrayList<>(0);
    final ArrayList<ConfigCategory> categories = new ArrayList<>(0);


    public LazrConfig(String name, ModConfig.Type type) {
        this.name = name;
        this.type = type;

        this.registerProperties();
    }

    public abstract void registerProperties();


    public <T> ConfigProperty<T> createGenericProperty(@Nonnull ConfigProperty<T> property) {
        genericProperties.add(property);
        return property;
    }
    public ConfigCategory createCategory(@Nonnull ConfigCategory category, CreatePropertiesFunction createProperties) {
        createProperties.call(category);
        categories.add(category);
        return category;
    }

    
    ModConfigSpec buildConfig() {
        builder.push(name +" Config");

        buildGenericProperties();

        buildCategories();

        builder.pop();
        spec = builder.build();
        LazrsLibMod.LOGGER.info("Built config '" + name +"' Successfully!");
        return spec;
    }

    void buildGenericProperties() {
        for (ConfigProperty<?> configProperty : genericProperties)
            configProperty.build();
    }
    void buildCategories() {
        for (ConfigCategory configCategory : categories)
            configCategory.build();
    }

    ModConfig.Type getType() {
        return type;
    }
    String getConfigName() {
        String typeString = "-client.toml";
        switch (getType()) {
            case SERVER:
                typeString = "-server.toml";
                break;
            case COMMON:
                typeString = "-common.toml";
                break;
            default:
                typeString = "-client.toml";
                break;
        }
        return name + typeString;
    }


    public void registerConfig(@Nonnull final ModContainer container) {
        container.registerConfig(getType(), buildConfig(), getConfigName());
    }
}
