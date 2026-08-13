package com.lazrproductions.lazrslib.common.config;

/**
 * A generic interface for fields within an {@link ILazrConfig}
 */
public interface IConfigField {
    /**
     * Get this field's parent config.
     * @return This field's parent config.
     */
    ILazrConfig getConfig();

    /**
     * Build this field for its respective mod loader.
     */
    void build();
}