package me.keet.shitfuck.effects;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModEffects implements ModInitializer {
    public static final RegistryEntry<StatusEffect> TATER;

    static {
        TATER = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of("shitfuck", "tater"), new TaterEffect());
    }

    @Override
    public void onInitialize() {
    }

    public static void initialize() {}
}
