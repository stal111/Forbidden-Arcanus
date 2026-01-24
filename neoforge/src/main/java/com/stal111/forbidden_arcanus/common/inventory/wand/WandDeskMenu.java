package com.stal111.forbidden_arcanus.common.inventory.wand;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.transfer.UnmodifiableSlotResourceHandler;
import com.stal111.forbidden_arcanus.common.item.MagicWandItem;
import com.stal111.forbidden_arcanus.common.item.wand.WandMaterial;
import com.stal111.forbidden_arcanus.common.item.wand.WandPart;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import com.stal111.forbidden_arcanus.core.init.other.ModMenuTypes;
import net.minecraft.core.Holder;
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
    private final UnmodifiableSlotResourceHandler unmodifiableSlotResourceHandler;
    private final Container inputsContainer;

    public WandDeskMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, ContainerLevelAccess.NULL, new UnmodifiableSlotResourceHandler(true));
    }

    public WandDeskMenu(int containerId, Inventory inventory, ContainerLevelAccess levelAccess, UnmodifiableSlotResourceHandler resultResourceHandler) {
        super(ModMenuTypes.WAND_DESK.get(), containerId);
        this.levelAccess = levelAccess;
        this.unmodifiableSlotResourceHandler = resultResourceHandler;

        this.addSlot(new ResourceHandlerSlot(resultResourceHandler, resultResourceHandler::set, 0, 80, 66));

        this.inputsContainer = new SimpleContainer(3) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }

            @Override
            public boolean canPlaceItem(int slot, ItemStack stack) {
                Holder<WandMaterial> material = stack.get(ModDataComponents.PROVIDES_WAND_MATERIAL);

                return switch (slot) {
                    case 0 -> material != null && material.value().wandPart() == WandPart.POMMEL;
                    case 1 -> material != null && material.value().wandPart() == WandPart.TRANSITION;
                    case 2 -> !stack.has(ModDataComponents.POMMEL_MATERIAL) && !stack.has(ModDataComponents.TRANSITION_MATERIAL) && stack.getItem() instanceof MagicWandItem;
                    default -> false;
                };
            }
        };

        this.addSlot(new InputSlot(this.inputsContainer, 0, 44, 44).setBackground(ForbiddenArcanus.identifier("slot/wand_pommel_material")));
        this.addSlot(new InputSlot(this.inputsContainer, 1, 116, 44).setBackground(ForbiddenArcanus.identifier("slot/wand_transition_material")));
        this.addSlot(new InputSlot(this.inputsContainer, 2, 80, 107).setBackground(ForbiddenArcanus.identifier("slot/wand_base_material")));

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
        Holder<WandMaterial> pommelMaterial = this.inputsContainer.getItem(0).get(ModDataComponents.PROVIDES_WAND_MATERIAL);
        Holder<WandMaterial> transitionMaterial = this.inputsContainer.getItem(1).get(ModDataComponents.PROVIDES_WAND_MATERIAL);
        ItemStack baseMaterial = this.inputsContainer.getItem(2);

        ItemStack stack = this.unmodifiableSlotResourceHandler.getStack();

        if (!baseMaterial.isEmpty()) {
            stack = stack.transmuteCopy(baseMaterial.getItem());
        }

        if (pommelMaterial != null) {
            stack.set(ModDataComponents.POMMEL_MATERIAL, pommelMaterial);
        }

        if (transitionMaterial != null) {
            stack.set(ModDataComponents.TRANSITION_MATERIAL, transitionMaterial);
        }

        this.unmodifiableSlotResourceHandler.setStack(stack);

        this.inputsContainer.clearContent();
    }

    private static class InputSlot extends Slot {
        public InputSlot(Container inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack itemStack) {
            return this.container.canPlaceItem(this.getSlotIndex(), itemStack);
        }
    }
}
