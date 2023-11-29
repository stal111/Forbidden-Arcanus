package com.stal111.forbidden_arcanus.common.research;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/**
 * @author stal111
 * @since 23.11.2023
 */
public record Knowledge(DisplayInfo displayInfo) {

    public static final Codec<Knowledge> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            DisplayInfo.CODEC.fieldOf("display_info").forGetter(Knowledge::displayInfo)
    ).apply(instance, Knowledge::new));
}
