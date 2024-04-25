package com.stal111.forbidden_arcanus.common.item.enhancer.effect;

import com.mojang.serialization.MapCodec;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerTarget;

/**
 * @author stal111
 * @since 2023-02-18
 */
public record EnhancerEffectType<T extends EnhancerEffect>(MapCodec<T> codec, EnhancerTarget target) {
}
