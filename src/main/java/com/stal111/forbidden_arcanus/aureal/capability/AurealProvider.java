package com.stal111.forbidden_arcanus.aureal.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Aureal Provider
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.aureal.capability.AurealProvider
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-01-26
 */
public class AurealProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundNBT> {

    @CapabilityInject(IAureal.class)
    public static Capability<IAureal> CAPABILITY = null;

    IAureal instance = new AurealImpl();

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == CAPABILITY ? LazyOptional.of(() -> (T) instance) : LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        return (CompoundNBT) CAPABILITY.getStorage().writeNBT(CAPABILITY, instance, null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        CAPABILITY.getStorage().readNBT(CAPABILITY, instance, null, nbt);
    }
}
