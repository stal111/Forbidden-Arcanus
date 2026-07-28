package com.stal111.forbidden_arcanus.common.block.entity;

import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class EssenceUtremJarBlockEntity extends EssenceStorageBlockEntity {

    public static final AABB ESSENCE_RENDER_BOUNDS = new AABB(3.5 / 16.0F, 0.5 / 16.0F, 3.5 / 16.0F, 12.5 / 16.0F, 12.5 / 16.0F, 12.5 / 16.0F);
    public static final int DEFAULT_CAPACITY = 10000;

    public EssenceUtremJarBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ESSENCE_UTREM_JAR.get(), pos, state);
    }

    @Override
    public AABB getEssenceRenderBounds() {
        return ESSENCE_RENDER_BOUNDS;
    }

    @Override
    public Block getEmptyReplacementBlock() {
        return ModBlocks.UTREM_JAR.get();
    }

    @Override
    public int getDefaultStorageCapacity() {
        return DEFAULT_CAPACITY;
    }

    @Override
    public ItemStack getAsItem() {
        ItemStack stack = ModItems.ESSENCE_UTREM_JAR.get().getDefaultInstance();

        stack.applyComponents(this.collectComponents());

        return stack;
    }
}
