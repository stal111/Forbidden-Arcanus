package com.stal111.forbidden_arcanus.common.item.enhancer;

import com.mojang.serialization.Codec;

/**
 * @author stal111
 * @since 2023-02-18
 */
public record EnhancerEffectType<T extends EnhancerEffect>(Codec<T> codec, EnhancerTarget target) {
}
