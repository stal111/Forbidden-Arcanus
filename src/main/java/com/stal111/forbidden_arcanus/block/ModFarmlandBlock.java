package com.stal111.forbidden_arcanus.block;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class ModFarmlandBlock extends FarmlandBlock {

    public ModFarmlandBlock(Properties builder) {
        super(builder);
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
        return plantable instanceof CropsBlock || plantable instanceof FlowerBlock;
    }

    @Override
    public void onFallenUpon(World world, BlockPos pos, Entity entity, float fallDistance) {
        entity.onLivingFall(fallDistance, 1.0F);
    }
}
