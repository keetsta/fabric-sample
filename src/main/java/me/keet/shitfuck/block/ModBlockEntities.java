package me.keet.shitfuck.block;

import me.keet.shitfuck.Shitfuck;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<CounterBlockEntity> COUNTER_BLOCK_ENTITY =
            register("counter", CounterBlockEntity::new, ModBlocks.COUNTER_BLOCK);

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType.BlockEntityFactory<? extends T> entityFactory, Block...blocks) {
        Identifier id = Identifier.of(Shitfuck.MOD_ID, name);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, BlockEntityType.Builder.<T>create(entityFactory, blocks).build());
    }

    public static void initialize() {
    }
}
