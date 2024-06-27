package com.lazrproductions.lazrslib.common.config;

import javax.annotation.Nonnull;

import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class ConfigProperty<T> extends AbstractConfigField{
    final String name;
    final String comment;
    final T defaultValue;

    ConfigValue<T> value;

    public ConfigProperty(@Nonnull LazrConfig config, String name, String description, T defaultValue) {
        super(config);
        
        this.name = name;
        this.comment = description;
        this.defaultValue = defaultValue;
    }

    public ConfigValue<T> build() {
        value = config.builder.comment(comment).define(name, defaultValue);
        return value;
    }



    public String getName() {
        return name;
    }
    public String getDescription() {
        return comment;
    }
    public T get() {
        return value.get();
    }
}
