package com.stal111.forbidden_arcanus.common.item.enhancer.condition;

import com.mojang.serialization.Codec;

/**
 * @author stal111
 * @since 2023-03-03
 */
public record EffectConditionType<T extends EffectCondition>(Codec<T> codec) {
}
