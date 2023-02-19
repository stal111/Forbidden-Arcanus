package com.stal111.forbidden_arcanus.common.item.enhancer;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.util.ExtraCodecs;

/**
 * @author stal111
 * @since 2023-02-18
 */
public abstract class EnhancerEffect {

    public static final Codec<EnhancerEffect> DIRECT_CODEC = ExtraCodecs.lazyInitializedCodec(() -> FARegistries.ENHANCER_EFFECT_REGISTRY.get().getCodec())
            .dispatch(EnhancerEffect::getType, EnhancerEffectType::codec);

    public abstract EnhancerEffectType<? extends EnhancerEffect> getType();
}
