package com.stal111.forbidden_arcanus.common.item.enhancer.effect;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerTarget;

/**
 * @author stal111
 * @since 2023-02-18
 */
public record EnhancerEffectType<T extends EnhancerEffect>(Codec<T> codec, EnhancerTarget target) {
}
