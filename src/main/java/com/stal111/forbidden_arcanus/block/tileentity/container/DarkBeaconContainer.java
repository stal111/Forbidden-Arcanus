package com.stal111.forbidden_arcanus.block.tileentity.container;

import javax.annotation.Nullable;

import com.stal111.forbidden_arcanus.block.ModBlocks;
import com.stal111.forbidden_arcanus.block.tileentity.DarkBeaconTileEntity;
import com.stal111.forbidden_arcanus.item.ModItems;
import com.stal111.forbidden_arcanus.util.GuiTile;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DarkBeaconContainer extends ModContainer {

	private final IInventory tileBeacon = new Inventory(1) {
		/**
		 * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
		 * guis use Slot.isItemValid
		 */
		public boolean isItemValidForSlot(int index, ItemStack stack) {
			return stack.isBeaconPayment();
		}

		/**
		 * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
		 */
		public int getInventoryStackLimit() {
			return 1;
		}
	};
	private final DarkBeaconContainer.BeaconSlot beaconSlot;
	private final IIntArray field_216972_f;

	public DarkBeaconContainer(int id, World world, BlockPos pos, PlayerInventory inventory, PlayerEntity playerEntity) {
		super(ModContainers.dark_beacon, id, world, pos, inventory, playerEntity, 1);
		assertIntArraySize(new IntArray(3), 3);
		this.field_216972_f = new IntArray(3);
		this.beaconSlot = new DarkBeaconContainer.BeaconSlot(this.tileBeacon, 0, 136, 110);
		this.addSlot(this.beaconSlot);
		this.trackIntArray(new IntArray(3));

		for(int k = 0; k < 3; ++k) {
			for(int l = 0; l < 9; ++l) {
				this.addSlot(new Slot(inventory, l + k * 9 + 9, 36 + l * 18, 137 + k * 18));
			}
		}

		for(int i1 = 0; i1 < 9; ++i1) {
			this.addSlot(new Slot(inventory, i1, 36 + i1 * 18, 195));
		}
	}

	/**
	 * Called when the container is closed.
	 */
	public void onContainerClosed(PlayerEntity playerIn) {
		super.onContainerClosed(playerIn);
		if (!playerIn.world.isRemote) {
			ItemStack itemstack = this.beaconSlot.decrStackSize(this.beaconSlot.getSlotStackLimit());
			if (!itemstack.isEmpty()) {
				playerIn.dropItem(itemstack, false);
			}

		}
	}

	public void updateProgressBar(int id, int data) {
		super.updateProgressBar(id, data);
		this.detectAndSendChanges();
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
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
			} else if (this.mergeItemStack(itemstack1, 0, 1, false)) { //Forge Fix Shift Clicking in beacons with stacks larger then 1.
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

	@OnlyIn(Dist.CLIENT)
	public int func_216969_e() {
		return this.field_216972_f.get(0);
	}

	@Nullable
	@OnlyIn(Dist.CLIENT)
	public Effect func_216967_f() {
		return Effect.get(this.field_216972_f.get(1));
	}

	@Nullable
	@OnlyIn(Dist.CLIENT)
	public Effect func_216968_g() {
		return Effect.get(this.field_216972_f.get(2));
	}

	public void func_216966_c(int p_216966_1_, int p_216966_2_) {
		if (this.beaconSlot.getHasStack()) {
			this.field_216972_f.set(1, p_216966_1_);
			this.field_216972_f.set(2, p_216966_2_);
			this.beaconSlot.decrStackSize(1);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public boolean func_216970_h() {
		return !this.tileBeacon.getStackInSlot(0).isEmpty();
	}

	class BeaconSlot extends Slot {
		public BeaconSlot(IInventory inventoryIn, int index, int xIn, int yIn) {
			super(inventoryIn, index, xIn, yIn);
		}

		/**
		 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
		 */
		public boolean isItemValid(ItemStack stack) {
			return stack.getItem() == ModItems.arcane_crystal;
		}

		/**
		 * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the
		 * case of armor slots)
		 */
		public int getSlotStackLimit() {
			return 1;
		}
	}
}