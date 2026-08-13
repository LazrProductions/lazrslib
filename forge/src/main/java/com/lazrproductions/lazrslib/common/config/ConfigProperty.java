package com.lazrproductions.lazrslib.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nonnull;

public class ConfigProperty<T> implements IConfigProperty<T> {
    final LazrConfig config;
    final String name;
    final String comment;
    final T defaultValue;

    ForgeConfigSpec.ConfigValue<T> value;

    public ConfigProperty(@Nonnull LazrConfig config, String name, String description, T defaultValue) {
        this.config = config;
        this.name = name;
        this.comment = description;
        this.defaultValue = defaultValue;
    }

    public void build() {
        value = config.builder.comment(comment).define(name, defaultValue);
    }

    public ILazrConfig getConfig() {
        return config;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return comment;
    }
    public T get() {
        return value == null ? defaultValue : value.get();
    }
}
