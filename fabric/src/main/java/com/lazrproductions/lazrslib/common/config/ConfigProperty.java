package com.lazrproductions.lazrslib.common.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import org.jetbrains.annotations.NotNull;

public class ConfigProperty<T> implements IConfigProperty<T> {
    final LazrConfig config;
    final String name;
    final String comment;
    final T defaultValue;

    // Optional: set by ConfigCategory when the property is inside a category
    String categoryPath = null;

    public ConfigProperty(@NotNull LazrConfig config, String name, String description, T defaultValue) {
        this.config = config;
        this.name = name;
        this.comment = description;
        this.defaultValue = defaultValue;
    }

    /** Called by ConfigCategory so the property knows its full path */
    public void setCategoryPath(String category) {
        this.categoryPath = category;
    }

    private String fullPath() {
        return categoryPath == null ? name : categoryPath + "." + name;
    }

    @Override
    public void build() {
        CommentedConfig raw = config.getRawConfig();
        String path = fullPath();

        raw.setComment(path, comment);

        if (!raw.contains(path)) {
            raw.set(path, defaultValue);
        }
    }

    @Override
    public T get() {
        return config.getRawConfig().getOrElse(fullPath(), defaultValue);
    }

    public void set(T value) {
        config.getRawConfig().set(fullPath(), value);
    }

    @Override
    public ILazrConfig getConfig() {
        return config;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return comment;
    }
}
