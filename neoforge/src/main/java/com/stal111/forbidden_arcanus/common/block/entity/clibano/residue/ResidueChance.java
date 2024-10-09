package com.stal111.forbidden_arcanus.common.block.entity.clibano.residue;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;

import javax.annotation.Nonnull;

/**
 * @author stal111
 * @since 05.02.2024
 */
public record ResidueChance(Holder<ResidueType> type, double chance) {

    public static final Codec<ResidueChance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResidueType.CODEC.fieldOf("type").forGetter(info -> {
                return info.type;
            }),
            Codec.DOUBLE.fieldOf("chance").forGetter(info -> {
                return info.chance;
            })
    ).apply(instance, ResidueChance::new));

    public static ResidueChance fromNetwork(@Nonnull FriendlyByteBuf buffer) {
        return new ResidueChance(buffer.readJsonWithCodec(ResidueType.CODEC), buffer.readDouble());
    }

    public void toNetwork(@Nonnull FriendlyByteBuf buffer) {
        buffer.writeJsonWithCodec(ResidueType.CODEC, this.type);
        buffer.writeDouble(this.chance);
    }
}
