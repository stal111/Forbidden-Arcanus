package com.stal111.forbidden_arcanus.common.block.entity.clibano;

import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

/**
 * Clibano Block Entity <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoBlockEntity
 *
 * @author stal111
 * @since 2022-05-22
 */
public class ClibanoBlockEntity extends BlockEntity {

    /**
     * The Block State that was at that position in the world before the BE got created. After destroying it this block will be placed again.
     */
    private BlockState replaceState;

    @Nullable
    private Direction mainDirection;

    public ClibanoBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CLIBANO.get(), pos, state);
    }

    public void setReplaceState(BlockState state) {
        this.replaceState = state;
    }

    public BlockState getReplaceState() {
        return this.replaceState;
    }

    public void setMainDirection(@Nullable Direction mainDirection) {
        this.mainDirection = mainDirection;
    }

    @Override
    protected void saveAdditional(@Nonnull CompoundTag tag) {
        super.saveAdditional(tag);

        if (this.replaceState != null) {
            tag.put("State", NbtUtils.writeBlockState(this.replaceState));
        }

        if (this.mainDirection != null) {
            tag.putString("MainDirection", this.mainDirection.getName());
        }
    }

    @Override
    public void load(@Nonnull CompoundTag tag) {
        super.load(tag);

        if (tag.contains("State")) {
            this.replaceState = NbtUtils.readBlockState(tag.getCompound("State"));
        }

        if (tag.contains("MainDirection")) {
            this.mainDirection = Direction.byName(tag.getString("MainDirection"));
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (this.level == null || this.mainDirection == null || this.getBlockState().getBlock() != ModBlocks.CLIBANO_CENTER.get()) {
            return super.getCapability(cap, side);
        }

        BlockEntity blockEntity = this.level.getBlockEntity(this.worldPosition.relative(this.mainDirection));

        if (blockEntity == null) {
            return super.getCapability(cap, side);
        }

        return blockEntity.getCapability(cap, side);
    }
}
