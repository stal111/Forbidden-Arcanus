package com.stal111.forbidden_arcanus.capability.spawningBlockingBlocks;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class EntitySpawningBlockingBlocks implements IEntitySpawningBlockingBlocks {

    private List<CompoundNBT> spawningBlockingBlocks = new ArrayList<>();

    @Override
    public List<CompoundNBT> getSpawningBlockingBlocks() {
        return spawningBlockingBlocks;
    }

    @Override
    public void addSpawningBlockingBlock(BlockPos pos, SpawningBlockingMode spawningBlockingMode) {
        CompoundNBT compoundNBT = new CompoundNBT();

        compoundNBT.putInt("x", pos.getX());
        compoundNBT.putInt("y", pos.getY());
        compoundNBT.putInt("z", pos.getZ());
        compoundNBT.putString("mode", spawningBlockingMode.toString());

        spawningBlockingBlocks.add(compoundNBT);
    }

    @Override
    public void removeSpawningBlockingBlock(BlockPos pos) {
        spawningBlockingBlocks.removeIf(compoundNBT -> compoundNBT.getInt("x") == pos.getX() && compoundNBT.getInt("y") == pos.getY() && compoundNBT.getInt("z") == pos.getZ());
    }
}
