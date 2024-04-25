package com.stal111.forbidden_arcanus.common.block.entity;

import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.util.FluidTankTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Utrem Jar Block Entity <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.entity.UtremJarBlockEntity
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2021-02-19
 */
public class UtremJarBlockEntity extends BlockEntity {

    private final FluidTank tank = new FluidTankTile(FluidType.BUCKET_VOLUME, this);

    public UtremJarBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.UTREM_JAR.get(), pos, state);
    }

    public FluidTank getTank() {
        return this.tank;
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider lookupProvider) {
        super.loadAdditional(tag, lookupProvider);

        if (tag.contains("Fluid")) {
            this.tank.readFromNBT(lookupProvider, tag.getCompound("Fluid"));
        }

        if (!tank.getFluid().isEmpty() && level != null) {
            level.getLightEngine().checkBlock(worldPosition);
        }
    }

    @Override
    public void saveAdditional(@Nonnull CompoundTag tag, HolderLookup.@NotNull Provider lookupProvider) {
        super.saveAdditional(tag, lookupProvider);

        if (!this.tank.getFluid().isEmpty()) {
            tag.put("Fluid", this.tank.writeToNBT(lookupProvider, new CompoundTag()));
        }
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Nonnull
    @Override
    public CompoundTag getUpdateTag(HolderLookup.@NotNull Provider lookupProvider) {
        return this.saveWithoutMetadata(lookupProvider);
    }
}
