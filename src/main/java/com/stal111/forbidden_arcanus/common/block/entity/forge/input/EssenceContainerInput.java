package com.stal111.forbidden_arcanus.common.block.entity.forge.input;

import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceData;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.essence.EssenceStorage;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * @author stal111
 * @since 2021-07-08
 */
public class EssenceContainerInput extends HephaestusForgeInput {

    public static final int EXTRACTION_SPEED = 10;

    @Override
    public boolean canInput(EssenceType type, ItemStack stack) {
        return EssenceHelper.getEssenceStorage(stack).map(storage -> storage.data().type() == type).orElse(false);
    }

    @Override
    public EssenceData getInputValue(ItemStack stack, RandomSource random) {
        EssenceData data = this.getMaxInputValue(stack, random);

        return EssenceData.of(data.type(), Math.min(data.amount(), EXTRACTION_SPEED));
    }

    @Override
    public EssenceData getMaxInputValue(ItemStack stack, RandomSource random) {
        return EssenceHelper.getEssenceStorage(stack).orElse(EssenceStorage.EMPTY).data();
    }

    @Override
    public ItemStack finishInput(ItemStack stack, int inputValue) {
        return EssenceHelper.getEssenceStorage(stack).map(storage -> {
            int amount = storage.data().amount();

            storage.addEssence(stack, -inputValue);

            if (amount - inputValue <= 0) {
                Holder<Item> itemHolder = stack.get(ModDataComponents.EMPTY_ITEM);

                if (itemHolder != null) {
                    return new ItemStack(itemHolder);
                }
            }
            return stack;
        }).orElse(stack);
    }
}
