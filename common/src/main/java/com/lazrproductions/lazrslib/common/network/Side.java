package com.lazrproductions.lazrslib.common.network;

public enum Side {
    CLIENT {
        @Override
        public boolean isClientSide() {
            return true;
        }

        @Override
        public boolean isServerSide() {
            return false;
        }

        @Override
        public String toString() {
            return "client";
        }
    },
    SERVER {
        @Override
        public boolean isClientSide() {
            return false;
        }

        @Override
        public boolean isServerSide() {
            return true;
        }

        @Override
        public String toString() {
            return "server";
        }
    };

    public abstract boolean isClientSide();
    public abstract boolean isServerSide();
}
