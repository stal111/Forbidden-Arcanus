package com.stal111.forbidden_arcanus.common.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.util.ModTags;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

/**
 * @author stal111
 * @since 2021-09-24
 */
public class BlacksmithGavelLootModifier extends LootModifier {

    public static final Supplier<Codec<BlacksmithGavelLootModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(instance -> codecStart(instance).apply(instance, BlacksmithGavelLootModifier::new)));

    /**
     * Constructs a LootModifier.
     *
     * @param conditions the ILootConditions that need to be matched before the loot is modified.
     */
    public BlacksmithGavelLootModifier(LootItemCondition[] conditions) {
        super(conditions);
    }

    @Nonnull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        BlockState state = context.getParamOrNull(LootContextParams.BLOCK_STATE);
        ItemStack stack = context.getParamOrNull(LootContextParams.TOOL);

        if (state == null || stack == null) {
            return generatedLoot;
        }

        if (this.isValidGavel(stack) && state.is(Tags.Blocks.ORES) && !state.is(ModTags.Blocks.BLACKSMITH_GAVEL_UNAFFECTED)) {
            generatedLoot.addAll(generatedLoot.clone());
        }
        return generatedLoot;
    }

    private boolean isValidGavel(ItemStack stack) {
        return stack.is(ModTags.Items.BLACKSMITH_GAVEL) && stack.getEnchantmentLevel(Enchantments.SILK_TOUCH) == 0;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
