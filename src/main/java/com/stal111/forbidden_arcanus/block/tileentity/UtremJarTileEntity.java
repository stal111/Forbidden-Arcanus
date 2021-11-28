package com.stal111.forbidden_arcanus.block.tileentity;

import com.stal111.forbidden_arcanus.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.util.FluidTankTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Utrem Jar Tile Entity
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.block.tileentity.UtremJarTileEntity
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-19
 */
public class UtremJarTileEntity extends BlockEntity {

    protected FluidTank tank = new FluidTankTile(FluidAttributes.BUCKET_VOLUME, this);

    private final LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> tank);

    public UtremJarTileEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.UTREM_JAR.get(), pos, state);
    }

    public FluidTank getTank() {
        return this.tank;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("Fluid")) {
            this.tank.readFromNBT(tag.getCompound("Fluid"));
        }

        if (!tank.getFluid().isEmpty() && level != null) {
            level.getLightEngine().checkBlock(worldPosition);
        }
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag = super.save(tag);

        if (!this.tank.getFluid().isEmpty()) {
            CompoundTag fluid = this.tank.writeToNBT(new CompoundTag());
            tag.put("Fluid", fluid);
        }

        return tag;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return new ClientboundBlockEntityDataPacket(this.worldPosition, 0, this.getUpdateTag());
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.save(new CompoundTag());
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return holder.cast();
        }
        return super.getCapability(capability, facing);
    }
}
