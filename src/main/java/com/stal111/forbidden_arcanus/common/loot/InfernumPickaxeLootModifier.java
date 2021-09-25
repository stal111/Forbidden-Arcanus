package com.stal111.forbidden_arcanus.common.loot;

import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Infernum Pickaxe Loot Modifier <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.loot.InfernumPickaxeLootModifier
 *
 * @author stal111
 * @version 2.0.0
 */
public class InfernumPickaxeLootModifier extends LootModifier {

    /**
     * Constructs a LootModifier.
     *
     * @param conditions the ILootConditions that need to be matched before the loot is modified.
     */
    public InfernumPickaxeLootModifier(ILootCondition[] conditions) {
        super(conditions);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        List<ItemStack> ret = new ArrayList<>();
        generatedLoot.forEach(s -> ret.add(this.trySmelt(s, context.getWorld())));
        return ret;
    }

    private ItemStack trySmelt(ItemStack stack, World world) {
        if (!Tags.Blocks.ORES.contains(Block.getBlockFromItem(stack.getItem()))) {
            return stack;
        }

        return world.getRecipeManager().getRecipe(IRecipeType.SMELTING, new Inventory(stack), world)
                .map(FurnaceRecipe::getRecipeOutput)
                .filter(itemStack -> !itemStack.isEmpty())
                .map(itemStack -> ItemHandlerHelper.copyStackWithSize(itemStack, stack.getCount() * itemStack.getCount()))
                .orElse(stack);

    }

    public static class Serializer extends GlobalLootModifierSerializer<InfernumPickaxeLootModifier> {

        @Override
        public InfernumPickaxeLootModifier read(ResourceLocation name, JsonObject json, ILootCondition[] conditions) {
            return new InfernumPickaxeLootModifier(conditions);
        }

        @Override
        public JsonObject write(InfernumPickaxeLootModifier instance) {
            return super.makeConditions(instance.conditions);
        }
    }
}
