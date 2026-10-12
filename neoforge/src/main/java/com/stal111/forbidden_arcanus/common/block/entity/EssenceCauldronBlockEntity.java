package com.stal111.forbidden_arcanus.common.block.entity;

import com.stal111.forbidden_arcanus.client.particle.EssenceDropParticleOption;
import com.stal111.forbidden_arcanus.common.block.EssenceCauldronBlock;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import com.stal111.forbidden_arcanus.common.item.crafting.input.EssenceTransformInput;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.init.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EssenceCauldronBlockEntity extends EssenceStorageBlockEntity {

    public static final AABB ESSENCE_RENDER_BOUNDS = EssenceCauldronBlock.SHAPE_INSIDE.bounds().contract(0.0, 1.0 / 16.0, 0.0);
    public static final int DEFAULT_CAPACITY = 3000;

    public EssenceCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ESSENCE_CAULDRON.get(), pos, state);
    }

    public void tryTransformItem(ServerLevel level, ItemStack stack) {
        EssenceValue essenceValue = this.getEssenceStorage().getEssenceValue();

        var input = new EssenceTransformInput(stack, essenceValue, value -> {
            this.addEssence(-value);
            level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
        });

        level.recipeAccess().getRecipeFor(ModRecipeTypes.ESSENCE_TRANSFORM.get(), input, level)
                .map(RecipeHolder::value)
                .map(recipe -> recipe.assemble(input))
                .ifPresent(result -> {
                    DefaultDispenseItemBehavior.spawnItem(level, result, 2, Direction.UP, Vec3.atBottomCenterOf(this.worldPosition).relative(Direction.UP, 1.25));
                    this.playTransformationEffects(level, this.getBlockPos(), essenceValue.type());
                });
    }

    private void playTransformationEffects(ServerLevel level, BlockPos pos, EssenceType essenceType) {
        double x = pos.getX() + 0.5D;
        double z = pos.getZ() + 0.5D;

        float fillPercentage = this.getEssenceStorage().getFillPercentage();
        double liquidY = Math.clamp(pos.getY() + 0.25D + 0.6875D * fillPercentage, pos.getY() + 0.3D, pos.getY() + 0.9D);

        level.sendParticles(new EssenceDropParticleOption(essenceType), x, liquidY + 0.05D, z, 20, 0.16D, 0.08D, 0.16D, 0.15D);
        level.sendParticles(ParticleTypes.POOF, x, liquidY + 0.08D, z, 4, 0.12D, 0.04D, 0.12D, 0.02D);

        level.playSound(null, pos, SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 0.8F, 1.2F);
        level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 0.7F, 1.2F + level.getRandom().nextFloat() * 0.2F);
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
