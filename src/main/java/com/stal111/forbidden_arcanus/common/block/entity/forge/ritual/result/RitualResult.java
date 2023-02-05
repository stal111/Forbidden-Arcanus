package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.Level;

/**
 * @author stal111
 * @since 2023-02-05
 */
public abstract class RitualResult {

    public static final Codec<RitualResult> DIRECT_CODEC = ExtraCodecs.lazyInitializedCodec(() -> ForbiddenArcanus.RITUAL_RESULT_TYPE_REGISTRY.get().getCodec())
            .dispatch(RitualResult::getType, RitualResultType::codec);

    public abstract void apply(RitualManager.MainIngredientAccessor accessor, Level level, BlockPos pos);

    public void toNetwork(FriendlyByteBuf buffer) {
        this.getType().toNetwork(buffer, this);
    }

    /**
     * @return the codec which serializes and deserializes this structure modifier
     */
    public abstract RitualResultType<? extends RitualResult> getType();
}
