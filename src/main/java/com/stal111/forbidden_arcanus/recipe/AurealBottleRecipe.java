package com.stal111.forbidden_arcanus.recipe;

import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.ModRecipeSerializers;
import com.stal111.forbidden_arcanus.init.NewModItems;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Aureal Bottle Recipe
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.recipe.AurealBottleRecipe
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-05-20
 */
public class AurealBottleRecipe extends SpecialRecipe {

    public AurealBottleRecipe(ResourceLocation id) {
        super(id);
    }

    @Override
    public boolean matches(@Nonnull CraftingInventory inventory, @Nonnull World world) {
        for(int i = 0; i < inventory.getWidth(); i++) {
            for(int j = 0; j < inventory.getHeight(); j++) {
                ItemStack stack = inventory.getStackInSlot(i + j * inventory.getWidth());

                if (stack.isEmpty()) {
                    return false;
                }

                if (i == 1 && j == 1) {
                    List<EffectInstance> effects = PotionUtils.getEffectsFromStack(stack);

                    if (stack.getItem() != Items.POTION || effects.size() != 1 || effects.get(0).getPotion() != Effects.REGENERATION || effects.get(0).getAmplifier() != 1) {
                        return false;
                    }
                } else if (stack.getItem() != ModItems.ARCANE_CRYSTAL_DUST.get()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull CraftingInventory inventory) {
        return new ItemStack(NewModItems.AUREAL_BOTTLE.get());
    }

    @Override
    public boolean canFit(int width, int height) {
        return width >= 3 && height >= 3;
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.CRAFTING_AUREAL_BOTTLE.get();
    }

    @Override
    public boolean isDynamic() {
        return false;
    }
}