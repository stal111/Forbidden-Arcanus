package com.stal111.forbidden_arcanus.common.block.entity.forge.circle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryFileCodec;

/**
 * @author stal111
 * @since 14.04.2024
 */
public record MagicCircleType(Identifier innerTexture, Identifier outerTexture) {

    public static final Codec<MagicCircleType> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("inner_texture").forGetter(MagicCircleType::innerTexture),
            Identifier.CODEC.fieldOf("outer_texture").forGetter(MagicCircleType::outerTexture)
    ).apply(instance, MagicCircleType::new));

    public static final Codec<Holder<MagicCircleType>> CODEC = RegistryFileCodec.create(FARegistries.MAGIC_CIRCLE, DIRECT_CODEC);
}
