package com.lazrproductions.lazrslib.common.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigCategory extends AbstractConfigField {
    final String name;
    final ArrayList<ConfigProperty<?>> properties = new ArrayList<>();

    public ConfigCategory(LazrConfig config, String name) {
        super(config);

        this.name = name;
    }

    public ConfigCategory withProperties(List<ConfigProperty<?>> properties) {
        this.properties.clear();
        this.properties.addAll(properties);
        return this;
    }
    public <T> ConfigProperty<T> putProperty(ConfigProperty<T> property) {
        properties.add(property);
        return property;
    }

    public void build() {
        config.builder.push(name);

        for (ConfigProperty<?> configProperty : properties)
            configProperty.build();

        config.builder.pop();
    }
}
