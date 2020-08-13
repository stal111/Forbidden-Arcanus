package com.stal111.forbidden_arcanus.capability.eternalStellaActive;

import net.minecraft.nbt.StringNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EternalStellaActiveCapability implements ICapabilitySerializable<StringNBT> {

    @CapabilityInject(IEternalStellaActive.class)
    public static final Capability<IEternalStellaActive> ETERNAL_STELLA_ACTIVE_CAPABILITY = null;
    private LazyOptional<IEternalStellaActive> instance = LazyOptional.of(ETERNAL_STELLA_ACTIVE_CAPABILITY::getDefaultInstance);

    public static void register() {
        CapabilityManager.INSTANCE.register(IEternalStellaActive.class, new EternalStellaActiveStorage(), EternalStellaActive::new);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return ETERNAL_STELLA_ACTIVE_CAPABILITY.orEmpty(cap, instance);
    }

    @Override
    public StringNBT serializeNBT() {
        return (StringNBT) ETERNAL_STELLA_ACTIVE_CAPABILITY.getStorage().writeNBT(ETERNAL_STELLA_ACTIVE_CAPABILITY, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null);
    }

    @Override
    public void deserializeNBT(StringNBT nbt) {
        ETERNAL_STELLA_ACTIVE_CAPABILITY.getStorage().readNBT(ETERNAL_STELLA_ACTIVE_CAPABILITY, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null, nbt);
    }
}
