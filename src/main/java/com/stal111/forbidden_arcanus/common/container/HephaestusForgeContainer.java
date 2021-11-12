package com.stal111.forbidden_arcanus.common.container;

import com.stal111.forbidden_arcanus.common.tile.forge.HephaestusForgeLevel;
import com.stal111.forbidden_arcanus.common.tile.forge.HephaestusForgeTileEntity;
import com.stal111.forbidden_arcanus.init.other.ModContainers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.ContainerData;

import javax.annotation.Nonnull;

/**
 * Hephaestus Forge Container
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.container.HephaestusForgeContainer
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-06-28
 */
public class HephaestusForgeContainer extends AbstractContainerMenu {

    private final HephaestusForgeTileEntity tileEntity;
    private final ContainerData hephaestusForgeData;

    public HephaestusForgeContainer(int id, Inventory playerInventory) {
        //TODO
        this(id, playerInventory, null);
    }

    public HephaestusForgeContainer(int id, Inventory playerInventory, HephaestusForgeTileEntity tileEntity) {
        super(ModContainers.HEPHAESTUS_FORGE.get(), id);
        this.tileEntity = tileEntity;
        this.hephaestusForgeData = tileEntity.getHephaestusForgeData();

        checkContainerDataCount(this.hephaestusForgeData, 5);
        this.addDataSlots(this.hephaestusForgeData);

        int x = 26;

        // Hephaestus Forge Slots
        this.addSlot(new EnhancerSlot(this.getTileEntity(), 0, 32 + x, 24, HephaestusForgeLevel.getRequiredLevelForSlot(1)));
        this.addSlot(new EnhancerSlot(this.getTileEntity(), 1, 32 + x, 46, HephaestusForgeLevel.getRequiredLevelForSlot(2)));
        this.addSlot(new EnhancerSlot(this.getTileEntity(), 2, 128 + x, 24, HephaestusForgeLevel.getRequiredLevelForSlot(3)));
        this.addSlot(new EnhancerSlot(this.getTileEntity(), 3, 128 + x, 46, HephaestusForgeLevel.getRequiredLevelForSlot(4)));

        HephaestusForgeLevel level = this.getTileEntity().getForgeLevel();

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
        this.addSlot(new MainSlot(this.getTileEntity(), 4, 80 + x, 24));

        // Input Slots
        this.addSlot(new InputSlot(this.getTileEntity(), 5, 8, 25, InputType.AUREAL));
        this.addSlot(new InputSlot(this.getTileEntity(), 6, 8, 43, InputType.SOULS));
        this.addSlot(new InputSlot(this.getTileEntity(), 7, x + 176 + 2, 25, InputType.BLOOD));
        this.addSlot(new InputSlot(this.getTileEntity(), 8, x + 176 + 2, 43, InputType.EXPERIENCE));

        // Inventory Slots
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18 + x, 84 + i * 18));
            }
        }

        // Hotbar Slots
        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18 + x, 142));
        }
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(@Nonnull Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            result = stack.copy();

            if (index <= 4) {
                if (!this.moveItemStackTo(stack, 5, 41, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(stack, result);
            } else {
                if (this.tileEntity.getItem(4).isEmpty()) {
                    if (!this.moveItemStackTo(stack, 4, 5, false)) {
                        return ItemStack.EMPTY;
                    }

                    slot.onQuickCraft(stack, result);
                } else {
                    if (!this.moveItemStackTo(stack, 0, 4, false)) {
                        if (index < 32) {
                            if (!this.moveItemStackTo(stack, 32, 41, false)) {
                                return ItemStack.EMPTY;
                            }
                        } else if (index < 41 && !this.moveItemStackTo(stack, 5, 32, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }
            }
        }

        return result;
    }

    @Override
    public boolean stillValid(@Nonnull Player player) {
        return tileEntity.stillValid(player);
    }

    public HephaestusForgeTileEntity getTileEntity() {
        return tileEntity;
    }

    public ContainerData getHephaestusForgeData() {
        return hephaestusForgeData;
    }
}
