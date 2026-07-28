package com.stal111.forbidden_arcanus.common.block.entity;

import com.stal111.forbidden_arcanus.common.block.EssenceCauldronBlock;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class EssenceCauldronBlockEntity extends EssenceStorageBlockEntity {

    public static final AABB ESSENCE_RENDER_BOUNDS = EssenceCauldronBlock.SHAPE_INSIDE.bounds().contract(0.0, 1.0 / 16.0, 0.0);
    public static final int DEFAULT_CAPACITY = 5000;

    public EssenceCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ESSENCE_CAULDRON.get(), pos, state);
    }

    @Override
    public AABB getEssenceRenderBounds() {
        return ESSENCE_RENDER_BOUNDS;
    }

    @Override
    public Block getEmptyReplacementBlock() {
        return Blocks.CAULDRON;
    }

    @Override
    public int getDefaultStorageCapacity() {
        return DEFAULT_CAPACITY;
    }
}
