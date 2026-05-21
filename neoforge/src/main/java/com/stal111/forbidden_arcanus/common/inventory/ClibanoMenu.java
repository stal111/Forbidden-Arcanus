package com.stal111.forbidden_arcanus.common.inventory;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFireType;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoMainBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MaterialStorage;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.SelectedMaterialState;
import com.stal111.forbidden_arcanus.common.block.entity.transfer.EssenceInputResourceHandler;
import com.stal111.forbidden_arcanus.common.block.entity.transfer.FuelItemHandler;
import com.stal111.forbidden_arcanus.common.block.entity.transfer.ResultSlotItemHandler;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceStorage;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceStorages;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.other.ModMenuTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;

public class ClibanoMenu extends AbstractContainerMenu {

    public static final Identifier FUEL_SLOT_BACKGROUND = ForbiddenArcanus.identifier("slot/clibano/fuel");
    public static final Identifier ECTOPLASM_SLOT_BACKGROUND = ForbiddenArcanus.identifier("slot/clibano/ectoplasm");

    private final ContainerLevelAccess levelAccess;
    private final MaterialStorage materialStorage;
    private final SelectedMaterialState selectedMaterialState;

    private final ContainerData data;

    public ClibanoMenu(int containerId, Inventory playerInventory, RegistryFriendlyByteBuf buffer) {
        this(containerId, playerInventory, new FuelItemHandler(stack -> ClibanoMainBlockEntity.getBurnDuration(stack, playerInventory.player.level()) > 0, stack -> {}), new ItemStacksResourceHandler(2), new EssenceInputResourceHandler(EssenceType.ECTOPLASM), new ResultSlotItemHandler(), new SimpleContainerData(ClibanoMainBlockEntity.DATA_COUNT), ContainerLevelAccess.NULL, MaterialStorage.STREAM_CODEC.decode(buffer), SelectedMaterialState.STREAM_CODEC.decode(buffer));
    }

    public ClibanoMenu(int containerId, Inventory playerInventory, FuelItemHandler fuelHandler, ItemStacksResourceHandler inputInventory, ItemStacksResourceHandler essenceInputInventory, ResultSlotItemHandler resultInventory, ContainerData data, ContainerLevelAccess levelAccess, MaterialStorage materialStorage, SelectedMaterialState selectedMaterialState) {
        super(ModMenuTypes.CLIBANO.get(), containerId);
        this.levelAccess = levelAccess;
        this.materialStorage = materialStorage;
        this.selectedMaterialState = selectedMaterialState;

        checkContainerDataCount(data, ClibanoMainBlockEntity.DATA_COUNT);
        this.data = data;

        this.addDataSlots(data);

        this.addSlot(new ResourceHandlerSlot(fuelHandler, fuelHandler::set, 0, 80, 103).setBackground(FUEL_SLOT_BACKGROUND));
        this.addSlot(new ResourceHandlerSlot(inputInventory, inputInventory::set, 0, 71, 21));
        this.addSlot(new ResourceHandlerSlot(inputInventory, inputInventory::set, 1, 89, 21));
        this.addSlot(new ResourceHandlerSlot(essenceInputInventory, essenceInputInventory::set, 0, 46, 62).setBackground(ECTOPLASM_SLOT_BACKGROUND));
        this.addSlot(new ResourceHandlerSlot(resultInventory, resultInventory::set, 0, 118, 81));

        this.addStandardInventorySlots(playerInventory, 8, 139);
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

    public SelectedMaterialState getSelectedMaterialState() {
        return this.selectedMaterialState;
    }

    public float getLitProgress() {
        int litTotalTime = this.data.get(ClibanoMainBlockEntity.DATA_LIT_TOTAL_TIME);

        if (litTotalTime <= 0) {
            return 0.0F;
        }

        return Mth.clamp((float) this.data.get(ClibanoMainBlockEntity.DATA_LIT_TIME_REMAINING) / litTotalTime, 0.0F, 1.0F);
    }

    public float getEctoplasmProgress() {
        return Mth.clamp((float) this.data.get(ClibanoMainBlockEntity.DATA_ECTOPLASM_TIME_REMAINING) / ClibanoMainBlockEntity.ECTOPLASM_DURATION, 0.0F, 1.0F);
    }

    public float getResultProgress() {
        return Mth.clamp((float) this.data.get(ClibanoMainBlockEntity.DATA_RESULT_PROGRESS) / ClibanoMainBlockEntity.RESULT_TIME, 0.0F, 1.0F);
    }

    public int[] getCookingTimes() {
        return new int[]{this.data.get(ClibanoMainBlockEntity.DATA_COOKING_TIME_1), this.data.get(ClibanoMainBlockEntity.DATA_COOKING_TIME_2)};
    }

    public int[] getCookingTotalTimes() {
        return new int[]{this.data.get(ClibanoMainBlockEntity.DATA_COOKING_TOTAL_TIME_1), this.data.get(ClibanoMainBlockEntity.DATA_COOKING_TOTAL_TIME_2)};
    }

    public ClibanoFireType getFireType() {
        return ClibanoFireType.values()[this.data.get(ClibanoMainBlockEntity.DATA_FIRE_TYPE)];
    }

    public EssenceStorage getEssenceStorage() {
        return EssenceStorages.CLIBANO_ECTOPLASM_EMPTY.setAmount(this.data.get(ClibanoMainBlockEntity.DATA_ECTOPLASM_AMOUNT));
    }
}
