package com.stal111.forbidden_arcanus.common.item.wand;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;

public record WandPart(
        Component materialName,
        WandStats stats
) {

    public static final Codec<WandPart> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ComponentSerialization.CODEC.fieldOf("material_name").forGetter(WandPart::materialName),
            WandStats.CODEC.forGetter(WandPart::stats)
    ).apply(instance, WandPart::new));
}
