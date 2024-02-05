package com.stal111.forbidden_arcanus.common.block.entity.clibano.residue;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.RegistryFileCodec;

/**
 * @author stal111
 * @since 2022-06-29
 */
public record ResidueType(Component name) {

    public static final Codec<ResidueType> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ComponentSerialization.CODEC.fieldOf("name").forGetter(info -> {
                return info.name;
            })
    ).apply(instance, ResidueType::new));

    public static final Codec<Holder<ResidueType>> CODEC = RegistryFileCodec.create(FARegistries.RESIDUE_TYPE, DIRECT_CODEC);

    public static ResidueType withDefaultKey(String name) {
        return new ResidueType(Component.translatable("residue." + ForbiddenArcanus.MOD_ID + "." + name));
    }
}
