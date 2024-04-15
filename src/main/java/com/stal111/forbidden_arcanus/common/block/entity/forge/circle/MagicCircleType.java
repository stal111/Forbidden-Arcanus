package com.stal111.forbidden_arcanus.common.block.entity.forge.circle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.forge.MagicCircle;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

/**
 * @author stal111
 * @since 14.04.2024
 */
public record MagicCircleType(ResourceLocation innerTexture, ResourceLocation outerTexture) {

    public static final Codec<MagicCircleType> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("inner_texture").forGetter(type -> {
                return type.innerTexture;
            }),
            ResourceLocation.CODEC.fieldOf("outer_texture").forGetter(type -> {
                return type.outerTexture;
            })
    ).apply(instance, MagicCircleType::new));

    public static final Codec<Holder<MagicCircleType>> CODEC = RegistryFileCodec.create(FARegistries.MAGIC_CIRCLE, DIRECT_CODEC);

    public MagicCircleType(ResourceLocation innerTexture, ResourceLocation outerTexture) {
        this.innerTexture = innerTexture;
        this.outerTexture = outerTexture;
    }

    public MagicCircle create(Level level, BlockPos pos) {
        return new MagicCircle(this, level, pos);
    }
}
