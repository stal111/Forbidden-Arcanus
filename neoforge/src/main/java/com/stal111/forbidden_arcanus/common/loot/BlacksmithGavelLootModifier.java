package com.stal111.forbidden_arcanus.common.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.util.ModTags;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 2021-09-24
 */
public class BlacksmithGavelLootModifier extends LootModifier {

    public static final MapCodec<BlacksmithGavelLootModifier> CODEC = RecordCodecBuilder.mapCodec(instance -> codecStart(instance).apply(instance, BlacksmithGavelLootModifier::new));

    public BlacksmithGavelLootModifier(LootItemCondition[] conditions, int priority) {
        super(conditions, priority);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(@NotNull ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (generatedLoot.isEmpty()) {
            return generatedLoot;
        }

        BlockState state = context.getOptionalParameter(LootContextParams.BLOCK_STATE);

        if (state != null && state.is(Tags.Blocks.ORES) && !state.is(ModTags.Blocks.BLACKSMITH_GAVEL_UNAFFECTED)) {
            int size = generatedLoot.size();

            for (int i = 0; i < size; i++) {
                generatedLoot.add(generatedLoot.get(i).copy());
            }
        }

        return generatedLoot;
    }

    @Override
    public @NotNull MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
