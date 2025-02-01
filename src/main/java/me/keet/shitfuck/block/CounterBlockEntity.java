package me.keet.shitfuck.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CounterBlockEntity extends BlockEntity {
    private int ticksSinceLast = 0;
    private int clicks = 0;

    public CounterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COUNTER_BLOCK_ENTITY, pos, state);
    }

    public int getClicks() {
        return clicks;
    }

    public void incrementClicks() {
        clicks++;
        markDirty();
        if (ticksSinceLast < 10) return;
        ticksSinceLast = 0;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putInt("clicks", clicks);

        super.writeNbt(nbt, registryLookup);
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, CounterBlockEntity entity) {
        entity.ticksSinceLast++;
    }
}
