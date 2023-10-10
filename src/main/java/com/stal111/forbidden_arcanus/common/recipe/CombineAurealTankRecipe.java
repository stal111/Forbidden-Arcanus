package com.stal111.forbidden_arcanus.common.recipe;

import com.stal111.forbidden_arcanus.common.aureal.ItemAurealProvider;
import com.stal111.forbidden_arcanus.common.item.AurealTankItem;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.init.ModRecipeSerializers;
import net.minecraft.core.RegistryAccess;
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
        ItemAurealProvider.ProviderInfo info = this.getCombinedInfo(container.getItems());
        boolean multipleStacks = container.getItems().stream().filter(stack -> !stack.isEmpty()).toList().size() > 1;

        return info != ItemAurealProvider.ProviderInfo.EMPTY && multipleStacks && info.limit() <= AurealTankItem.MAX_CAPACITY;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull CraftingContainer container, @NotNull RegistryAccess registryAccess) {
        ItemAurealProvider.ProviderInfo info = this.getCombinedInfo(container.getItems());

        ItemStack stack = new ItemStack(ModItems.AUREAL_TANK.get());

        stack.getCapability(ItemAurealProvider.AUREAL).ifPresent(provider -> {
            provider.setAurealLimit(info.limit());
            provider.setAureal(info.aureal());
        });

        return stack;
    }

    private ItemAurealProvider.ProviderInfo getCombinedInfo(List<ItemStack> stacks) {
        ItemAurealProvider.ProviderInfo info = ItemAurealProvider.ProviderInfo.EMPTY;

        for (ItemStack stack : stacks) {
            if (stack.isEmpty()) {
                continue;
            }

            if (!stack.is(ModItems.AUREAL_TANK.get())) {
                return ItemAurealProvider.ProviderInfo.EMPTY;
            }

            ItemAurealProvider provider = stack.getCapability(ItemAurealProvider.AUREAL).orElseThrow(() -> new RuntimeException("Aureal Tank item is missing aureal capability."));

            info = info.combine(provider.getSnapshot());
        }

        return info;
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