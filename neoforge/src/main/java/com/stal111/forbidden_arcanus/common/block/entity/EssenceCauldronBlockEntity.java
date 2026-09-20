package com.stal111.forbidden_arcanus.common.block.entity;

import com.stal111.forbidden_arcanus.common.block.EssenceCauldronBlock;
import com.stal111.forbidden_arcanus.common.item.crafting.input.EssenceTransformInput;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.init.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EssenceCauldronBlockEntity extends EssenceStorageBlockEntity {

    public static final AABB ESSENCE_RENDER_BOUNDS = EssenceCauldronBlock.SHAPE_INSIDE.bounds().contract(0.0, 1.0 / 16.0, 0.0);
    public static final int DEFAULT_CAPACITY = 5000;

    public EssenceCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ESSENCE_CAULDRON.get(), pos, state);
    }

    public void tryTransformItem(ServerLevel level, ItemStack stack) {
        var input = new EssenceTransformInput(stack, this.getEssenceStorage().getEssenceValue(), value -> {
            this.addEssence(-value);
            level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
        });

        level.recipeAccess().getRecipeFor(ModRecipeTypes.ESSENCE_TRANSFORM.get(), input, level)
                .map(RecipeHolder::value)
                .map(recipe -> recipe.assemble(input))
                .ifPresent(result -> {
                    DefaultDispenseItemBehavior.spawnItem(level, result, 2, Direction.UP, Vec3.atBottomCenterOf(this.worldPosition).relative(Direction.UP, 1.4));
                });
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
