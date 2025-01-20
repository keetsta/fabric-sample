package me.keet.shitfuck;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Shitfuck implements ModInitializer {

    public static final String MOD_ID = "shitfuck";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("shitfuck loaded!");

        ModItems.initialize();
        ModComponents.initialize();
        ModArmorMaterials.initialize();
    }
}
