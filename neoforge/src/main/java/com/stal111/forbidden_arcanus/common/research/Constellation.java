package com.stal111.forbidden_arcanus.common.research;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;

/**
 * @author stal111
 * @since 23.11.2023
 */
public record Constellation(String name, int startX, int startY, Holder<Knowledge> startKnowledge) {

    public static final Codec<Constellation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(Constellation::name),
            Codec.INT.fieldOf("start_x").forGetter(Constellation::startX),
            Codec.INT.fieldOf("start_y").forGetter(Constellation::startY),
            Knowledge.CODEC.fieldOf("start_knowledge").forGetter(Constellation::startKnowledge)
    ).apply(instance, Constellation::new));
}
