package com.stal111.forbidden_arcanus.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.IPlantable;

import javax.annotation.Nonnull;

/**
 * Magical Farmland Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.MagicalFarmlandBlock
 *
 * @author stal111
 * @version 1.18.1 - 2.0.3
 * @since 2022-01-27
 */
public class MagicalFarmlandBlock extends FarmBlock {

    public MagicalFarmlandBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canSustainPlant(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull Direction facing, @Nonnull IPlantable plantable) {
        return super.canSustainPlant(Blocks.FARMLAND.defaultBlockState(), level, pos, facing, plantable);
    }
}
