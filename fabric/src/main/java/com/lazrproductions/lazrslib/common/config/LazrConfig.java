package com.lazrproductions.lazrslib.common.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.lazrproductions.lazrslib.LazrsLibConstants;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;

public abstract class LazrConfig implements ILazrConfig {
    protected final CommentedFileConfig config;
    protected final String name;
    protected final ConfigSide side;

    final ArrayList<IConfigProperty<?>> genericProperties = new ArrayList<>(0);
    final ArrayList<IConfigCategory> categories = new ArrayList<>(0);

    public LazrConfig(String name, ConfigSide side) {
        this.name = name;
        this.side = side;

        Path configPath = FabricLoader.getInstance()
                .getConfigDir()
                .resolve(getConfigName());

        this.config = CommentedFileConfig.builder(configPath)
                .sync() 
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        this.registerProperties();
        this.buildConfig();
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

    public void buildConfig() {
        config.load();

        for (IConfigProperty<?> prop : genericProperties) {
            prop.build();
        }
        for (IConfigCategory cat : categories) {
            cat.build();
        }

        config.save();

        LazrsLibConstants.LOG.info("Loaded {} config '{}' successfully!", name, getSide().toString());
    }

    public void buildGenericProperties() {
        for (IConfigProperty<?> configProperty : genericProperties)
            configProperty.build();
    }
    public void buildCategories() {
        for (IConfigCategory configCategory : categories)
            configCategory.build();
    }

    public void registerConfig() {}

    public ConfigSide getSide() {
        return side;
    }

    public String getName() {
        return name;
    }

    public CommentedFileConfig getRawConfig() {
        return config;
    }
}
