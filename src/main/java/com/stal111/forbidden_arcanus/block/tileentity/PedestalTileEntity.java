package com.stal111.forbidden_arcanus.block.tileentity;

import com.stal111.forbidden_arcanus.init.ModTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;

/**
 * Pedestal Tile Entity
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.block.tileentity.PedestalTileEntity
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-06-25
 */
public class PedestalTileEntity extends TileEntity implements ITickableTileEntity {

    private ItemStack stack = ItemStack.EMPTY;

    private final float hoverStart;
    private int ticksExisted = 0;

    public PedestalTileEntity() {
        super(ModTileEntities.PEDESTAL.get());
        this.hoverStart = (float) (Math.random() * Math.PI * 2.0D);
    }

    @Override
    public void tick() {
        this.ticksExisted++;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }

    public ItemStack getStack() {
        return this.stack;
    }

    public boolean hasStack() {
        return !this.stack.isEmpty();
    }

    public void clearStack() {
        this.stack = ItemStack.EMPTY;
    }

    public int getTicksExisted() {
        return ticksExisted;
    }

    public float getHoverStart() {
        return hoverStart;
    }

    public float getItemHover(float partialTicks) {
        return (this.getTicksExisted() + partialTicks) / 20.0F + this.getHoverStart();
    }

    @Override
    public void read(@Nonnull BlockState state, @Nonnull CompoundNBT nbt) {
        super.read(state, nbt);
        if (nbt.contains("Stack")) {
            this.stack = ItemStack.read(nbt.getCompound("Stack"));
        }
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        super.write(compound);

        if (this.stack != ItemStack.EMPTY) {
            compound.put("Stack", this.stack.write(new CompoundNBT()));
        }

        return compound;
    }
}
