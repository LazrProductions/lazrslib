package com.lazrproductions.lazrslib.common.config;

import com.lazrproductions.lazrslib.LazrsLibConstants;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public abstract class LazrConfig implements ILazrConfig {
    public final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    ForgeConfigSpec spec;

    final String name;
    final ConfigSide type;

    final ArrayList<IConfigProperty<?>> genericProperties = new ArrayList<>(0);
    final ArrayList<IConfigCategory> categories = new ArrayList<>(0);

    public LazrConfig(String name, ConfigSide type) {
        this.name = name;
        this.type = type;

        this.registerProperties();
    }

    public abstract void registerProperties();

    public <T> IConfigProperty<T> createGenericProperty(@NotNull IConfigProperty<T> property) {
        genericProperties.add(property);
        return property;
    }
    public IConfigCategory createCategory(@NotNull IConfigCategory category, @NotNull ICreatePropertiesFunction createProperties) {
        createProperties.call(category);
        categories.add(category);
        return category;
    }

    ForgeConfigSpec buildConfig() {
        builder.push(name +" Config");

        buildGenericProperties();

        buildCategories();

        builder.pop();
        spec = builder.build();

        LazrsLibConstants.LOG.info("Loaded {} config '{}' successfully!", name, getSide().toString());
        return spec;
    }

    public void buildGenericProperties() {
        for (IConfigProperty<?> configProperty : genericProperties)
            configProperty.build();
    }
    public void buildCategories() {
        for (IConfigCategory configCategory : categories)
            configCategory.build();
    }

    public ConfigSide getSide() {
        return type;
    }

    public void registerConfig(@NotNull final ModLoadingContext ctx) {
        ModConfig.Type configType;
        switch (getSide()) {
            case SERVER -> configType = ModConfig.Type.SERVER;
            case CLIENT -> configType = ModConfig.Type.CLIENT;
            default -> configType = ModConfig.Type.COMMON;
        }
        ctx.registerConfig(configType, buildConfig(), getConfigName());
    }

    public String getName() {
        return name;
    }
}
