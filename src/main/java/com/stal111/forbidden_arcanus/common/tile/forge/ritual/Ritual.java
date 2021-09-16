package com.stal111.forbidden_arcanus.common.tile.forge.ritual;

import com.stal111.forbidden_arcanus.common.tile.forge.HephaestusForgeTileEntity;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private final Map<Integer, Ingredient> inputs;
    private final ItemStack hephaestusForgeItem;
    private final ItemStack result;

    private final RitualEssences essences;

    private final ResourceLocation outerTexture;
    private final ResourceLocation innerTexture;

    private final PedestalType pedestalType;

    private final int time;

    public Ritual(ResourceLocation name, Map<Integer, Ingredient> inputs, ItemStack hephaestusForgeItem, ItemStack result, RitualEssences essences, ResourceLocation outerTexture, ResourceLocation innerTexture, int time) {
        this.name = name;
        this.inputs = inputs;
        this.hephaestusForgeItem = hephaestusForgeItem;
        this.result = result;
        this.essences = essences;
        this.outerTexture = outerTexture;
        this.innerTexture = innerTexture;
        this.pedestalType = PedestalType.DARKSTONE_PEDESTAL;
        this.time = time;
    }

    public boolean canStart(List<ItemStack> inputs, HephaestusForgeTileEntity tileEntity) {
        if (!this.getEssences().checkEssences(tileEntity)) {
            return false;
        }
        return this.checkIngredients(inputs, tileEntity);
    }

    public boolean checkIngredients(List<ItemStack> ingredients, HephaestusForgeTileEntity tileEntity) {
        for (Ingredient ingredient : this.getInputs()) {
            boolean foundStack = false;

            for (ItemStack input : ingredients) {
                if (ingredient.test(input)) {
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
        return this.name;
    }

    public Ingredient getInput(int slot) {
        return this.inputs.get(slot);
    }

    public List<Ingredient> getInputs() {
        List<Ingredient> inputs = new ArrayList<>();
        for (Map.Entry<Integer, Ingredient> entry : this.inputs.entrySet()) {
            inputs.add(entry.getValue());
        }
        return inputs;
    }

    public ItemStack getHephaestusForgeItem() {
        return this.hephaestusForgeItem;
    }

    public ItemStack getResult() {
        return this.result.copy();
    }

    public RitualEssences getEssences() {
        return this.essences;
    }

    public ResourceLocation getOuterTexture() {
        return this.outerTexture;
    }

    public ResourceLocation getInnerTexture() {
        return this.innerTexture;
    }

    public PedestalType getPedestalType() {
        return this.pedestalType;
    }

    public int getTime() {
        return this.time;
    }

    public enum PedestalType {
        DARKSTONE_PEDESTAL(NewModBlocks.DARKSTONE_PEDESTAL.get()),
        ARCANE_DARKSTONE_PEDESTAL(NewModBlocks.ARCANE_DARKSTONE_PEDESTAL.get());

        private final Block block;

        PedestalType(Block block) {
            this.block = block;
        }

        public Block getBlock() {
            return block;
        }
    }
}
