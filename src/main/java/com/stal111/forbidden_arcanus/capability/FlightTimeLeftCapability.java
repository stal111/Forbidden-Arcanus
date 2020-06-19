package com.stal111.forbidden_arcanus.capability;

import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FlightTimeLeftCapability implements ICapabilitySerializable<IntNBT> {

    @CapabilityInject(IFlightTimeLeft.class)
    public static final Capability<IFlightTimeLeft> FLIGHT_TIME_LEFT_CAPABILITY = null;
    private LazyOptional<IFlightTimeLeft> instance = LazyOptional.of(FLIGHT_TIME_LEFT_CAPABILITY::getDefaultInstance);

    public static void register() {
        CapabilityManager.INSTANCE.register(IFlightTimeLeft.class, new FlightTimeLeftStorage(), FlightTimeLeft::new);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return FLIGHT_TIME_LEFT_CAPABILITY.orEmpty(cap, instance);
    }

    @Override
    public IntNBT serializeNBT() {
        return (IntNBT) FLIGHT_TIME_LEFT_CAPABILITY.getStorage().writeNBT(FLIGHT_TIME_LEFT_CAPABILITY, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null);
    }

    @Override
    public void deserializeNBT(IntNBT nbt) {
        FLIGHT_TIME_LEFT_CAPABILITY.getStorage().readNBT(FLIGHT_TIME_LEFT_CAPABILITY, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null, nbt);
    }
}
