package com.lazrproductions.lazrslib.common.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigCategory implements IConfigCategory {
    final LazrConfig config;
    final String name;
    final ArrayList<IConfigProperty<?>> properties = new ArrayList<>();

    public ConfigCategory(LazrConfig config, String name) {
        this.config = config;
        this.name = name;
    }

    public ConfigCategory withProperties(List<IConfigProperty<?>> properties) {
        this.properties.clear();
        this.properties.addAll(properties);
        return this;
    }
    public <T> IConfigProperty<T> putProperty(IConfigProperty<T> property) {
        properties.add(property);
        return property;
    }

    public void build() {
        config.builder.push(name);

        for (IConfigProperty<?> configProperty : properties)
            configProperty.build();

        config.builder.pop();
    }

    public LazrConfig getConfig() {
        return config;
    }
}
