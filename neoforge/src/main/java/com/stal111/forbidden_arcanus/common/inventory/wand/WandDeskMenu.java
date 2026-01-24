package com.stal111.forbidden_arcanus.common.inventory.wand;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.transfer.ResultSlotResourceHandler;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.other.ModMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;

public class WandDeskMenu extends AbstractContainerMenu {

    private final ContainerLevelAccess levelAccess;
    private final Container inputsContainer;

    public WandDeskMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, ContainerLevelAccess.NULL, new ResultSlotResourceHandler(true));
    }

    public WandDeskMenu(int containerId, Inventory inventory, ContainerLevelAccess levelAccess, ResultSlotResourceHandler resultResourceHandler) {
        super(ModMenuTypes.WAND_DESK.get(), containerId);
        this.levelAccess = levelAccess;

        this.addSlot(new ResourceHandlerSlot(resultResourceHandler, resultResourceHandler::set, 0, 80, 66));

        this.inputsContainer = new SimpleContainer(3) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        };

        this.addSlot(new Slot(this.inputsContainer, 0, 44, 44).setBackground(ForbiddenArcanus.identifier("slot/wand_pommel_material")));
        this.addSlot(new Slot(this.inputsContainer, 1, 80, 107).setBackground(ForbiddenArcanus.identifier("slot/wand_base_material")));
        this.addSlot(new Slot(this.inputsContainer, 2, 116, 44).setBackground(ForbiddenArcanus.identifier("slot/wand_transition_material")));

        this.addStandardInventorySlots(inventory, 8, 149);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.levelAccess, player, ModBlocks.WAND_DESK.get());
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.levelAccess.execute((_, _) -> this.clearContainer(player, this.inputsContainer));
    }

    public boolean canCraftWand() {
        return !this.inputsContainer.isEmpty();
    }

    public void craftWand() {
        this.inputsContainer.clearContent();
    }
}
