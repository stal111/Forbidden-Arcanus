package com.stal111.forbidden_arcanus.capability.item.timer;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

/**
 * Timer Storage
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.capability.item.timer.TimerStorage
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-03
 */
public class TimerStorage implements Capability.IStorage<ITimer> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<ITimer> capability, ITimer instance, Direction side) {
        return IntNBT.valueOf(instance.getTimer());
    }

    @Override
    public void readNBT(Capability<ITimer> capability, ITimer instance, Direction side, INBT nbt) {
        instance.setTimer(((IntNBT) nbt).getInt());
    }
}
