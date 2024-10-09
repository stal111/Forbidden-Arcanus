package com.stal111.forbidden_arcanus.common.item.crafting;

import com.stal111.forbidden_arcanus.common.essence.EssenceData;
import com.stal111.forbidden_arcanus.common.essence.EssenceStorage;
import com.stal111.forbidden_arcanus.common.item.AurealTankItem;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.init.ModRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

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

        return data != EssenceStorage.EMPTY && multipleStacks && data.limit() <= AurealTankItem.MAX_CAPACITY;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull CraftingInput input, HolderLookup.@NotNull Provider lookupProvider) {
        EssenceStorage data = this.getCombinedStorage(input.items());

        ItemStack stack = new ItemStack(ModItems.AUREAL_TANK.get());

        stack.set(ModDataComponents.ESSENCE_STORAGE, data);

        return stack;    }

    private EssenceStorage getCombinedStorage(List<ItemStack> stacks) {
        EssenceData combined = EssenceData.EMPTY;
        int limit = 0;

        for (ItemStack stack : stacks) {
            if (stack.isEmpty()) {
                continue;
            }

            if (!stack.is(ModItems.AUREAL_TANK.get())) {
                return EssenceStorage.EMPTY;
            }

            EssenceStorage storage = stack.get(ModDataComponents.ESSENCE_STORAGE);

            if (storage != null) {
                combined = combined.combine(storage.data());
            }
        }

        return new EssenceStorage(combined, limit, true);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.COMBINE_AUREAL_TANK.get();
    }
}
