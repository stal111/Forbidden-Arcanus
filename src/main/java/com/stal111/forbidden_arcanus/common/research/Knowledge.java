package com.stal111.forbidden_arcanus.common.research;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.ExtraCodecs;

import java.util.List;

/**
 * @author stal111
 * @since 23.11.2023
 */
public record Knowledge(DisplayInfo displayInfo, List<Holder<Knowledge>> parents) {

    public static final Codec<Knowledge> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            DisplayInfo.CODEC.fieldOf("display_info").forGetter(Knowledge::displayInfo),
            ExtraCodecs.lazyInitializedCodec(() -> Knowledge.CODEC).listOf().fieldOf("requirements").forGetter(Knowledge::parents)
    ).apply(instance, Knowledge::new));

    public static final Codec<Holder<Knowledge>> CODEC = RegistryFileCodec.create(FARegistries.KNOWLEDGE, DIRECT_CODEC);
}
