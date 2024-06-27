package com.lazrproductions.lazrslib.common.config;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import com.lazrproductions.lazrslib.LazrsLibMod;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public abstract class LazrConfig {
    final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    ForgeConfigSpec spec;


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

    
    ForgeConfigSpec buildConfig() {
        builder.push(name +" Config");

        buildGenericProperties();

        buildCategories();

        builder.pop();
        LazrsLibMod.LOGGER.info("Built config '" + name +"' Successfully!");
        return builder.build();
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


    public void registerConfig(@Nonnull final ModLoadingContext ctx) {
        ctx.registerConfig(getType(), buildConfig(), getConfigName());
    }
}
