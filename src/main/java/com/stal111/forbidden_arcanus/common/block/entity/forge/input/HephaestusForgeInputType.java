package com.stal111.forbidden_arcanus.common.block.entity.forge.input;

import com.mojang.serialization.Codec;

/**
 * @author stal111
 * @since 2023-05-24
 */
public record HephaestusForgeInputType<T extends HephaestusForgeInput>(Codec<T> codec) {
}
