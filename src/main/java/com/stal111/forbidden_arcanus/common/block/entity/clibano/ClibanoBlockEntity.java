package com.stal111.forbidden_arcanus.common.block.entity.clibano;

import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

/**
 * Clibano Block Entity <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoBlockEntity
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 * @since 2022-05-22
 */
public class ClibanoBlockEntity extends BlockEntity {

    private BlockState state;

    public ClibanoBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.CLIBANO.get(), worldPosition, blockState);
    }

    public void setState(BlockState state) {
        this.state = state;
    }

    public BlockState getState() {
        return this.state;
    }

    @Override
    protected void saveAdditional(@Nonnull CompoundTag tag) {
        super.saveAdditional(tag);

        if (this.state != null) {
            tag.put("State", NbtUtils.writeBlockState(this.state));
        }
    }

    @Override
    public void load(@Nonnull CompoundTag tag) {
        super.load(tag);

        if (tag.contains("State")) {
            this.state = NbtUtils.readBlockState(tag.getCompound("State"));
        }
    }
}
