package com.stal111.forbidden_arcanus.common.loot;

import com.google.gson.JsonObject;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Fiery Loot Modifier <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.loot.FieryLootModifier
 *
 * @author stal111
 * @version 2.0.0
 */
public class FieryLootModifier extends LootModifier {

    /**
     * Constructs a LootModifier.
     *
     * @param conditions the ILootConditions that need to be matched before the loot is modified.
     */
    public FieryLootModifier(LootItemCondition[] conditions) {
        super(conditions);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        List<ItemStack> ret = new ArrayList<>();
        generatedLoot.forEach(s -> ret.add(this.trySmelt(s, context.getLevel())));
        return ret;
    }

    private ItemStack trySmelt(ItemStack stack, Level world) {
        return world.getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(stack), world)
                .map(SmeltingRecipe::getResultItem)
                .filter(itemStack -> !itemStack.isEmpty())
                .map(itemStack -> ItemHandlerHelper.copyStackWithSize(itemStack, stack.getCount() * itemStack.getCount()))
                .orElse(stack);

    }

    public static class Serializer extends GlobalLootModifierSerializer<FieryLootModifier> {

        @Override
        public FieryLootModifier read(ResourceLocation name, JsonObject json, LootItemCondition[] conditions) {
            return new FieryLootModifier(conditions);
        }

        @Override
        public JsonObject write(FieryLootModifier instance) {
            return super.makeConditions(instance.conditions);
        }
    }
}
