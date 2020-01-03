package com.stal111.forbidden_arcanus.block.tileentity.container;

import com.stal111.forbidden_arcanus.util.GuiTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ModContainer extends Container {

    public TileEntity tile;
    private PlayerEntity player;
    private PlayerInventory inventory;
    public final int SIZE;

    protected ModContainer(@Nullable ContainerType<?> type, int id, World world, BlockPos pos, PlayerInventory inventory, PlayerEntity player, int size) {
        super(type, id);
        this.tile = world.getTileEntity(pos);
        this.player = player;
        this.inventory = inventory;
        this.SIZE = size;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    protected void addPlayerSlots() {
        for (int row = 0; row <= 2; row++) {
            for (int col = 0; col <= 8; col++){
                int x = 8 + col * 18;
                int y = row * 18 + ((GuiTile)tile).getHeight() - 82;
                this.addSlot(new Slot(inventory, col + row * 9 + 9, x, y));
            }
        }

        for (int row = 0; row <= 8; row++){
            int x = 8 + row * 18;
            int y = ((GuiTile)tile).getHeight() - 24;
            this.addSlot(new Slot(inventory, row, x, y));
        }
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);//[index];
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < SIZE) {
                if (!this.mergeItemStack(itemstack1, SIZE, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, SIZE, false)) return ItemStack.EMPTY;

            if (itemstack1.getCount() <= 0) slot.putStack(ItemStack.EMPTY);
            else slot.onSlotChanged();
        }
        return itemstack;
    }
}
