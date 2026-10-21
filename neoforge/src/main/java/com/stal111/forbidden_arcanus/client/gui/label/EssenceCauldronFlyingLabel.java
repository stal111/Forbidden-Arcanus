package com.stal111.forbidden_arcanus.client.gui.label;

import com.stal111.forbidden_arcanus.common.block.entity.EssenceCauldronBlockEntity;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceStorage;
import com.stal111.forbidden_arcanus.common.item.crafting.cache.ClientRecipeCache;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.function.Consumer;

public class EssenceCauldronFlyingLabel implements BlockFlyingLabel {

    @Override
    public void getComponents(ClientLevel level, LocalPlayer player, ItemStack stack, BlockPos pos, Consumer<Component> adder) {
        if (stack.isEmpty()) {
            return;
        }

        if (level.getBlockEntity(pos) instanceof EssenceCauldronBlockEntity blockEntity) {
            EssenceStorage storage = blockEntity.getEssenceStorage();

            if (storage.isEmpty()) {
                return;
            }

            ClientRecipeCache.transformRecipes.stream()
                    .map(RecipeHolder::value)
                    .filter(recipe -> recipe.input().test(stack) && recipe.essenceCost().value().type() == storage.type())
                    .findFirst()
                    .ifPresent(recipe -> {
                        adder.accept(recipe.essenceCost().asFormattedComponent(storage.getEssenceValue()));
                    });
        }
    }
}
