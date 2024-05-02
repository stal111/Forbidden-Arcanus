package com.stal111.forbidden_arcanus.common.recipe;

import com.stal111.forbidden_arcanus.common.essence.EssenceData;
import com.stal111.forbidden_arcanus.common.essence.ItemEssenceData;
import com.stal111.forbidden_arcanus.common.item.AurealTankItem;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.init.ModRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
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
    public boolean matches(@NotNull CraftingContainer container, @NotNull Level level) {
        ItemEssenceData data = this.getCombinedStorage(container.getItems());
        boolean multipleStacks = container.getItems().stream().filter(stack -> !stack.isEmpty()).toList().size() > 1;

        return data != ItemEssenceData.EMPTY && multipleStacks && data.get().limit() <= AurealTankItem.MAX_CAPACITY;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull CraftingContainer container, HolderLookup.@NotNull Provider lookupProvider) {
        ItemEssenceData data = this.getCombinedStorage(container.getItems());

        ItemStack stack = new ItemStack(ModItems.AUREAL_TANK.get());

        stack.set(ModDataComponents.ESSENCE_DATA, data);

        return stack;
    }

    private ItemEssenceData getCombinedStorage(List<ItemStack> stacks) {
        EssenceData combined = EssenceData.EMPTY;

        for (ItemStack stack : stacks) {
            if (stack.isEmpty()) {
                continue;
            }

            if (!stack.is(ModItems.AUREAL_TANK.get())) {
                return ItemEssenceData.EMPTY;
            }

            ItemEssenceData data = stack.get(ModDataComponents.ESSENCE_DATA);

            if (data != null) {
                combined = combined.combine(data.get());
            }
        }

        return new ItemEssenceData(combined, true);
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
