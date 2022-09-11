package com.stal111.forbidden_arcanus.common.block.entity.clibano;

import com.mojang.datafixers.util.Pair;
import com.stal111.forbidden_arcanus.common.inventory.clibano.ClibanoMenu;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author stal111
 * @since 2022-09-10
 */
public class ClibanoItemHandler implements IItemHandler {

    private static final int SOUL_SLOT = ClibanoMenu.SOUL_SLOT;
    private static final int FUEL_SLOT = ClibanoMenu.FUEL_SLOT;

    private static final Pair<Integer, Integer> INPUT_SLOTS = ClibanoMenu.INPUT_SLOTS;
    public static final Pair<Integer, Integer> RESULT_SLOTS = ClibanoMenu.RESULT_SLOTS;

    private final IItemHandler itemHandler;
    @Nullable
    private final Direction[] sides;

    public ClibanoItemHandler(IItemHandler itemHandler, @Nullable Direction... sides) {
        this.itemHandler = itemHandler;
        this.sides = sides;
    }

    @Override
    public int getSlots() {
        return this.itemHandler.getSlots();
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        return this.itemHandler.getStackInSlot(slot);
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (this.sides.length == 1) {
            if (this.sides[0] == Direction.DOWN) {
                return stack;
            } else if (this.sides[0] == Direction.UP && (slot == INPUT_SLOTS.getFirst() || slot == INPUT_SLOTS.getSecond())) {
                return this.itemHandler.insertItem(slot, stack, simulate);
            }
        } else if (slot == SOUL_SLOT || slot == FUEL_SLOT) {
            return this.itemHandler.insertItem(slot, stack, simulate);
        }

        return stack;
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (this.sides.length == 1 && this.sides[0] == Direction.DOWN && slot == RESULT_SLOTS.getFirst() || slot == RESULT_SLOTS.getSecond()) {
            return this.itemHandler.extractItem(slot, amount, simulate);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int slot) {
        return this.itemHandler.getSlotLimit(slot);
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return this.itemHandler.isItemValid(slot, stack);
    }
}
