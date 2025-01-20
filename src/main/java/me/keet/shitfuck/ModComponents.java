package me.keet.shitfuck;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModComponents {
    protected static void initialize() {
        Shitfuck.LOGGER.info("Registering {} components", Shitfuck.MOD_ID);
        // Technically this method can stay empty, but some developers like to notify
        // the console, that certain parts of the mod have been successfully initialized
    }

    public static final ComponentType<Integer> CLICK_COUNT_COMPONENT = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(Shitfuck.MOD_ID, "click_count"),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );
}