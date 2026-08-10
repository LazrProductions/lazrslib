package com.lazrproductions.lazrslib.common.config;

import com.lazrproductions.lazrslib.LazrsLibConstants;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public abstract class LazrConfig implements ILazrConfig {
    public final ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
    ModConfigSpec spec;

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

    ModConfigSpec buildConfig() {
        builder.push(name +" Config");

        buildGenericProperties();

        buildCategories();

        builder.pop();
        LazrsLibConstants.LOG.info("Loaded {} config '{}' successfully!", name, getSide().toString());
        return builder.build();
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

    public void registerConfig(@NotNull ModContainer modContainer) {
        ModConfig.Type configType;
        switch (getSide()) {
            case SERVER -> configType = ModConfig.Type.SERVER;
            case CLIENT -> configType = ModConfig.Type.CLIENT;
            default -> configType = ModConfig.Type.COMMON;
        }

        modContainer.registerConfig(configType, buildConfig(), getName() + "-" + getSide().toString() +".toml");
    }

    public String getName() {
        return name;
    }
}
