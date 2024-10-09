package com.stal111.forbidden_arcanus.common.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

/**
 * @author stal111
 * @since 2023-04-03
 */
public class SmelterPrismItem extends Item {

    private static final int BURN_TIME = 200;

    public SmelterPrismItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getBurnTime(ItemStack stack, @Nullable RecipeType<?> recipeType) {
        return BURN_TIME;
    }
}
