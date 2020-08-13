package com.stal111.forbidden_arcanus.capability.eternalStellaActive;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class EternalStellaActiveStorage implements Capability.IStorage<IEternalStellaActive> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<IEternalStellaActive> capability, IEternalStellaActive instance, Direction side) {
        return StringNBT.valueOf(String.valueOf(instance.getEternalStellaActive()));
    }

    @Override
    public void readNBT(Capability<IEternalStellaActive> capability, IEternalStellaActive instance, Direction side, INBT nbt) {
        instance.setEternalStellaActive(Boolean.parseBoolean(nbt.getString()));
    }
}
