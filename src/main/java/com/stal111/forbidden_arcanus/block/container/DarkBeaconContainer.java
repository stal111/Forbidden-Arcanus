package com.stal111.forbidden_arcanus.block.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DarkBeaconContainer extends Container {

	private final IInventory tileArcaneBeacon;

	private final DarkBeaconContainer.BeaconSlot beaconSlot;

	public DarkBeaconContainer(IInventory playerInventory, IInventory tileBeaconIn) {
		this.tileArcaneBeacon = tileBeaconIn;
		this.beaconSlot = new DarkBeaconContainer.BeaconSlot(tileBeaconIn, 0, 136, 110);
		this.addSlot(this.beaconSlot);

		for (int k = 0; k < 3; ++k) {
			for (int l = 0; l < 9; ++l) {
				this.addSlot(new Slot(playerInventory, l + k * 9 + 9, 36 + l * 18, 137 + k * 18));
			}
		}

		for (int i1 = 0; i1 < 9; ++i1) {
			this.addSlot(new Slot(playerInventory, i1, 36 + i1 * 18, 195));
		}
	}

	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendAllWindowProperties(this, this.tileArcaneBeacon);
	}

	@OnlyIn(Dist.CLIENT)
	public void updateProgressBar(int id, int data) {
		this.tileArcaneBeacon.setField(id, data);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (index == 0) {
				if (!this.mergeItemStack(itemstack1, 1, 37, true)) {
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (this.mergeItemStack(itemstack1, 0, 1, false)) {
				return ItemStack.EMPTY;
			} else if (index >= 1 && index < 28) {
				if (!this.mergeItemStack(itemstack1, 28, 37, false)) {
					return ItemStack.EMPTY;
				}
			} else if (index >= 28 && index < 37) {
				if (!this.mergeItemStack(itemstack1, 1, 28, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 1, 37, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemstack1);
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.tileArcaneBeacon.isUsableByPlayer(playerIn);
	}

	class BeaconSlot extends Slot {
		public BeaconSlot(IInventory inventoryIn, int index, int xIn, int yIn) {
			super(inventoryIn, index, xIn, yIn);
		}

		/**
		 * Check if the stack is allowed to be placed in this slot, used for
		 * armor slots as well as furnace fuel.
		 */
		public boolean isItemValid(ItemStack stack) {
			return stack.isBeaconPayment();
		}

		/**
		 * Returns the maximum stack size for a given slot (usually the same as
		 * getInventoryStackLimit(), but 1 in the case of armor slots)
		 */
		public int getSlotStackLimit() {
			return 1;
		}
	}
}
