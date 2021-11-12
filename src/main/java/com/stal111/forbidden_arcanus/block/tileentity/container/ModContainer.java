package com.stal111.forbidden_arcanus.block.tileentity.container;

import com.stal111.forbidden_arcanus.util.GuiTile;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class ModContainer extends AbstractContainerMenu {

    public BlockEntity tile;
    private Player player;
    private Inventory inventory;
    public final int SIZE;

    protected ModContainer(@Nullable MenuType<?> type, int id, Level world, BlockPos pos, Inventory inventory, Player player, int size) {
        super(type, id);
        this.tile = world.getBlockEntity(pos);
        this.player = player;
        this.inventory = inventory;
        this.SIZE = size;
    }

    @Override
    public boolean stillValid(Player playerIn) {
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
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);//[index];
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            if (index < SIZE) {
                if (!this.moveItemStackTo(itemstack1, SIZE, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, SIZE, false)) return ItemStack.EMPTY;

            if (itemstack1.getCount() <= 0) slot.set(ItemStack.EMPTY);
            else slot.setChanged();
        }
        return itemstack;
    }
}
