package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualManager;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * @author stal111
 * @since 2023-02-05
 */
public abstract class RitualResult {

    public static final Codec<RitualResult> DIRECT_CODEC = Codec.lazyInitialized(FARegistries.RITUAL_RESULT_TYPE_REGISTRY::byNameCodec)
            .dispatch(RitualResult::getType, RitualResultType::codec);

    /**
     * Called before a ritual gets started to see if the result it will have is valid.
     *
     * @param mainIngredient the main ingredient inside the Hephaestus Forge
     * @param level    the level the Forge is in
     * @param pos      the pos where the Forge is located
     * @return whether the result can happen
     */
    public boolean checkConditions(ItemStack mainIngredient, Level level, BlockPos pos) {
        return true;
    }

    /**
     * Called once the ritual animation finishes. Use this to create the desired result.
     *
     * @param accessor the main ingredient inside the Hephaestus Forge
     * @param level    the level the Forge is in
     * @param pos      the pos where the Forge is located
     */
    public abstract void apply(RitualManager.MainIngredientAccessor accessor, Level level, BlockPos pos);

    /**
     * @return the type which serializes and deserializes this result
     */
    public abstract RitualResultType<? extends RitualResult> getType();
}
