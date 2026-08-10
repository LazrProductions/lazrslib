package com.lazrproductions.lazrslib.common.config;

/**
 * The side a {@link ILazrConfig} is registered for.
 */
public enum ConfigSide {
    COMMON {
        @Override
        public boolean isClient() {
            return false;
        }

        @Override
        public boolean isCommon() {
            return true;
        }

        @Override
        public boolean isServer() {
            return false;
        }

        @Override
        public String toString() {
            return "common";
        }
    },
    CLIENT {
        @Override
        public boolean isClient() {
            return true;
        }

        @Override
        public boolean isCommon() {
            return false;
        }

        @Override
        public boolean isServer() {
            return false;
        }

        @Override
        public String toString() {
            return "client";
        }
    },
    SERVER {
        @Override
        public boolean isClient() {
            return false;
        }

        @Override
        public boolean isCommon() {
            return false;
        }

        @Override
        public boolean isServer() {
            return true;
        }

        @Override
        public String toString() {
            return "server";
        }
    };

    /**
     * Get the string representation of this side.
     * <code>
     *      <br/>ConfigSide.CLIENT -> "client"
     *      <br/>ConfigSide.COMMON -> "common"
     *      <br/>ConfigSide.SERVER -> "server"
     * </code>
     * @return The string representation of this side.
     */
    public abstract String toString();

    /**
     * Get whether or not this side is equal to ConfigSide.CLIENT
     * @return whether or not this side is equal to ConfigSide.CLIENT.
     */
    public abstract boolean isClient();
    public abstract boolean isCommon();
    public abstract boolean isServer();
}
