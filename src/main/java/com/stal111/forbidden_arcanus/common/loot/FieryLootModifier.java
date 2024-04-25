package com.stal111.forbidden_arcanus.common.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

/**
 * Fiery Loot Modifier <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.loot.FieryLootModifier
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 */
public class FieryLootModifier extends LootModifier {

    public static final Supplier<MapCodec<FieryLootModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.mapCodec(instance -> codecStart(instance).apply(instance, FieryLootModifier::new)));

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
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        ObjectArrayList<ItemStack> list = new ObjectArrayList<>();

        generatedLoot.forEach(stack -> list.add(this.trySmelt(stack, context.getLevel())));

        return list;
    }

    private ItemStack trySmelt(ItemStack stack, Level level) {
        return level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(stack), level)
                .map(smeltingRecipe -> smeltingRecipe.value().getResultItem(level.registryAccess()))
                .filter(itemStack -> !itemStack.isEmpty())
                .map(itemStack -> itemStack.copyWithCount(stack.getCount() * itemStack.getCount()))
                .orElse(stack);
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
