package com.lazrproductions.lazrslib.common.config;

public abstract class AbstractConfigField {
    protected final LazrConfig config;

    public AbstractConfigField(LazrConfig config) {
        this.config = config;
    }

    public LazrConfig getConfig() {
        return config;
    }
}
