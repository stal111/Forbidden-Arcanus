package com.stal111.forbidden_arcanus.common.item.enhancer.condition;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.util.ExtraCodecs;

/**
 * @author stal111
 * @since 2023-03-03
 */
public abstract class EffectCondition {

    public static final Codec<EffectCondition> DIRECT_CODEC = ExtraCodecs.lazyInitializedCodec(() -> FARegistries.ENHANCER_EFFECT_CONDITION_REGISTRY.get().getCodec())
            .dispatch(EffectCondition::getType, EffectConditionType::codec);

    public abstract EffectConditionType<? extends EffectCondition> getType();
}
