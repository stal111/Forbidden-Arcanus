package com.stal111.forbidden_arcanus.common.inventory;

import com.google.common.collect.ImmutableList;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeLevel;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerCache;
import com.stal111.forbidden_arcanus.core.init.other.ModMenuTypes;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.valhelsia.valhelsia_core.api.common.block.entity.MenuCreationContext;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Hephaestus Forge Menu <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.inventory.HephaestusForgeMenu
 *
 * @author stal111
 * @since 2021-06-28
 */
public class HephaestusForgeMenu extends AbstractContainerMenu {

    public static final List<Integer> ENHANCERS_SLOTS = ImmutableList.of(0, 1, 2, 3);

    private final ContainerData hephaestusForgeData;
    private final ContainerLevelAccess levelAccess;

    private final DataSlot hephaestusForgeLevel = DataSlot.standalone();

    public HephaestusForgeMenu(int id, Inventory inventory, FriendlyByteBuf buffer) {
        this(id, new ItemStackHandler(9), new SimpleContainerData(4), MenuCreationContext.of(inventory), HephaestusForgeLevel.ONE);
    }

    public HephaestusForgeMenu(int id, ItemStackHandler handler, ContainerData containerData, MenuCreationContext<HephaestusForgeBlockEntity, IItemHandler> creationContext, HephaestusForgeLevel level) {
        super(ModMenuTypes.HEPHAESTUS_FORGE.get(), id);
        this.levelAccess = creationContext.levelAccess();
        this.hephaestusForgeData = containerData;

        checkContainerDataCount(this.hephaestusForgeData, 4);
        this.addDataSlots(this.hephaestusForgeData);
        this.addDataSlot(this.hephaestusForgeLevel);

        this.hephaestusForgeLevel.set(level.getAsInt());

        // Hephaestus Forge Slots
        this.addEnhancerSlot(handler, 0, 36, 24);
        this.addEnhancerSlot(handler, 1, 36, 46);
        this.addEnhancerSlot(handler, 2, 124, 24);
        this.addEnhancerSlot(handler, 3, 124, 46);

        // Main Slot
        this.addSlot(new MainSlot(handler, 4, 80, 24));

        // Input Slots
        this.addSlot(new InputSlot(handler, 5, 8 - 26, 25, EssenceType.AUREAL));
        this.addSlot(new InputSlot(handler, 6, 8 - 26, 43, EssenceType.SOULS));
        this.addSlot(new InputSlot(handler, 7, 176 + 2, 25, EssenceType.BLOOD));
        this.addSlot(new InputSlot(handler, 8, 176 + 2, 43, EssenceType.EXPERIENCE));

        // Inventory Slots
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new SlotItemHandler(creationContext.inventory(), j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        // Hotbar Slots
        for (int k = 0; k < 9; ++k) {
            this.addSlot(new SlotItemHandler(creationContext.inventory(), k, 8 + k * 18, 142));
        }
    }

    private void addEnhancerSlot(ItemStackHandler handler, int index, int x, int y) {
        this.addSlot(new EnhancerSlot(handler, index, x, y, () -> this.isSlotLocked(index)));
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(@Nonnull Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        Level level = player.level();

        if (!slot.hasItem()) {
            return result;
        }

        ItemStack stack = slot.getItem();
        result = stack.copy();

        if (index <= 4) {
            if (!this.moveItemStackTo(stack, 5, 41, true)) {
                return ItemStack.EMPTY;
            }

            slot.onQuickCraft(stack, result);
        } else if (index <= 8) {
            if (!this.moveItemStackTo(stack, 9, 41, true)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (this.canInput(level, EssenceType.AUREAL, stack)) {
                if (!this.moveItemStackTo(stack, 5, 6, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.canInput(level, EssenceType.SOULS, stack)) {
                if (!this.moveItemStackTo(stack, 6, 7, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.canInput(level, EssenceType.BLOOD, stack)) {
                if (!this.moveItemStackTo(stack, 7, 8, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.canInput(level, EssenceType.EXPERIENCE, stack)) {
                if (!this.moveItemStackTo(stack, 8, 9, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (EnhancerCache.get(stack.getItem()).isPresent()) {
                if (!this.moveItemStackTo(stack, 0, this.getLevel().getAsInt() + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.getSlot(4).hasItem()) {
                if (!this.moveItemStackTo(stack, 4, 5, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 36) {
                if (!this.moveItemStackTo(stack, 36, 45, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 45 && !this.moveItemStackTo(stack, 9, 36, false)) {
                return ItemStack.EMPTY;
            }
        }

        if (index == 4) {
            this.levelAccess.execute((level1, pos) -> {
                level.sendBlockUpdated(pos, level1.getBlockState(pos), level1.getBlockState(pos), 3);

            });
        }

        slot.onTake(player, stack);

        return result;
    }

    public boolean canInput(Level level, EssenceType type, ItemStack stack) {
        return level.registryAccess().registryOrThrow(FARegistries.FORGE_INPUT).holders()
                .map(Holder.Reference::value)
                .anyMatch(input -> input.canInput(type, stack));
    }

    @Override
    public boolean stillValid(@Nonnull Player player) {
        return stillValid(this.levelAccess, player, this.getLevel().getBlock());
    }

    public ContainerData getHephaestusForgeData() {
        return this.hephaestusForgeData;
    }

    public HephaestusForgeLevel getLevel() {
        return HephaestusForgeLevel.getFromIndex(this.hephaestusForgeLevel.get());
    }


    public boolean isSlotLocked(int slot) {
        return this.slots.get(slot).getSlotIndex() >= this.getLevel().getAsInt();
    }
}
