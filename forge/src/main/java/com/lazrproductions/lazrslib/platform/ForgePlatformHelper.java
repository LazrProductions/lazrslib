package com.lazrproductions.lazrslib.platform;

import com.lazrproductions.lazrslib.platform.services.IPlatformHelper;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

import java.lang.reflect.Field;

public class ForgePlatformHelper implements IPlatformHelper {
    @Override
    public String getPlatformName() {
        return "Forge";
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