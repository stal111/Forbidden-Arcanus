package com.stal111.forbidden_arcanus.common.block.entity.clibano.logic;

import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoInputSlot;
import com.stal111.forbidden_arcanus.common.item.crafting.ClibanoRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

/**
 * @author stal111
 * @since 18.02.2024
 */
public interface ClibanoAccessor {

    boolean canSmelt(@Nullable RecipeHolder<ClibanoRecipe> recipe, ClibanoInputSlot slot);
    void finishRecipe(RecipeHolder<ClibanoRecipe> recipe, ClibanoInputSlot slot);

    int getCookingTime(RecipeHolder<ClibanoRecipe> recipe);
}
