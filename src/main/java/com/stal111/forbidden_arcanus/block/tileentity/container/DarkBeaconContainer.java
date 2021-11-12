package com.stal111.forbidden_arcanus.block.tileentity.container;

import javax.annotation.Nullable;

import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DarkBeaconContainer extends ModContainer {

	private final Container tileBeacon = new SimpleContainer(1) {
		/**
		 * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
		 * guis use Slot.isItemValid
		 */
		public boolean canPlaceItem(int index, ItemStack stack) {
			return false;
		}

		/**
		 * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
		 */
		public int getMaxStackSize() {
			return 1;
		}
	};
	private final DarkBeaconContainer.BeaconSlot beaconSlot;
	private final ContainerData beaconData;

	public DarkBeaconContainer(int id, Level world, BlockPos pos, Inventory inventory, Player playerEntity) {
		super(ModContainers.dark_beacon, id, world, pos, inventory, playerEntity, 1);
		checkContainerDataCount(new SimpleContainerData(3), 3);
		this.beaconData = new SimpleContainerData(3);
		this.beaconSlot = new DarkBeaconContainer.BeaconSlot(this.tileBeacon, 0, 136, 110);
		this.addSlot(this.beaconSlot);
		this.addDataSlots(new SimpleContainerData(3));

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
	public void removed(Player playerIn) {
		super.removed(playerIn);
		if (!playerIn.level.isClientSide) {
			ItemStack itemstack = this.beaconSlot.remove(this.beaconSlot.getMaxStackSize());
			if (!itemstack.isEmpty()) {
				playerIn.drop(itemstack, false);
			}

		}
	}

	public void setData(int id, int data) {
		super.setData(id, data);
		this.broadcastChanges();
	}

	@Override
	public ItemStack quickMoveStack(Player playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if (index == 0) {
				if (!this.moveItemStackTo(itemstack1, 1, 37, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickCraft(itemstack1, itemstack);
			} else if (this.moveItemStackTo(itemstack1, 0, 1, false)) { //Forge Fix Shift Clicking in beacons with stacks larger then 1.
				return ItemStack.EMPTY;
			} else if (index >= 1 && index < 28) {
				if (!this.moveItemStackTo(itemstack1, 28, 37, false)) {
					return ItemStack.EMPTY;
				}
			} else if (index >= 28 && index < 37) {
				if (!this.moveItemStackTo(itemstack1, 1, 28, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(itemstack1, 1, 37, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemstack1);
		}

		return itemstack;
	}

	@OnlyIn(Dist.CLIENT)
	public int getLevels() {
		return this.beaconData.get(0);
	}

	@Nullable
	@OnlyIn(Dist.CLIENT)
	public MobEffect getPrimaryEffect() {
		return MobEffect.byId(this.beaconData.get(1));
	}

	@Nullable
	@OnlyIn(Dist.CLIENT)
	public MobEffect getSecondaryEffect() {
		return MobEffect.byId(this.beaconData.get(2));
	}

	public void updateEffects(int p_216966_1_, int p_216966_2_) {
		if (this.beaconSlot.hasItem()) {
			this.beaconData.set(1, p_216966_1_);
			this.beaconData.set(2, p_216966_2_);
			this.beaconSlot.remove(1);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public boolean hasPayment() {
		return !this.tileBeacon.getItem(0).isEmpty();
	}

	class BeaconSlot extends Slot {
		public BeaconSlot(Container inventoryIn, int index, int xIn, int yIn) {
			super(inventoryIn, index, xIn, yIn);
		}

		/**
		 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
		 */
		public boolean mayPlace(ItemStack stack) {
			return stack.getItem() == ModItems.ARCANE_CRYSTAL.get();
		}

		/**
		 * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the
		 * case of armor slots)
		 */
		public int getMaxStackSize() {
			return 1;
		}
	}
}