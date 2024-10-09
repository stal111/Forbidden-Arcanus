package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result;

import com.mojang.serialization.MapCodec;

/**
 * @author stal111
 * @since 2023-02-05
 */
public record RitualResultType<T extends RitualResult>(MapCodec<T> codec) {

}
