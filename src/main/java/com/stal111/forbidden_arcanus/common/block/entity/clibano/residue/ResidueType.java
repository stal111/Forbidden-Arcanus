package com.stal111.forbidden_arcanus.common.block.entity.clibano.residue;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;

/**
 * @author stal111
 * @since 2022-06-29
 */
public record ResidueType(Component name) {

    public static final Codec<ResidueType> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ComponentSerialization.CODEC.fieldOf("name").forGetter(info -> {
                return info.name;
            })
    ).apply(instance, ResidueType::new));
}
