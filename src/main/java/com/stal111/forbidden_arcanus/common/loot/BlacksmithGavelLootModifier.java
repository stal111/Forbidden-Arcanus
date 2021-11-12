package com.stal111.forbidden_arcanus.common.loot;

import com.google.gson.JsonObject;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Blacksmith Gavel Loot Modifier <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.loot.BlacksmithGavelLootModifier
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-09-24
 */
public class BlacksmithGavelLootModifier extends LootModifier {

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
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        BlockState state = context.getParamOrNull(LootContextParams.BLOCK_STATE);
        ItemStack stack = context.getParamOrNull(LootContextParams.TOOL);

        if (state == null || stack == null) {
            return generatedLoot;
        }

        if (ModTags.Items.BLACKSMITH_GAVEL.contains(stack.getItem()) && Tags.Blocks.ORES.contains(state.getBlock()) && !ModTags.Blocks.BLACKSMITH_GAVEL_UNAFFECTED.contains(state.getBlock())) {
            generatedLoot.addAll(new ArrayList<>(generatedLoot));
        }
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<BlacksmithGavelLootModifier> {

        @Override
        public BlacksmithGavelLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] conditions) {
            return new BlacksmithGavelLootModifier(conditions);
        }

        @Override
        public JsonObject write(BlacksmithGavelLootModifier instance) {
            return super.makeConditions(instance.conditions);
        }
    }
}
