package com.stal111.forbidden_arcanus.common.item.crafting;

import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceStorage;
import com.stal111.forbidden_arcanus.common.item.AurealTankItem;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.init.ModRecipeSerializers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author stal111
 * @since 24.09.2023
 */
public class CombineAurealTankRecipe extends CustomRecipe {

    public CombineAurealTankRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(@NotNull CraftingInput input, @NotNull Level level) {
        EssenceStorage data = this.getCombinedStorage(input.items());
        boolean multipleStacks = input.items().stream().filter(stack -> !stack.isEmpty()).toList().size() > 1;

        return data != null && multipleStacks && data.limit() <= AurealTankItem.MAX_CAPACITY;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull CraftingInput input) {
        EssenceStorage data = this.getCombinedStorage(input.items());

        ItemStack stack = new ItemStack(ModItems.AUREAL_TANK.get());

        stack.set(ModDataComponents.ESSENCE_STORAGE, data);

        return stack;
    }

    @Nullable
    private EssenceStorage getCombinedStorage(List<ItemStack> stacks) {
        EssenceValue combined = EssenceValue.EMPTY;
        int limit = 0;

        for (ItemStack stack : stacks) {
            if (stack.isEmpty()) {
                continue;
            }

            if (!stack.is(ModItems.AUREAL_TANK.get())) {
                return null;
            }

            EssenceStorage storage = stack.get(ModDataComponents.ESSENCE_STORAGE);

            if (storage != null) {
                combined = combined.combine(storage.getEssenceValue());
            }
        }

        return new EssenceStorage(combined.type(), combined.amount(), limit);
    }

    @Override
    public @NotNull RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return ModRecipeSerializers.COMBINE_AUREAL_TANK.get();
    }
}
