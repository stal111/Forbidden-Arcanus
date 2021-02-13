package com.stal111.forbidden_arcanus.capability.item.timer;

import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Timer Provider
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.capability.item.timer.TimerProvider
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-03
 */
public class TimerProvider implements ICapabilityProvider, ICapabilitySerializable<IntNBT> {

    @CapabilityInject(ITimer.class)
    public static Capability<ITimer> CAPABILITY = null;

    ITimer instance = new TimerImpl();

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == CAPABILITY ? LazyOptional.of(() -> (T) instance) : LazyOptional.empty();
    }

    @Override
    public IntNBT serializeNBT() {
        return (IntNBT) CAPABILITY.getStorage().writeNBT(CAPABILITY, instance, null);
    }

    @Override
    public void deserializeNBT(IntNBT nbt) {
        CAPABILITY.getStorage().readNBT(CAPABILITY, instance, null, nbt);
    }
}
