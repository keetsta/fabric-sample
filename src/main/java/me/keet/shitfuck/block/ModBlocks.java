package me.keet.shitfuck.block;

import com.mojang.serialization.MapCodec;
import me.keet.shitfuck.ModItems;
import me.keet.shitfuck.Shitfuck;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    public static final Block COUNTER_BLOCK = register(
            new CounterBlock(AbstractBlock.Settings.create()), "counter_block.json", true
    );

        public static void initGroups() {
            ItemGroupEvents.modifyEntriesEvent(ModItems.FREAKY_GROUP_KEY).register((itemGroup) -> {
                itemGroup.add(ModBlocks.CONDENSED_DIRT.asItem());
                itemGroup.add(ModBlocks.CONDENSED_OAK_LOG.asItem());
                itemGroup.add(ModBlocks.PRISMARINE_LAMP.asItem());
                itemGroup.add(ModBlocks.COUNTER_BLOCK.asItem());
            });
        }
}

final class CounterBlock extends BlockWithEntity {
    public CounterBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(CounterBlock::new);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public @NotNull BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CounterBlockEntity(pos, state);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!(world.getBlockEntity(pos) instanceof CounterBlockEntity counterBlockEntity)) {
            return super.onUse(state, world, pos, player, hit);
        }

        counterBlockEntity.incrementClicks();
        player.sendMessage(Text.literal("You've clicked the block for the " + counterBlockEntity.getClicks() + "th time."), true);

        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.COUNTER_BLOCK_ENTITY, CounterBlockEntity::tick);
    }
}