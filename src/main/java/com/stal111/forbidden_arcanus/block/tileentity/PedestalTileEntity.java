package com.stal111.forbidden_arcanus.block.tileentity;

import com.stal111.forbidden_arcanus.init.ModTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
    private int ticksExisted;
    private int itemHeight = 110;

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
        this.setItemHeight(110);
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

    public int getItemHeight() {
        return this.itemHeight;
    }

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
    }

    @Override
    public void read(@Nonnull BlockState state, @Nonnull CompoundNBT compound) {
        super.read(state, compound);
        if (compound.contains("Stack")) {
            this.stack = ItemStack.read(compound.getCompound("Stack"));
            this.itemHeight = compound.getInt("ItemHeight");
        }
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        super.write(compound);

        if (this.stack != ItemStack.EMPTY) {
            compound.put("Stack", this.stack.write(new CompoundNBT()));
            compound.putInt("ItemHeight", this.itemHeight);
        }

        return compound;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 0, this.getUpdateTag());
    }

    @Nonnull
    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager networkManager, SUpdateTileEntityPacket packet) {
        if (this.world != null) {
            this.read(this.world.getBlockState(packet.getPos()), packet.getNbtCompound());
        }
    }
}
