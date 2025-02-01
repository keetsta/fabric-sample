package me.keet.shitfuck;

import me.keet.shitfuck.block.ModBlockEntities;
import me.keet.shitfuck.block.ModBlocks;
import me.keet.shitfuck.effects.ModEffects;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Shitfuck implements ModInitializer {

    public static final String MOD_ID = "shitfuck";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("shitfuck loaded!");

        initialize();
    }

    public static void initialize() {
        ModItems.initialize();
        ModComponents.initialize();
        ModArmorMaterials.initialize();
        ModBlocks.initialize();
        ModEffects.initialize();
        ModBlockEntities.initialize();
    }
}