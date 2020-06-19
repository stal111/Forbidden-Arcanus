package com.stal111.forbidden_arcanus.capability;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class FlightTimeLeftStorage implements Capability.IStorage<IFlightTimeLeft> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<IFlightTimeLeft> capability, IFlightTimeLeft instance, Direction side) {
        return IntNBT.valueOf(instance.getFlightTimeLeft());
    }

    @Override
    public void readNBT(Capability<IFlightTimeLeft> capability, IFlightTimeLeft instance, Direction side, INBT nbt) {
        instance.setFlightTimeLeft(((IntNBT) nbt).getInt());
    }
}
