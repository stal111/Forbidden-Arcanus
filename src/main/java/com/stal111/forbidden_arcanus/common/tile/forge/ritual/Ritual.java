package com.stal111.forbidden_arcanus.common.tile.forge.ritual;

import com.stal111.forbidden_arcanus.common.tile.forge.HephaestusForgeTileEntity;
import net.minecraft.item.ItemStack;

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

    private final List<ItemStack> inputs;
    private final ItemStack hephaestusForgeItem;
    private final ItemStack result;

    private final RitualEssences essences;

    public Ritual(List<ItemStack> inputs, ItemStack hephaestusForgeItem, ItemStack result, RitualEssences essences) {
        this.inputs = inputs;
        this.hephaestusForgeItem = hephaestusForgeItem;
        this.result = result;
        this.essences = essences;
    }

    public boolean canStart(List<ItemStack> inputs, HephaestusForgeTileEntity tileEntity) {
        if (!this.getEssences().checkEssences(tileEntity)) {
            return false;
        }
        for (ItemStack stack : this.getInputs()) {
            boolean foundStack = false;

            for (ItemStack input : inputs) {
                if (stack.equals(input, false)) {
                    inputs.remove(input);

                    foundStack = true;
                    System.out.println("FOUND: " + stack.getItem());
                    break;
                }
            }

            if (!foundStack) {
                System.out.println("MISSING: " + stack.getItem());
                return false;
            }
        }

        ItemStack stack = tileEntity.getStackInSlot(4);

        if (!inputs.isEmpty()) {
            return false;
        }

        return this.getHephaestusForgeItem().isEmpty() ? stack.isEmpty() : this.getHephaestusForgeItem().equals(stack, false);
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
}
