package com.stal111.forbidden_arcanus.common.tile.forge.ritual;

import com.stal111.forbidden_arcanus.common.tile.forge.HephaestusForgeTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

/**
 * Ritual
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.tile.forge.ritual.Ritual
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-09
 */
public class Ritual {

    private final ResourceLocation name;

    private final List<ItemStack> inputs;
    private final ItemStack hephaestusForgeItem;
    private final ItemStack result;

    private final RitualEssences essences;

    private final ResourceLocation outerTexture;
    private final ResourceLocation innerTexture;

    private final int time;

    public Ritual(ResourceLocation name, List<ItemStack> inputs, ItemStack hephaestusForgeItem, ItemStack result, RitualEssences essences, ResourceLocation outerTexture, ResourceLocation innerTexture, int time) {
        this.name = name;
        this.inputs = inputs;
        this.hephaestusForgeItem = hephaestusForgeItem;
        this.result = result;
        this.essences = essences;
        this.outerTexture = outerTexture;
        this.innerTexture = innerTexture;
        this.time = time;
    }

    public boolean canStart(List<ItemStack> inputs, HephaestusForgeTileEntity tileEntity) {
        if (!this.getEssences().checkEssences(tileEntity)) {
            return false;
        }
        return this.checkIngredients(inputs, tileEntity);
    }

    public boolean checkIngredients(List<ItemStack> ingredients, HephaestusForgeTileEntity tileEntity) {
        for (ItemStack stack : this.getInputs()) {
            boolean foundStack = false;

            for (ItemStack input : ingredients) {
                if (stack.equals(input, false)) {
                    ingredients.remove(input);

                    foundStack = true;
                    break;
                }
            }

            if (!foundStack) {
                return false;
            }
        }

        ItemStack stack = tileEntity.getStackInSlot(4);

        if (!ingredients.isEmpty()) {
            return false;
        }

        return this.getHephaestusForgeItem().isEmpty() ? stack.isEmpty() : this.getHephaestusForgeItem().equals(stack, false);
    }

    public ResourceLocation getName() {
        return name;
    }

    public List<ItemStack> getInputs() {
        return inputs;
    }

    public ItemStack getHephaestusForgeItem() {
        return hephaestusForgeItem;
    }

    public ItemStack getResult() {
        return result.copy();
    }

    public RitualEssences getEssences() {
        return essences;
    }

    public ResourceLocation getOuterTexture() {
        return outerTexture;
    }

    public ResourceLocation getInnerTexture() {
        return innerTexture;
    }

    public int getTime() {
        return time;
    }
}
