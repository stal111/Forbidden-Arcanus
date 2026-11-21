package com.stal111.forbidden_arcanus.common.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

/**
 * @author stal111
 */
public class FieryLootModifier extends LootModifier {

    public static final MapCodec<FieryLootModifier> CODEC = RecordCodecBuilder.mapCodec(instance -> codecStart(instance).apply(instance, FieryLootModifier::new));

    private static final LootItemFunction SMELT_FUNCTION = SmeltItemFunction.smelted().build();

    /**
     * Constructs a LootModifier.
     *
     * @param conditions the ILootConditions that need to be matched before the loot is modified.
     */
    public FieryLootModifier(LootItemCondition[] conditions, int priority) {
        super(conditions, priority);
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        generatedLoot.replaceAll(stack -> SMELT_FUNCTION.apply(stack, context));

        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
