package com.stal111.forbidden_arcanus.common.inventory;

import com.google.common.collect.ImmutableList;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeLevel;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerCache;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.other.ModMenuTypes;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.valhelsia.valhelsia_core.api.common.block.entity.MenuCreationContext;
import org.apache.commons.lang3.BooleanUtils;

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
    private final int[] lockedSlots = new int[4];

    public HephaestusForgeMenu(int id, Inventory inventory, FriendlyByteBuf buffer) {
        this(id, new ItemStackHandler(9), new SimpleContainerData(4), MenuCreationContext.of(inventory));
    }

    public HephaestusForgeMenu(int id, ItemStackHandler handler, ContainerData containerData, MenuCreationContext<HephaestusForgeBlockEntity, IItemHandler> creationContext) {
        super(ModMenuTypes.HEPHAESTUS_FORGE.get(), id);
        this.levelAccess = creationContext.levelAccess();
        this.hephaestusForgeData = containerData;

        checkContainerDataCount(this.hephaestusForgeData, 4);
        this.addDataSlots(this.hephaestusForgeData);
        this.addDataSlot(this.hephaestusForgeLevel);
        this.addDataSlot(DataSlot.shared(this.lockedSlots, 0));
        this.addDataSlot(DataSlot.shared(this.lockedSlots, 1));
        this.addDataSlot(DataSlot.shared(this.lockedSlots, 2));
        this.addDataSlot(DataSlot.shared(this.lockedSlots, 3));

        HephaestusForgeLevel level = this.updateLevel();

        this.hephaestusForgeLevel.set(level.getAsInt());

        // Hephaestus Forge Slots
        this.addEnhancerSlot(handler, 0, 36, 24, HephaestusForgeLevel.ONE, level);
        this.addEnhancerSlot(handler, 1, 36, 46, HephaestusForgeLevel.TWO, level);
        this.addEnhancerSlot(handler, 2, 124, 24, HephaestusForgeLevel.THREE, level);
        this.addEnhancerSlot(handler, 3, 124, 46, HephaestusForgeLevel.FOUR, level);

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

    private void addEnhancerSlot(ItemStackHandler handler, int index, int x, int y, HephaestusForgeLevel requiredLevel, HephaestusForgeLevel currentLevel) {
        this.addSlot(new EnhancerSlot(handler, index, x, y, requiredLevel, locked -> this.lockedSlots[index] = BooleanUtils.toInteger(locked)).updateLocked(currentLevel));
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
                if (!this.moveItemStackTo(stack, 0, this.getHighestUnlockedEnhancerSlot() + 1, false)) {
                    return ItemStack.EMPTY;
                }
            }  else if (!this.getSlot(4).hasItem()) {
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

        slot.onTake(player, stack);

        return result;
    }

    public boolean canInput(Level level, EssenceType type, ItemStack stack) {
        return level.registryAccess().registryOrThrow(FARegistries.FORGE_INPUT).holders()
                .map(Holder::get)
                .anyMatch(input -> input.canInput(type, stack));
    }

    @Override
    public boolean stillValid(@Nonnull Player player) {
        return stillValid(this.levelAccess, player, ModBlocks.HEPHAESTUS_FORGE.get());
    }

    public ContainerData getHephaestusForgeData() {
        return this.hephaestusForgeData;
    }

    private HephaestusForgeLevel updateLevel() {
        return this.levelAccess.evaluate((level, pos) -> {
            return HephaestusForgeLevel.getFromIndex(level.getBlockState(pos).getValue(ModBlockStateProperties.TIER));
        }, HephaestusForgeLevel.ONE);
    }

    public HephaestusForgeLevel getLevel() {
        return HephaestusForgeLevel.getFromIndex(this.hephaestusForgeLevel.get());
    }

    public boolean isSlotLocked(EnhancerSlot slot) {
        return this.lockedSlots[slot.getSlotIndex()] == 1;
    }

    private int getHighestUnlockedEnhancerSlot() {
        for (int i : ENHANCERS_SLOTS) {
            Slot slot = this.slots.get(i);

            if (this.lockedSlots[slot.getSlotIndex()] == 1) {
                return i;
            }
        }

        return 3;
    }
}
