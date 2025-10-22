package com.stal111.forbidden_arcanus.common.inventory;

import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MaterialStorage;
import com.stal111.forbidden_arcanus.common.block.entity.transfer.FuelItemHandler;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.other.ModMenuTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;

public class ClibanoMenu extends AbstractContainerMenu {

    private final ContainerLevelAccess levelAccess;
    private final MaterialStorage materialStorage;

    public ClibanoMenu(int containerId, Inventory playerInventory, RegistryFriendlyByteBuf buffer) {
        this(containerId, playerInventory, new FuelItemHandler(stack -> stack.getBurnTime(RecipeType.SMELTING, playerInventory.player.level().fuelValues()) > 0, stack -> {}), ContainerLevelAccess.NULL, MaterialStorage.STREAM_CODEC.decode(buffer));
    }

    public ClibanoMenu(int containerId, Inventory playerInventory, FuelItemHandler fuelHandler, ContainerLevelAccess levelAccess, MaterialStorage materialStorage) {
        super(ModMenuTypes.CLIBANO.get(), containerId);
        this.levelAccess = levelAccess;
        this.materialStorage = materialStorage;

        this.addSlot(new ResourceHandlerSlot(fuelHandler, fuelHandler::set, 0, 38, 56));

        this.addStandardInventorySlots(playerInventory, 8, 91);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (!slot.hasItem()) {
            return result;
        }

        ItemStack stack = slot.getItem();
        result = stack.copy();

        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        if (stack.getCount() == result.getCount()) {
            return ItemStack.EMPTY;
        }

        slot.onTake(player, stack);

        return result;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.levelAccess, player, ModBlocks.CLIBANO_MAIN_PART.get());
    }

    public MaterialStorage getStoredMaterials() {
        return this.materialStorage;
    }
}
