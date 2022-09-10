package com.stal111.forbidden_arcanus.common.inventory;

import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeLevel;
import com.stal111.forbidden_arcanus.core.init.other.ModContainers;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

/**
 * Hephaestus Forge Menu <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.inventory.HephaestusForgeMenu
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-06-28
 */
public class HephaestusForgeMenu extends AbstractContainerMenu {

    private final ContainerData hephaestusForgeData;
    private final Container container;

    public HephaestusForgeMenu(int id, Inventory player) {
        this(id, new SimpleContainer(9), new SimpleContainerData(6), player);
    }

    public HephaestusForgeMenu(int id, Container container, ContainerData containerData, Inventory player) {
        super(ModContainers.HEPHAESTUS_FORGE.get(), id);
        this.container = container;
        this.hephaestusForgeData = containerData;

        checkContainerDataCount(this.hephaestusForgeData, 5);
        this.addDataSlots(this.hephaestusForgeData);

        // Hephaestus Forge Slots
        this.addSlot(new EnhancerSlot(new ItemStackHandler(3), 0, 32, 24, HephaestusForgeLevel.getRequiredLevelForSlot(1).getName()));
        this.addSlot(new EnhancerSlot(new ItemStackHandler(3), 1, 32, 46, HephaestusForgeLevel.getRequiredLevelForSlot(2).getName()));
        this.addSlot(new EnhancerSlot(new ItemStackHandler(3), 2, 128, 24, HephaestusForgeLevel.getRequiredLevelForSlot(3).getName()));
        this.addSlot(new EnhancerSlot(new ItemStackHandler(3), 3, 128, 46, HephaestusForgeLevel.getRequiredLevelForSlot(4).getName()));

        HephaestusForgeLevel level = HephaestusForgeLevel.getFromIndex(this.hephaestusForgeData.get(0));

        if (level.getEnhancerSlots() < 4) {
            ((EnhancerSlot) this.getSlot(3)).setUnlocked(false);

            if (level.getEnhancerSlots() < 3) {
                ((EnhancerSlot) this.getSlot(2)).setUnlocked(false);

                if (level.getEnhancerSlots() < 2) {
                    ((EnhancerSlot) this.getSlot(1)).setUnlocked(false);
                }
            }
        }

        // Main Slot
        this.addSlot(new MainSlot(container, 4, 80, 24));

        // Input Slots
        this.addSlot(new InputSlot(container, 5, 8 - 26, 25, InputType.AUREAL));
        this.addSlot(new InputSlot(container, 6, 8 - 26, 43, InputType.SOULS));
        this.addSlot(new InputSlot(container, 7, 176 + 2, 25, InputType.BLOOD));
        this.addSlot(new InputSlot(container, 8, 176 + 2, 43, InputType.EXPERIENCE));

        // Inventory Slots
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(player, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        // Hotbar Slots
        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(player, k, 8 + k * 18, 142));
        }
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(@Nonnull Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            result = stack.copy();

            if (index <= 4) {
                if (!this.moveItemStackTo(stack, 5, 41, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(stack, result);
            } else {
                if (!this.slots.get(4).hasItem()) {
                    if (!this.moveItemStackTo(stack, 4, 5, false)) {
                        return ItemStack.EMPTY;
                    }

                    slot.onQuickCraft(stack, result);
                } else {
                    if (!this.moveItemStackTo(stack, 0, 4, false)) {
                        if (index < 36) {
                            if (!this.moveItemStackTo(stack, 36, 45, false)) {
                                return ItemStack.EMPTY;
                            }
                        } else if (this.moveItemStackTo(stack, 5, 36, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }
            }

            slot.onTake(player, stack);
        }

        return result;
    }

    @Override
    public boolean stillValid(@Nonnull Player player) {
        return this.container.stillValid(player);
    }

    public ContainerData getHephaestusForgeData() {
        return hephaestusForgeData;
    }
}
