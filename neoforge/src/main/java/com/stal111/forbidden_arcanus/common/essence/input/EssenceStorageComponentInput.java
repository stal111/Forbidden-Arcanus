package com.stal111.forbidden_arcanus.common.essence.input;

import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceStorage;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * @author stal111
 * @since 2021-07-08
 */
public class EssenceStorageComponentInput implements EssenceInput {

    public static final int EXTRACTION_SPEED = 10;

    @Override
    public int getAmount(ItemStack stack, EssenceType type) {
        return Math.min(this.getMaxAmount(stack, type), EXTRACTION_SPEED);
    }

    @Override
    public int getMaxAmount(ItemStack stack, EssenceType type) {
        return EssenceHelper.getEssenceStorage(stack)
                .filter(storage -> storage.type() == type)
                .map(EssenceStorage::amount)
                .orElse(0);
    }

    @Override
    public ItemStack finishInput(ItemStack stack, int inputAmount) {
        return EssenceHelper.getEssenceStorage(stack).map(storage -> {
            int amount = storage.amount();

            storage.addEssence(stack, -inputAmount);

            if (amount - inputAmount <= 0) {
                Holder<Item> itemHolder = stack.get(ModDataComponents.EMPTY_ITEM);

                if (itemHolder != null) {
                    return new ItemStack(itemHolder);
                }
            }
            return stack;
        }).orElse(stack);
    }
}
