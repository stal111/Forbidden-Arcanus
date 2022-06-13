package com.stal111.forbidden_arcanus.common.inventory.clibano;

import com.mojang.datafixers.util.Pair;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoMainBlockEntity;
import com.stal111.forbidden_arcanus.common.inventory.EnhancerSlot;
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
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nonnull;

/**
 * Clibano Menu <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.inventory.clibano.ClibanoMenu
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 * @since 2022-05-25
 */
public class ClibanoMenu extends AbstractContainerMenu {

    public static final int SLOT_COUNT = 7;
    public static final int DATA_COUNT = ClibanoMainBlockEntity.DATA_COUNT;

    public static final int ENHANCER_SLOT = 0;
    public static final int SOUL_SLOT = 1;
    public static final int FUEL_SLOT = 2;

    public static final Pair<Integer, Integer> INPUT_SLOTS = Pair.of(3, 4);
    public static final Pair<Integer, Integer> RESULT_SLOTS = Pair.of(5, 6);

    private final Container container;
    private final ContainerData containerData;

    private final Level level;

    public ClibanoMenu(int id, Inventory inventory) {
        this(id, new SimpleContainer(SLOT_COUNT), inventory, new SimpleContainerData(DATA_COUNT));
    }

    public ClibanoMenu(int containerId, Container container, Inventory inventory, ContainerData containerData) {
        super(ModContainers.CLIBANO.get(), containerId);
        this.container = container;
        this.containerData = containerData;
        this.level = inventory.player.getLevel();

        this.addDataSlots(this.containerData);

        checkContainerSize(container, SLOT_COUNT);
        checkContainerDataCount(containerData, DATA_COUNT);

        this.addSlot(new EnhancerSlot(container, ENHANCER_SLOT, 18, 20));
        this.addSlot(new ClibanoSoulSlot(container, SOUL_SLOT, 18, 56));
        this.addSlot(new ClibanoFuelSlot(this, container, FUEL_SLOT, 53, 56));

        // Input Slots
        this.addSlot(new Slot(container, INPUT_SLOTS.getFirst(), 44, 20));
        this.addSlot(new Slot(container, INPUT_SLOTS.getSecond(), 62, 20));

        // Result Slot
        this.addSlot(new ClibanoResultSlot(inventory.player, container, RESULT_SLOTS.getFirst(), 116, 32));
        this.addSlot(new ClibanoResultSlot(inventory.player, container, RESULT_SLOTS.getSecond(), 142, 28));

        // Inventory Slots
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 91 + i * 18));
            }
        }

        // Hotbar Slots
        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 149));
        }
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(@Nonnull Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (!slot.hasItem()) {
            return result;
        }

        ItemStack stack = slot.getItem();
        result = stack.copy();

        if (index == RESULT_SLOTS.getFirst() || index == RESULT_SLOTS.getSecond()) {
            if (!this.moveItemStackTo(stack, 7, 43, true)) {
                return ItemStack.EMPTY;
            }

            slot.onQuickCraft(stack, result);
        } else if (index < SLOT_COUNT) {
            if (!this.moveItemStackTo(stack, 7, 43, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (this.canSmelt(stack)) {
                if (!this.moveItemStackTo(stack, INPUT_SLOTS.getFirst(), INPUT_SLOTS.getSecond() + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.isFuel(stack)) {
                if (!this.moveItemStackTo(stack, FUEL_SLOT, FUEL_SLOT + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.isSoul(stack)) {
                if (!this.moveItemStackTo(stack, SOUL_SLOT, SOUL_SLOT + 1, false)) {
                    return ItemStack.EMPTY;
                }
            }  else if (index < 34) {
                if (!this.moveItemStackTo(stack, 34, 43, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 43 && !this.moveItemStackTo(stack, 7, 34, false)) {
                return ItemStack.EMPTY;
            }
        }

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

    protected boolean canSmelt(ItemStack stack) {
        return this.level.getRecipeManager().getRecipeFor(ClibanoMainBlockEntity.RECIPE_TYPE, new SimpleContainer(stack), this.level).isPresent();
    }

    protected boolean isFuel(ItemStack stack) {
        return ForgeHooks.getBurnTime(stack, RecipeType.BLASTING) > 0;
    }

    protected boolean isSoul(ItemStack stack) {
        return ClibanoMainBlockEntity.ITEM_TO_FIRE_TYPE.apply(stack).isPresent();
    }

    public boolean isSoulActive() {
        return this.getSoulDuration() > 0;
    }

    public int getSoulDuration() {
        return this.containerData.get(ClibanoMainBlockEntity.DATA_SOUL_TIME);
    }

    public int getBurnTime() {
        return this.containerData.get(ClibanoMainBlockEntity.DATA_BURN_TIME);
    }

    public int getBurnDuration() {
        return this.containerData.get(ClibanoMainBlockEntity.DATA_BURN_DURATION);
    }

    public Pair<Integer, Integer> getCookingProgress() {
        return Pair.of(this.containerData.get(ClibanoMainBlockEntity.DATA_COOKING_PROGRESS_FIRST), this.containerData.get(ClibanoMainBlockEntity.DATA_COOKING_PROGRESS_SECOND));
    }

    public Pair<Integer, Integer> getCookingDuration() {
        return Pair.of(this.containerData.get(ClibanoMainBlockEntity.DATA_COOKING_DURATION_FIRST), this.containerData.get(ClibanoMainBlockEntity.DATA_COOKING_DURATION_SECOND));
    }

    @Override
    public boolean stillValid(@Nonnull Player player) {
        return this.container.stillValid(player);
    }

    public int getFireType() {
        return this.containerData.get(ClibanoMainBlockEntity.DATA_FIRE_TYPE);
    }
}
