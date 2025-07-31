package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * @author stal111
 * @since 2023-02-05
 */
public interface RitualResult {

    Codec<RitualResult> DIRECT_CODEC = Codec.lazyInitialized(FARegistries.RITUAL_RESULT_TYPE_REGISTRY::byNameCodec)
            .dispatch(RitualResult::getType, RitualResultType::codec);

    /**
     * Called once the ritual animation finishes. Use this to create the desired result.
     *
     * @param level     the level the Forge is in
     * @param pos       the pos where the Forge is located
     * @param forgeTier the tier of the Forge
     * @param mainInput the main input ItemStack of the ritual
     * @return the result ItemStack of the ritual
     */
    ItemStack apply(Level level, BlockPos pos, int forgeTier, ItemStack mainInput);

    /**
     * @return the type which serializes and deserializes this result
     */
    RitualResultType<? extends RitualResult> getType();
}
