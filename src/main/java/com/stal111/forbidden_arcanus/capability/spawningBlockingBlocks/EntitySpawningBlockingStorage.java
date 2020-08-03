package com.stal111.forbidden_arcanus.capability.spawningBlockingBlocks;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class EntitySpawningBlockingStorage implements Capability.IStorage<IEntitySpawningBlockingBlocks> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<IEntitySpawningBlockingBlocks> capability, IEntitySpawningBlockingBlocks instance, Direction side) {
        ListNBT listNBT = new ListNBT();

        listNBT.addAll(instance.getSpawningBlockingBlocks());

        return listNBT;
    }

    @Override
    public void readNBT(Capability<IEntitySpawningBlockingBlocks> capability, IEntitySpawningBlockingBlocks instance, Direction side, INBT nbt) {
        ListNBT listNBT = (ListNBT) nbt;

        listNBT.forEach(inbt -> {
            CompoundNBT compoundNBT = (CompoundNBT) inbt;

            instance.addSpawningBlockingBlock(new BlockPos(compoundNBT.getInt("x"), compoundNBT.getInt("y"), compoundNBT.getInt("z")), SpawningBlockingMode.valueOf(compoundNBT.getString("mode")));
        });
    }
}
