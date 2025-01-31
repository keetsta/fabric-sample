package me.keet.shitfuck.block;

import me.keet.shitfuck.ModItems;
import me.keet.shitfuck.Shitfuck;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Identifier;

public class ModBlocks {
        public static Block register(Block block, String name, boolean shouldRegisterItem) {
            // Register the block and its item.
            Identifier id = Identifier.of(Shitfuck.MOD_ID, name);

            // Sometimes, you may not want to register an item for the block.
            // Eg: if it's a technical block like `minecraft:air` or `minecraft:end_gateway`
            if (shouldRegisterItem) {
                BlockItem blockItem = new BlockItem(block, new Item.Settings());
                Registry.register(Registries.ITEM, id, blockItem);
            }

            return Registry.register(Registries.BLOCK, id, block);
        }

    public static void initialize() {
            initGroups();
    }

    public static final Block CONDENSED_DIRT = register(
            new Block(AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.ROOTED_DIRT).hardness(1.0F)),
            "condensed_dirt",
            true
    );

    public static final Block CONDENSED_OAK_LOG = register(
            new PillarBlock(
                    AbstractBlock.Settings.create()
                            .sounds(BlockSoundGroup.WOOD)
                            .hardness(1.0F)
            ), "condensed_oak_log", true
    );

    public static final Block PRISMARINE_LAMP = register(
            new PrismarineLampBlock(
                    AbstractBlock.Settings.create()
                            .sounds(BlockSoundGroup.LANTERN)
                            .luminance(PrismarineLampBlock::getLuminance)
                            .hardness(0.5F)
            ), "prismarine_lamp", true
    );

        public static void initGroups() {
            ItemGroupEvents.modifyEntriesEvent(ModItems.FREAKY_GROUP_KEY).register((itemGroup) -> {
                itemGroup.add(ModBlocks.CONDENSED_DIRT.asItem());
                itemGroup.add(ModBlocks.CONDENSED_OAK_LOG.asItem());
                itemGroup.add(ModBlocks.PRISMARINE_LAMP.asItem());
            });
        }
}

