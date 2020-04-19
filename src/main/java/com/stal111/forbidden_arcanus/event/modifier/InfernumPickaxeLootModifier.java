package com.stal111.forbidden_arcanus.event.modifier;

import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.List;

public class InfernumPickaxeLootModifier extends LootModifier {

    protected InfernumPickaxeLootModifier(ILootCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        List<ItemStack> ret = new ArrayList<>();
        generatedLoot.forEach(s -> ret.add(smelt(s, context)));
        return ret;
    }

    private static ItemStack smelt(ItemStack stack, LootContext context) {
        if (Tags.Blocks.ORES.contains(Block.getBlockFromItem(stack.getItem()))) {
            return context.getWorld().getRecipeManager().getRecipe(IRecipeType.SMELTING, new Inventory(stack), context.getWorld())
                    .map(FurnaceRecipe::getRecipeOutput)
                    .filter(itemStack -> !itemStack.isEmpty())
                    .map(itemStack -> ItemHandlerHelper.copyStackWithSize(itemStack, stack.getCount() * itemStack.getCount()))
                    .orElse(stack);
        } else {
            return stack;
        }
    }

    public static class Serializer extends GlobalLootModifierSerializer<InfernumPickaxeLootModifier> {

        @Override
        public InfernumPickaxeLootModifier read(ResourceLocation name, JsonObject json, ILootCondition[] conditionsIn) {
            return new InfernumPickaxeLootModifier(conditionsIn);
        }
    }
}
