package com.stal111.forbidden_arcanus.common.essence.source;

import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class EssenceStorageSource implements EssenceSource {

    public static final int EXTRACTION_SPEED = 10;

    @Nullable
    @Override
    public EssenceValue getEssence(ItemStack stack) {
        return EssenceHelper.getEssenceStorage(stack)
                .map(storage -> new EssenceValue(storage.type(), storage.amount()))
                .orElse(null);
    }

    @Override
    public int getExtractionRate(ItemStack stack, EssenceType type) {
        return Math.min(this.getMaxAmount(stack, type), EXTRACTION_SPEED);
    }

    @Override
    public ItemStack finishExtraction(ItemStack stack, int extractedAmount) {
        return EssenceHelper.getEssenceStorage(stack).map(storage -> {
            int amount = storage.amount();

            storage.addEssence(stack, -extractedAmount);

            if (amount - extractedAmount <= 0) {
                Holder<Item> itemHolder = stack.get(ModDataComponents.EMPTY_ITEM);

                if (itemHolder != null) {
                    return new ItemStack(itemHolder);
                }
            }
            return stack;
        }).orElse(stack);
    }
}
