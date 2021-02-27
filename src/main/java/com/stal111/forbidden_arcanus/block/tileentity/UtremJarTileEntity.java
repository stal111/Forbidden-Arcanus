package com.stal111.forbidden_arcanus.block.tileentity;

import com.stal111.forbidden_arcanus.init.ModTileEntities;
import com.stal111.forbidden_arcanus.util.FluidTankTile;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
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
public class UtremJarTileEntity extends TileEntity {

    protected FluidTank tank = new FluidTankTile(FluidAttributes.BUCKET_VOLUME, this);

    private final LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> tank);

    public UtremJarTileEntity() {
        super(ModTileEntities.UTREM_JAR.get());
    }

    public FluidTank getTank() {
        return this.tank;
    }

    @Override
    public void read(BlockState state, CompoundNBT tag) {
        super.read(state, tag);
        if (tag.contains("Fluid")) {
            this.tank.readFromNBT(tag.getCompound("Fluid"));
        }

        if (!tank.getFluid().isEmpty() && world != null) {
            world.getLightManager().checkBlock(pos);
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag = super.write(tag);

        if (!this.tank.getFluid().isEmpty()) {
            CompoundNBT fluid = this.tank.writeToNBT(new CompoundNBT());
            tag.put("Fluid", fluid);
        }

        return tag;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 0, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
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
