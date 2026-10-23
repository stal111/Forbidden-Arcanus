package com.stal111.forbidden_arcanus.common.essence.source;

import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class EssenceValueSource implements EssenceSource {

    @Nullable
    @Override
    public EssenceValue getEssence(ItemStack stack) {
        return EssenceHelper.getEssenceValue(stack).orElse(null);
    }

    @Override
    public ItemStack finishExtraction(ItemStack stack, int extractedAmount) {
        stack.shrink(1);

        return stack;
    }
}
