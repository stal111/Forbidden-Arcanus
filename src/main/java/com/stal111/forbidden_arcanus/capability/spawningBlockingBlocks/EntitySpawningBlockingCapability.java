package com.stal111.forbidden_arcanus.capability.spawningBlockingBlocks;

import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntitySpawningBlockingCapability implements ICapabilitySerializable<ListNBT> {

    @CapabilityInject(IEntitySpawningBlockingBlocks.class)
    public static final Capability<IEntitySpawningBlockingBlocks> ENTITY_SPAWNING_BLOCKING_BLOCKS_CAPABILITY = null;
    private LazyOptional<IEntitySpawningBlockingBlocks> instance = LazyOptional.of(ENTITY_SPAWNING_BLOCKING_BLOCKS_CAPABILITY::getDefaultInstance);

    public static void register() {
        CapabilityManager.INSTANCE.register(IEntitySpawningBlockingBlocks.class, new EntitySpawningBlockingStorage(), EntitySpawningBlockingBlocks::new);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return ENTITY_SPAWNING_BLOCKING_BLOCKS_CAPABILITY.orEmpty(cap, instance);
    }

    @Override
    public ListNBT serializeNBT() {
        return (ListNBT) ENTITY_SPAWNING_BLOCKING_BLOCKS_CAPABILITY.getStorage().writeNBT(ENTITY_SPAWNING_BLOCKING_BLOCKS_CAPABILITY, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null);
    }

    @Override
    public void deserializeNBT(ListNBT nbt) {
        ENTITY_SPAWNING_BLOCKING_BLOCKS_CAPABILITY.getStorage().readNBT(ENTITY_SPAWNING_BLOCKING_BLOCKS_CAPABILITY, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null, nbt);
    }
}