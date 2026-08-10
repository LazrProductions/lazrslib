package com.lazrproductions.lazrslib.platform;

import com.lazrproductions.lazrslib.platform.services.IPlatformHelper;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

import java.lang.reflect.Field;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    public boolean isSideOnlyPresent(Field field) {
        return field.isAnnotationPresent(OnlyIn.class);
    }
}