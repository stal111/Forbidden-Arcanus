package com.stal111.forbidden_arcanus.common.inventory;

import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoMainBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MaterialStorage;
import com.stal111.forbidden_arcanus.common.block.entity.transfer.FuelItemHandler;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.other.ModMenuTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;

public class ClibanoMenu extends AbstractContainerMenu {

    private final ContainerLevelAccess levelAccess;
    private final MaterialStorage materialStorage;

    private final ContainerData data;

    public ClibanoMenu(int containerId, Inventory playerInventory, RegistryFriendlyByteBuf buffer) {
        this(containerId, playerInventory, new FuelItemHandler(stack -> ClibanoMainBlockEntity.getBurnDuration(stack, playerInventory.player.level()) > 0, stack -> {}), new ItemStacksResourceHandler(2), new SimpleContainerData(2), ContainerLevelAccess.NULL, MaterialStorage.STREAM_CODEC.decode(buffer));
    }

    public ClibanoMenu(int containerId, Inventory playerInventory, FuelItemHandler fuelHandler, ItemStacksResourceHandler inputInventory, ContainerData data, ContainerLevelAccess levelAccess, MaterialStorage materialStorage) {
        super(ModMenuTypes.CLIBANO.get(), containerId);
        this.levelAccess = levelAccess;
        this.materialStorage = materialStorage;

        checkContainerDataCount(data, 2);
        this.data = data;

        this.addDataSlots(data);

        this.addSlot(new ResourceHandlerSlot(fuelHandler, fuelHandler::set, 0, 38, 56));
        this.addSlot(new ResourceHandlerSlot(inputInventory, inputInventory::set, 0, 29, 20));
        this.addSlot(new ResourceHandlerSlot(inputInventory, inputInventory::set, 1, 47, 20));

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

    public float getLitProgress() {
        int litTotalTime = this.data.get(1);

        if (litTotalTime <= 0) {
            return 0.0F;
        }

        return Mth.clamp((float) this.data.get(0) / litTotalTime, 0.0F, 1.0F);
    }
}
